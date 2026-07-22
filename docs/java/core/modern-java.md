# Современная Java

С 2017 года Java выходит каждые полгода, а раз в два года версия объявляется
**LTS** (long-term support) — с многолетней поддержкой. В проде живут именно
LTS-версии:

| LTS | Год | Главное |
|---|---|---|
| 8 | 2014 | Лямбды, Stream API, `Optional`, java.time |
| 11 | 2018 | `var`, HTTP Client, запуск `java File.java` без компиляции |
| 17 | 2021 | Records, sealed-классы, pattern matching для `instanceof`, switch-выражения, text blocks |
| 21 | 2023 | **Виртуальные потоки**, pattern matching для `switch`, sequenced collections |

Вопрос «чем Java 17 отличается от 8» — один из самых частых на собеседованиях,
и ответ на него — по сути содержание этой страницы.

## var — локальный вывод типов

```java
var users = new ArrayList<User>();        // компилятор сам выводит ArrayList<User>
var response = restClient.get(...);
```

Это не «динамическая типизация»: тип по-прежнему статический и известен при
компиляции — просто его не нужно писать руками. Работает **только для локальных
переменных** с инициализатором; для полей, параметров и возвращаемых типов —
нельзя. Практика: использовать там, где тип очевиден из правой части,
и не использовать там, где `var` прячет важную информацию.

## Records

Record — класс-значение одной строкой:

```java
public record OrderDto(long id, String status, BigDecimal total) {}
```

Компилятор генерирует: приватные `final`-поля, конструктор по всем полям,
методы доступа (`id()`, без префикса `get`), корректные `equals`, `hashCode`
и `toString`. Получается **неизменяемый** класс-носитель данных без
шаблонного кода — то, для чего раньше тянули Lombok.

Что ещё важно знать:

- Можно добавлять свои методы и **компактный конструктор** для валидации:

    ```java
    public record Range(int from, int to) {
        public Range {
            if (from > to) throw new IllegalArgumentException("from > to");
        }
    }
    ```

- Record неявно `final` и не может наследовать классы (интерфейсы — может).
- Идеальное применение — DTO: тела запросов/ответов API, проекции из БД,
  события. **Не** подходит для JPA-сущностей: JPA нужны изменяемое состояние
  и конструктор без аргументов.

## Sealed-классы

`sealed` ограничивает, кто может наследовать класс или реализовывать интерфейс:

```java
public sealed interface PaymentResult permits Success, Declined, Error {}
public record Success(String txId) implements PaymentResult {}
public record Declined(String reason) implements PaymentResult {}
public record Error(Exception cause) implements PaymentResult {}
```

Иерархия становится **закрытой**: компилятор знает все варианты. Это раскрывается
в связке со `switch` — можно перебрать варианты без ветки `default`, и если
добавится новый наследник, все такие `switch` перестанут компилироваться,
указав места, которые нужно обновить.

## Pattern matching и switch-выражения

`instanceof` с приведением типа схлопнулся в одну конструкцию:

```java
// было
if (obj instanceof String) {
    String s = (String) obj;
    ...
}
// стало
if (obj instanceof String s) { ... }
```

`switch` стал **выражением** — возвращает значение, стрелочные ветки не
проваливаются (никаких забытых `break`), а компилятор проверяет полноту:

```java
String label = switch (status) {
    case NEW -> "Новый";
    case PAID, SHIPPED -> "В работе";
    // enum перебран полностью — default не нужен
};

// с Java 21 switch умеет сопоставлять по типу
String describe = switch (result) {
    case Success s -> "OK: " + s.txId();
    case Declined d -> "Отказ: " + d.reason();
    case Error e -> "Ошибка: " + e.cause().getMessage();
};
```

Sealed-иерархия + records + switch по типам — это стиль «данные отдельно,
обработка отдельно», пришедший из функциональных языков и активно
используемый в современном Java-коде.

## Text blocks

Многострочные строки без экранирования и склейки — незаменимы для SQL и JSON
в тестах:

```java
String query = """
        select id, status
        from orders
        where user_id = ?
        """;
```

## Мелочи, которые постоянно нужны

- `List.of(...)`, `Map.of(...)`, `Set.of(...)` (Java 9) — неизменяемые коллекции
  на месте.
- `stream.toList()` (Java 16) — вместо `collect(Collectors.toList())`.
- `String`: `isBlank()`, `strip()`, `repeat()`, `lines()`.
- `Optional.ifPresentOrElse(...)`, `Optional.or(...)`.

## Виртуальные потоки — заголовком

Главная фича Java 21: потоки, которые стоят почти ничего, — их можно создавать
миллионами, и блокирующий код перестаёт требовать реактивных фреймворков.
Это тема отдельного разговора — подробно разбирается в разделе
«Многопоточность» (тема «Пулы потоков и асинхронность»).

## Как ответить на интервью

Коротко: после 8-й главные вехи — 11 (`var`), 17 (records, sealed, pattern
matching, switch-выражения, text blocks) и 21 (виртуальные потоки, switch по
типам). Records дали неизменяемые DTO без шаблонного кода, sealed + switch —
закрытые иерархии с проверкой полноты компилятором, виртуальные потоки —
дешёвую конкурентность для блокирующего кода. Если проект переходит с 8 на 17+,
код становится компактнее в первую очередь за счёт records, `var` и switch.
