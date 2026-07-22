# Контроллеры и маппинг параметров

Контроллер — класс веб-слоя: принимает разобранный запрос, вызывает сервис,
возвращает результат. Хороший контроллер тонкий: никакой бизнес-логики,
только маппинг HTTP ↔ доменные вызовы.

## @Controller и @RestController

- `@Controller` — исторический вариант для серверного HTML: методы возвращают
  имя шаблона (view), тело — через отдельную аннотацию `@ResponseBody`.
- `@RestController` = `@Controller` + `@ResponseBody` на всех методах:
  возвращаемое значение — сразу тело ответа (JSON). Стандарт для API.

```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) { this.service = service; }

    @GetMapping("/{id}")
    public OrderDto get(@PathVariable long id) {
        return service.find(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody @Valid CreateOrderRequest request) {
        return service.create(request);
    }
}
```

`@RequestMapping` на классе — общий префикс пути; `@GetMapping`,
`@PostMapping` и т.д. — сокращения `@RequestMapping(method = ...)`.

## Откуда берутся аргументы метода

Каждая аннотация — свой источник данных из запроса:

| Аннотация | Источник | Пример |
|---|---|---|
| `@PathVariable` | сегмент пути | `/orders/{id}` → `id` |
| `@RequestParam` | query-параметр | `?status=NEW&page=2` |
| `@RequestBody` | тело запроса (JSON → объект) | POST/PUT-запросы |
| `@RequestHeader` | заголовок | `@RequestHeader("X-Request-Id")` |
| `@CookieValue` | кука | сессии, трекинг |

Плюс параметры без аннотаций, которые Spring узнаёт по типу:
`HttpServletRequest`/`Response`, `Principal`/`Authentication`
(текущий пользователь), `Pageable` (пагинация из query-параметров).

Нюансы, которые всплывают на интервью:

- `@RequestParam` по умолчанию **обязателен** — без параметра будет 400.
  Необязательный — `required = false` или `defaultValue`, а лучше тип
  `Optional<...>`/nullable.
- **Группу query-параметров** можно собрать в объект без аннотации —
  Spring заполнит поля по именам (form-биндинг): удобно для фильтров поиска.
- `@RequestBody` в метод — **один**: тело читается единожды.

## Возврат ответа

Обычный случай — вернуть DTO (200 OK) или `@ResponseStatus` для другого
кода. Когда код/заголовки зависят от логики — `ResponseEntity`:

```java
@GetMapping("/{id}")
public ResponseEntity<OrderDto> get(@PathVariable long id) {
    return service.findOptional(id)
        .map(ResponseEntity::ok)                      // 200 + тело
        .orElse(ResponseEntity.notFound().build());   // 404
}
```

Правило стиля: не возвращать наружу JPA-сущности — только DTO. Сущность
тянет за собой ленивые связи (LazyInitializationException при сериализации),
жёстко связывает API со схемой БД и может утащить лишние поля.

## Как ответить на интервью

Коротко: `@RestController` — контроллер, у которого возвращаемое значение
сразу сериализуется в тело ответа; `@GetMapping`/`@PostMapping` привязывают
методы к пути и HTTP-методу. Аргументы собираются аннотациями по источникам:
`@PathVariable` — путь, `@RequestParam` — query (по умолчанию обязателен),
`@RequestBody` — JSON-тело, плюс типовые параметры вроде `Pageable`
и `Authentication`. Ответ — DTO напрямую или `ResponseEntity`, когда нужен
контроль статуса и заголовков. Контроллер тонкий, наружу — DTO,
не JPA-сущности.
