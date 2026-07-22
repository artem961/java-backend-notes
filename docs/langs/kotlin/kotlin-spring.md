# Kotlin и Spring

Spring официально поддерживает Kotlin: стартеры, документация с примерами
на обоих языках, Kotlin-расширения в API. Spring Boot-приложение на Kotlin —
это тот же Spring, просто на другом языке; отличий немного, но они важные.

## Два компиляторных плагина

Почти весь «интеграционный» слой — это два плагина компилятора,
которые Spring Initializr подключает автоматически:

- **`kotlin-spring`** (вариант `allopen`) — классы Kotlin по умолчанию
  `final`, а Spring нужны прокси-наследники для `@Transactional`, `@Async`,
  `@Cacheable`. Плагин автоматически делает `open` классы, помеченные
  Spring-аннотациями (`@Component`, `@Service`, `@Configuration`...).
  Без него — ошибка старта или молча не работающие аннотации.
- **`kotlin-jpa`** (вариант `noarg`) — JPA требует у сущностей конструктор
  без аргументов; плагин генерирует его для классов с `@Entity`.

## Что меняется в повседневном коде

**Внедрение зависимостей** — идиоматично через первичный конструктор,
без `@Autowired`:

```kotlin
@Service
class OrderService(
    private val repository: OrderRepository,
    private val kafka: KafkaTemplate<String, OrderEvent>
) { ... }
```

**Null safety против конфигурации и запросов**: nullability типов заменяет
`required=false` и `Optional`:

```kotlin
@GetMapping("/orders")
fun find(@RequestParam status: String?): List<OrderDto>   // параметр необязателен
```

Spring читает nullability Kotlin-типов: non-null параметр без значения —
это ошибка запроса, nullable — законный `null`.

**DTO — data-классы**: Jackson с модулем `jackson-module-kotlin`
(подключается автоматически) десериализует их через основной конструктор,
уважая параметры по умолчанию и nullability — JSON без обязательного поля
упадёт с понятной ошибкой, а не создаст объект с `null` в non-null поле.

**Сущности JPA — не data-классы** (причины — в теме про классы): обычный
класс, `equals` по `id`, поля `var` с разумными дефолтами.

## Корутины в контроллерах

Spring WebFlux (и часть MVC-API) понимает suspend-функции напрямую:

```kotlin
@GetMapping("/dashboard/{id}")
suspend fun dashboard(@PathVariable id: Long): Dashboard =
    dashboardService.load(id)      // suspend-вызовы вместо Mono/Flux
```

Это главный практический бонус Kotlin в реактивном стеке: асинхронный
код пишется последовательно, без цепочек `Mono.flatMap`. В классическом
блокирующем MVC с JDBC корутины не обязательны — там всё и так работает
на потоках (или виртуальных потоках).

## Мелкие идиомы

- Логгер: `companion object { private val log = LoggerFactory.getLogger(...) }`.
- Расширения Spring для Kotlin: reified-варианты типа
  `restTemplate.getForObject<UserDto>(url)` — без `UserDto::class.java`.
- Тесты на Kotlin приятнее за счёт бэктик-имён:
  `` fun `возвращает 404 для несуществующего заказа`() ``.

## Как ответить на интервью

Коротко: Spring полноценно поддерживает Kotlin; ключевая специфика — два
компиляторных плагина: `kotlin-spring` открывает (`open`) бины для прокси,
`kotlin-jpa` генерирует no-arg конструкторы сущностям. Зависимости внедряются
через первичный конструктор, nullability типов работает как `required=false`,
DTO — data-классы через `jackson-module-kotlin`, сущности — обычные классы.
В WebFlux контроллеры пишутся suspend-функциями — асинхронность без
`Mono`/`Flux`-цепочек.
