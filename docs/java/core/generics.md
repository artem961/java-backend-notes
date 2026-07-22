# Дженерики

Дженерики — параметризация типов: класс или метод пишется один раз, а с каким
типом работать — подставляется в месте использования. Главная цель — перенести
проверку типов из рантайма в **компиляцию**.

До дженериков коллекции хранили `Object`, и ошибка типа взрывалась в проде:

```java
List list = new ArrayList();          // сырой (raw) тип
list.add("строка");
Integer x = (Integer) list.get(0);    // компилируется; ClassCastException в рантайме
```

С дженериками тот же баг не пройдёт компиляцию:

```java
List<Integer> list = new ArrayList<>();
list.add("строка"); // ошибка компиляции
```

Сырые типы остались в языке только для совместимости — в новом коде их не пишут.

## Обобщённые классы и методы

```java
// класс с параметром типа
public class Pair<A, B> {
    private final A first;
    private final B second;
    // ...
}

// обобщённый метод: параметр типа объявляется перед возвращаемым типом
public static <T> T firstOrNull(List<T> list) {
    return list.isEmpty() ? null : list.get(0);
}
```

Параметр можно ограничить сверху — «любой `T`, который умеет X»:

```java
public static <T extends Comparable<T>> T max(List<T> list) { ... }
```

Дженерики — повсюду в повседневном коде: `List<T>`, `Optional<T>`,
`ResponseEntity<T>` в Spring MVC, `JpaRepository<Order, Long>` в Spring Data —
это всё один механизм.

## Type erasure — стирание типов

Дженерики существуют **только на этапе компиляции**. Компилятор проверяет типы,
расставляет приведения — и стирает параметры: в байткоде `List<Integer>`
и `List<String>` — это один и тот же класс `List`. Так сделали ради обратной
совместимости со старым кодом при появлении дженериков в Java 5.

Практические следствия стирания:

- **Нельзя `new T()`** и `new T[]` — в рантайме неизвестно, что такое `T`.
- **Нельзя `x instanceof List<String>`** — в рантайме нет `<String>`,
  можно проверить только `x instanceof List`.
- **Нет перегрузки по параметру типа**: `process(List<String>)` и
  `process(List<Integer>)` после стирания — одинаковые сигнатуры,
  ошибка компиляции.
- Если тип всё же нужен в рантайме, его передают явно — отсюда параметр
  `Class<T>` во многих API: `objectMapper.readValue(json, Order.class)`.

## Инвариантность: `List<Integer>` — не `List<Number>`

Интуитивно кажется: раз `Integer` — наследник `Number`, то `List<Integer>`
можно передать туда, где ждут `List<Number>`. Компилятор это запрещает,
и правильно делает:

```java
List<Integer> ints = new ArrayList<>();
List<Number> nums = ints;   // ЕСЛИ БЫ это компилировалось...
nums.add(3.14);             // ...в список Integer попал бы Double
Integer i = ints.get(0);    // и здесь был бы ClassCastException
```

Это называется **инвариантностью**: между `List<Integer>` и `List<Number>`
нет никакого отношения наследования, какими бы ни были их параметры.

## Wildcards: `? extends` и `? super`

Инвариантность безопасна, но неудобна: метод, принимающий `List<Number>`,
не примет `List<Integer>`. Гибкость возвращают маски (wildcards):

- **`List<? extends Number>`** — список Number **или любого наследника**.
  Из него можно безопасно **читать** как `Number`. Но добавлять нельзя ничего:
  компилятор не знает, `List<Integer>` это или `List<Double>`.
- **`List<? super Integer>`** — список Integer **или любого предка**
  (`Number`, `Object`). В него можно безопасно **добавлять** `Integer`:
  куда бы ни добавили, Integer там уместен. А читать — только как `Object`.

Мнемоника **PECS**: *Producer Extends, Consumer Super* — если коллекция
**отдаёт** вам значения, пиши `extends`; если **принимает** — `super`.
Каноничный пример из JDK:

```java
// src отдаёт (producer) -> extends; dest принимает (consumer) -> super
public static <T> void copy(List<? super T> dest, List<? extends T> src)
```

Тот же паттерн — в `Stream API`: `map(Function<? super T, ? extends R>)`,
`sorted(Comparator<? super T>)`. Читать такие сигнатуры и понимать «почему
здесь super» — ровно тот уровень, который ждут на интервью.

## Как ответить на интервью

Коротко: дженерики переносят проверку типов в компиляцию и избавляют от
приведений; в рантайме параметры стёрты (erasure), поэтому нет `new T()`,
`instanceof` с параметром и перегрузки по нему — тип при необходимости передают
через `Class<T>`. Дженерики инвариантны: `List<Integer>` не является
`List<Number>`, иначе можно было бы испортить список. Гибкость дают wildcard:
`? extends` для чтения, `? super` для записи — PECS.
