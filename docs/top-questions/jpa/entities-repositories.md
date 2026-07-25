# Сущности и репозитории в JPA

Это база работы с БД через JPA/Hibernate. **Сущность (Entity)** — Java-класс,
привязанный к таблице; **репозиторий** — интерфейс, через который мы эти сущности
сохраняем и достаём, не написав ни строчки SQL. Разберём требования к ним.

## Сущность (Entity)

Сущность — это обычный класс, помеченный `@Entity`, где каждый объект = одна строка
таблицы, а поля = колонки.

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // геттеры, сеттеры
}
```

**Требования к Entity:**

- аннотация `@Entity`;
- поле-идентификатор с `@Id` (первичный ключ);
- **конструктор без аргументов** (Hibernate создаёт объект через него);
- класс не `final` и поля не `final` (Hibernate строит прокси и подставляет
  значения);
- обычно `@GeneratedValue` — чтобы БД сама генерировала id.

`@GeneratedValue` со стратегией `IDENTITY` — БД сама присваивает id (авто-инкремент);
`SEQUENCE` — через последовательность (эффективнее для пакетной вставки в PostgreSQL).

## Составной первичный ключ

Иногда ключ состоит из **нескольких** колонок. Есть два способа:

- **`@EmbeddedId`** — ключ выносят в отдельный класс, помеченный `@Embeddable`:

```java
@Embeddable
class OrderItemId {
    private Long orderId;
    private Long productId;
}

@Entity
class OrderItem {
    @EmbeddedId
    private OrderItemId id;
}
```

- **`@IdClass`** — похоже, но поля ключа объявляются прямо в сущности, а отдельный
  класс лишь описывает их набор.

Класс ключа должен корректно реализовать `equals`/`hashCode` — по нему сущность
идентифицируется.

## Репозиторий

Репозиторий — интерфейс, который **ты не реализуешь**: Spring Data создаёт
реализацию сам. Достаточно унаследоваться от `JpaRepository`:

```java
public interface UserRepository extends JpaRepository<User, Long> {
    // готовые методы: save, findById, findAll, delete, count...

    // метод по имени — Spring сам построит запрос по названию
    Optional<User> findByName(String name);
    List<User> findByNameAndActiveTrue(String name);
}
```

Откуда берётся реализация: Spring видит интерфейс, генерирует прокси и подставляет
код методов — стандартные (`save`, `findById`) и **выведенные из имени** метода
(`findByName` → `where name = ?`).

Иерархия: `CrudRepository` (базовый CRUD) → `PagingAndSortingRepository` (+
пагинация) → `JpaRepository` (+ JPA-специфика, `flush`, `saveAll`). Обычно берут
`JpaRepository`.

## Что запомнить

- Entity — класс с `@Entity` и `@Id`, нужен конструктор без аргументов, не `final`.
- `@GeneratedValue` — БД сама генерирует id (`IDENTITY` или `SEQUENCE`).
- Составной ключ — `@EmbeddedId` (отдельный класс) или `@IdClass`.
- Репозиторий — интерфейс от `JpaRepository`; реализацию пишет Spring Data, методы
  можно задавать по имени.
