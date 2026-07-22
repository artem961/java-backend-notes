# Запросы

Три способа выразить запрос в Spring Data JPA — по нарастанию контроля:
имя метода, `@Query` с JPQL, нативный SQL. Умение выбрать подходящий —
практический навык, который проверяют.

## 1. Производные запросы: из имени метода

```java
List<Order> findByStatusAndCreatedAtAfter(OrderStatus status, Instant after);
Optional<Order> findFirstByCustomerIdOrderByCreatedAtDesc(long customerId);
long countByStatus(OrderStatus status);
boolean existsByEmail(String email);
```

Spring парсит имя: `findBy` + поля через `And`/`Or` + операторы
(`After`, `Between`, `Like`, `In`, `IsNull`...) + `OrderBy`. Плюсы:
нет ни строчки запроса, проверка на старте (опечатка в поле — падение
при запуске, а не в проде). Минус — длинные имена быстро становятся
нечитаемыми: три условия — уже повод перейти на `@Query`.

Полезные формы результата: `Optional<T>`, `List<T>`, `boolean exists...`,
`long count...`, `Page<T>`/`Slice<T>` (пагинация), `Stream<T>`
(потоковая обработка внутри транзакции).

## 2. @Query: JPQL

```java
@Query("""
    select o from Order o
    where o.status = :status
      and o.total >= :minTotal
    """)
List<Order> findExpensive(@Param("status") OrderStatus status,
                          @Param("minTotal") BigDecimal minTotal);
```

JPQL — запросы в терминах **сущностей и их полей**, не таблиц; Hibernate
транслирует в SQL нужного диалекта. Именованные параметры защищают от
SQL-инъекций (никакой конкатенации строк). JPQL тоже валидируется
на старте. Здесь же решается проблема N+1:

```java
@Query("select o from Order o join fetch o.items where o.id = :id")
Optional<Order> findWithItems(@Param("id") long id);
```

`join fetch` загружает связь сразу, одним запросом (подробно — в теме
про ленивую загрузку). Альтернатива без JPQL — `@EntityGraph(attributePaths = "items")`
над производным методом.

## 3. Нативный SQL

```java
@Query(value = "select * from orders where status = :status limit 100",
       nativeQuery = true)
List<Order> findRawByStatus(@Param("status") String status);
```

Когда нужны возможности конкретной БД: оконные функции, CTE, `ON CONFLICT`,
полнотекстовый поиск, хинты. Цена — привязка к диалекту и отсутствие
проверки на старте. Для отчётов и выборок «не сущностей» результат мапится
в **проекции** — интерфейсы с геттерами или DTO-конструкторы:

```java
public interface OrderStats {           // интерфейсная проекция
    String getStatus();
    long getCnt();
}

@Query(value = "select status, count(*) as cnt from orders group by status",
       nativeQuery = true)
List<OrderStats> stats();
```

Проекции работают и в JPQL (`select new com.app.OrderSummary(o.id, o.total)`),
и в производных запросах — и это не только про отчёты: выбирать **только
нужные поля** вместо целых сущностей дешевле и безопаснее для чтения.

## Динамические запросы

Фильтры поиска с необязательными условиями в статический запрос
не укладываются. Штатные инструменты: **Specification**
(`JpaSpecificationExecutor` — условия собираются кодом через Criteria API
и комбинируются), Querydsl (типобезопасный DSL, сторонняя библиотека)
или кастомный фрагмент с `EntityManager`. Конкатенация строк запроса —
никогда (инъекции + нечитаемость).

## Как выбирать

Правило по умолчанию: **имя метода** для простых выборок (1–2 условия),
**JPQL** для всего остального, **native** — когда нужна сила конкретной
БД, **Specification** — для динамических фильтров. И для любых списков —
проекции вместо полных сущностей, где сущность не нужна.

## Как ответить на интервью

Коротко: три уровня — производные запросы из имени метода (быстро,
проверяется на старте, но нечитаемо после 2–3 условий), `@Query` с JPQL
(запросы по сущностям, именованные параметры, `join fetch` против N+1)
и нативный SQL (фичи конкретной БД, результат — в проекции). Динамические
фильтры — Specification/Criteria, не конкатенация строк. Проекции
(интерфейсы или DTO) — чтобы не тянуть полные сущности там, где нужны
три поля.
