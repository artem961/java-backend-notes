# Разработка на Quarkus

Как выглядит Quarkus-проект глазами разработчика, знакомого со Spring Boot:
структура и цикл работы очень похожи, отличаются аннотации (стандартные
вместо спринговых) и режим разработки.

## Сервис на Quarkus

```java
@Path("/orders")                          // JAX-RS вместо @RestController
public class OrderResource {

    @Inject                               // CDI вместо @Autowired
    OrderService service;

    @GET
    @Path("/{id}")
    public OrderDto get(@PathParam("id") long id) {
        return service.find(id);
    }
}

@ApplicationScoped                        // аналог @Service (синглтон)
public class OrderService { ... }
```

Соответствие концепций:

| Spring | Quarkus (стандарт) |
|---|---|
| `@RestController`, `@GetMapping` | `@Path`, `@GET` (JAX-RS) |
| `@Autowired` / конструктор | `@Inject` (CDI) |
| `@Service`, `@Component` | `@ApplicationScoped` |
| `application.yml` | `application.properties` (MicroProfile Config) |
| `@Value` | `@ConfigProperty` |
| Spring Data репозитории | Panache |

**Panache** — надстройка над Hibernate: сущность наследует
`PanacheEntity` и получает готовые `persist()`, `findById()`, `list()`
без объявления репозитория:

```java
@Entity
public class Order extends PanacheEntity {
    public String status;

    public static List<Order> byStatus(String status) {
        return list("status", status);
    }
}
```

## Dev mode — главная фишка разработки

`./mvnw quarkus:dev` запускает **режим живой перезагрузки**: изменил код,
обновил запрос — Quarkus пересобрал и перезапустил изменённое за доли
секунды, без ручного рестарта. Там же — Dev UI (веб-панель с бинами,
конфигурацией, эндпоинтами) и **Dev Services**: если в конфигурации не указана
БД, dev mode сам поднимет её в Testcontainers-контейнере. Цикл обратной
связи заметно короче, чем классический перезапуск Spring Boot.

## Расширения вместо стартеров

Аналог Spring Boot-стартеров — **extensions**: `quarkus-hibernate-orm-panache`,
`quarkus-jdbc-postgresql`, `quarkus-kafka` и сотни других. Отличие от
стартеров — расширение не просто тянет зависимости, а включает свой шаг
в build-time-обработку: сообщает, что генерировать и что регистрировать
для нативной сборки. Поэтому библиотеки для Quarkus желательно брать
в виде расширений — «голая» Java-библиотека с рефлексией может не пережить
нативную компиляцию без дополнительной настройки.

## Тесты

`@QuarkusTest` поднимает приложение один раз на все тесты — аналог
`@SpringBootTest`, но быстрее за счёт быстрого старта. В паре с Dev Services
интеграционные тесты с реальной БД получаются почти бесплатно.

## Как ответить на интервью

Коротко: разработка выглядит как Spring Boot со стандартными аннотациями:
JAX-RS (`@Path`, `@GET`) вместо `@RestController`, CDI (`@Inject`) вместо
`@Autowired`, MicroProfile Config вместо `@Value`, Panache — активные записи
поверх Hibernate вместо Spring Data. Сильная сторона — dev mode: живая
перезагрузка кода, Dev UI и автоподнятие зависимостей (БД в контейнере)
без конфигурации. Функциональность подключается расширениями — аналогом
стартеров, дополнительно готовящим код к build-time-обработке и нативной
сборке.
