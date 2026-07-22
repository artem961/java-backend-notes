# Репозитории

Spring Data JPA убирает последний слой рутины над Hibernate: вместо
написания DAO с `EntityManager` объявляется **интерфейс**, а реализацию
генерирует Spring. Здесь — как это работает; сами JPA/Hibernate
(persistence context, ленивость) разобраны в блоке «Данные».

## Интерфейс вместо реализации

```java
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
}
```

Этого достаточно. При старте Spring Data находит интерфейсы-наследники
`Repository`, и для каждого создаёт **прокси-реализацию**: базовые методы
делегируются готовому классу (`SimpleJpaRepository` поверх `EntityManager`),
а производные (`findByStatus`) — разбираются из имени метода и превращаются
в запросы. Репозиторий — обычный бин, внедряется как всё остальное.

## Иерархия интерфейсов

- `Repository<T, ID>` — маркер, методов нет;
- `CrudRepository` — CRUD: `save`, `findById`, `delete`, `count`...;
- `PagingAndSortingRepository` — + пагинация и сортировка;
- **`JpaRepository`** — + JPA-специфика (`flush`, `saveAll`, батчи).
  Стандартный выбор в JPA-проектах.

Практика: можно наследовать `Repository` и объявить **только нужные
методы** — например, репозиторий без `deleteAll` для сущности, которую
нельзя удалять массово.

## Ключевые базовые методы

- `findById` → `Optional<T>` — отсутствие результата выражено типом.
- **`save` — это upsert по состоянию `id`**: новая сущность → `persist`
  (INSERT), с существующим id → `merge` (SELECT + UPDATE при отличиях).
  Отсюда неожиданный для новичков SELECT перед UPDATE.
- Внутри транзакции у **managed**-сущности `save` вызывать не обязательно —
  dirty checking Hibernate сам сохранит изменения при коммите; `save`
  нужен для новых и detached-объектов.

## Кастомная реализация

Когда генерации мало (сложный динамический запрос, jdbc, батчи) —
фрагментный интерфейс:

```java
public interface OrderRepositoryCustom {
    List<Order> searchComplex(OrderFilter filter);
}

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    // своя реализация через EntityManager / JdbcTemplate
}

public interface OrderRepository
        extends JpaRepository<Order, Long>, OrderRepositoryCustom { }
```

Spring склеит прокси из генерированной и ручной части. Это штатный ответ
на «а если запрос слишком сложный для Spring Data».

## Слой репозитория в архитектуре

Договорённости, которые ждут от кандидата:

- Репозиторий — **только доступ к данным**; бизнес-логика — в сервисах.
- Транзакции открываются на **сервисном** слое; у базовых методов
  Spring Data есть свои `@Transactional`, но одна бизнес-операция
  из нескольких вызовов должна быть атомарной на уровне сервиса.
- Наружу из сервиса отдаются DTO, не сущности.

## Как ответить на интервью

Коротко: Spring Data JPA генерирует реализацию репозитория по интерфейсу:
наследуешь `JpaRepository`, получаешь CRUD поверх `EntityManager`,
а методы вида `findByStatus` превращаются в запросы по имени. `save` —
это persist для новых и merge для существующих (для managed-сущностей
внутри транзакции изменения сохраняет dirty checking и без save).
Сложные случаи — фрагментный интерфейс со своей реализацией. Репозиторий —
слой доступа к данным; транзакции и логика — на сервисах.
