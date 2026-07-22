# Фильтры и интерсепторы

Сквозная обработка запросов — логирование, аутентификация, трейсинг,
лимиты — не должна размазываться по контроллерам. Для неё два механизма
на разных уровнях: сервлетные фильтры и спринговые интерсепторы.

## Фильтр: уровень Servlet API

Фильтр стоит **до** `DispatcherServlet` и оборачивает всю дальнейшую
обработку:

```java
@Component
public class RequestIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String requestId = Optional.ofNullable(req.getHeader("X-Request-Id"))
                                   .orElse(UUID.randomUUID().toString());
        MDC.put("requestId", requestId);          // в контекст логирования
        try {
            chain.doFilter(req, res);             // передать дальше по цепочке
        } finally {
            MDC.remove("requestId");
        }
    }
}
```

- Фильтры образуют **цепочку**: каждый решает — передать дальше
  (`chain.doFilter`) или оборвать обработку (так Security возвращает 401).
- Код до `doFilter` выполняется на пути «туда», после — на пути «обратно».
- `OncePerRequestFilter` — базовый класс Spring, гарантирующий один вызов
  на запрос; порядок фильтров задаётся `@Order`/`FilterRegistrationBean`.
- Фильтр не знает, какой контроллер обработает запрос, — он видит только
  «сырые» request/response. Зато видит **все** запросы, включая статику
  и те, что не дойдут до MVC.

Типовые обитатели фильтров: **вся цепочка Spring Security**, CORS,
request-id/трейсинг, логирование доступа, rate limiting.

## Интерсептор: уровень Spring MVC

Интерсептор работает **внутри** `DispatcherServlet`, после маршрутизации:

```java
public class TimingInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
                             Object handler) {        // handler — метод контроллера
        req.setAttribute("start", System.nanoTime());
        return true;                                  // false — оборвать обработку
    }

    public void afterCompletion(HttpServletRequest req, HttpServletResponse res,
                                Object handler, Exception ex) {
        long tookMs = (System.nanoTime() - (long) req.getAttribute("start")) / 1_000_000;
        log.info("{} {} -> {} ({} ms)", req.getMethod(), req.getRequestURI(),
                 res.getStatus(), tookMs);
    }
}
```

Три точки: `preHandle` (до контроллера, может оборвать), `postHandle`
(после контроллера, до рендера ответа), `afterCompletion` (после всего,
включая ошибки). Регистрируется через `WebMvcConfigurer.addInterceptors`
с указанием путей.

Ключевое отличие от фильтра: интерсептор **знает обработчик** — параметр
`handler` содержит метод контроллера, можно прочитать его аннотации
(идиома: своя аннотация `@Audited` на методе + интерсептор, который
её проверяет).

## Как выбирать

| Критерий | Фильтр | Интерсептор |
|---|---|---|
| Уровень | Servlet API, до Spring MVC | внутри Spring MVC |
| Видит | сырые request/response | + какой метод контроллера |
| Покрывает | все запросы (и статику) | только MVC-маршруты |
| Типовые задачи | Security, CORS, трейсинг | аудит, тайминги, проверки по аннотациям |

Правило: нужно работать **до** Spring или со всеми запросами — фильтр;
нужна информация о контроллере — интерсептор. Третий инструмент того же
ряда — АОП-аспект: сквозная логика не на HTTP-границе, а вокруг вызовов
бинов (разобран в теме про AOP).

## Как ответить на интервью

Коротко: фильтр — механизм Servlet API до `DispatcherServlet`: цепочка,
каждый передаёт дальше или обрывает; там живут Security, CORS, трейсинг —
и фильтры видят все запросы, но не знают, какой контроллер сработает.
Интерсептор — механизм Spring MVC после маршрутизации: `preHandle`/
`postHandle`/`afterCompletion`, знает целевой метод и его аннотации —
аудит, тайминги. Выбор: до Spring / все запросы — фильтр, нужен контекст
контроллера — интерсептор, не-HTTP сквозная логика — аспект.
