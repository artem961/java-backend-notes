# Сущности и маппинг

Сущность (`@Entity`) — класс, отображённый на таблицу базы. Экземпляр
сущности соответствует строке, поля — столбцам. Маппинг задаётся аннотациями.

## Минимальная сущность

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private Instant createdAt;

    protected User() { }   // JPA требует конструктор без аргументов
    // геттеры/сеттеры
}
```

Требования JPA к сущности: аннотация `@Entity`, поле `@Id`, **конструктор без
аргументов** (Hibernate создаёт объект рефлексией и заполняет поля), не
`final` класс/методы (нужен прокси для ленивости).

## Основные аннотации

| Аннотация | Назначение |
|---|---|
| `@Id` | первичный ключ |
| `@GeneratedValue` | как генерируется ключ |
| `@Column` | имя/ограничения столбца |
| `@Enumerated(EnumType.STRING)` | как хранить enum |
| `@Transient` | поле **не** сохранять в БД |
| `@Embedded` / `@Embeddable` | вложить группу полей (адрес) в ту же таблицу |
| `@Version` | оптимистичная блокировка (см. тему про блокировки) |

## Генерация id — важная деталь

Стратегии `@GeneratedValue`:

- **`IDENTITY`** — id генерирует БД (`SERIAL`/auto-increment). Просто, но
  Hibernate **не может** батчить вставки: чтобы узнать id, он обязан выполнить
  `INSERT` немедленно на каждую сущность.
- **`SEQUENCE`** — id из последовательности PostgreSQL. Рекомендуется для
  PostgreSQL: Hibernate может брать id пачками (`allocationSize`) и батчить
  вставки — быстрее при массовой записи.
- **`AUTO`** — Hibernate выбирает сам (для PostgreSQL — обычно sequence).

Практический вывод: на PostgreSQL для нагруженной вставки предпочтителен
`SEQUENCE`.

## `@Enumerated` — грабля

Всегда `EnumType.STRING`, не `ORDINAL`. `ORDINAL` хранит **порядковый номер**
enum-константы: вставишь новую константу в середину — все старые записи в базе
«поедут» на другое значение. `STRING` хранит имя — устойчиво к изменениям.

## Про Kotlin и Lombok

- **Kotlin `data class` для сущности — антипаттерн** (сгенерированные
  `equals`/`hashCode`/`copy` конфликтуют с прокси и ленивостью; подробно — в
  разделе про Kotlin).
- **Lombok `@Data` на сущности — тоже плохо**: его `equals`/`hashCode` по всем
  полям трогают ленивые связи (лишние запросы, `LazyInitializationException`)
  и ломаются на управляемых сущностях. Для `equals`/`hashCode` сущности
  берут бизнес-ключ или id с аккуратной реализацией.

## Как ответить на интервью

Коротко: `@Entity` — класс на таблицу, экземпляр = строка. Нужны `@Id`,
конструктор без аргументов, не-final класс. Ключевые аннотации — `@Column`,
`@Enumerated`, `@Transient`, `@Embedded`, `@Version`. Две вещи, где новички
спотыкаются: генерацию id на PostgreSQL лучше делать `SEQUENCE` (позволяет
батчить вставки, в отличие от `IDENTITY`), а enum хранить `EnumType.STRING`,
не `ORDINAL` (иначе добавление константы ломает старые данные). И не делать
сущность Kotlin `data class` или Lombok `@Data` — это конфликтует с прокси и
ленивостью.
