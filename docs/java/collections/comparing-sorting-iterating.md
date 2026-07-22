# Сравнение, сортировка и итерирование

Сквозные механики, на которых работают все коллекции: как элементы
сравниваются и сортируются и что можно (и нельзя) делать при обходе.

## Comparable: естественный порядок

`Comparable<T>` реализует сам класс — это его **встроенный** порядок:

```java
public class Money implements Comparable<Money> {
    private final long amount;

    @Override
    public int compareTo(Money o) {
        return Long.compare(amount, o.amount);
    }
}
```

Контракт `compareTo`: отрицательное — «я меньше», ноль — «равны»,
положительное — «я больше». `String`, числа, `LocalDate` уже `Comparable` —
поэтому их можно сортировать и класть в `TreeSet` без дополнительных настроек.

## Comparator: внешний порядок

`Comparator<T>` — отдельный объект-правило сравнения. Он нужен, когда порядок
не «зашит» в класс: у класса нет `compareTo`, или нужных порядков несколько
(по имени, по дате, по сумме).

Современный способ создавать компараторы — статические фабрики:

```java
Comparator<User> byName = Comparator.comparing(User::getName);

Comparator<User> cmp = Comparator
        .comparing(User::getCity)                     // сначала по городу
        .thenComparing(User::getName)                 // при равенстве — по имени
        .reversed();                                  // и всё в обратном порядке

Comparator<User> nullsOk = Comparator.comparing(
        User::getEmail, Comparator.nullsLast(Comparator.naturalOrder()));
```

`comparing` + `thenComparing` + `reversed` + `nullsFirst/nullsLast` покрывают
практически любые требования к сортировке — писать `compareTo` руками
с вычитаниями не нужно (тем более что `a - b` на int может переполниться;
правильно — `Integer.compare(a, b)`).

## Сортировка

```java
list.sort(byName);                    // сортирует список на месте
list.sort(null);                      // по естественному порядку (Comparable)
var sorted = list.stream().sorted(cmp).toList(); // новая отсортированная копия
```

Под капотом — TimSort: O(n log n), **стабильная** сортировка (равные элементы
сохраняют исходный взаимный порядок — поэтому сортировки можно «наслаивать»).

!!! warning "Согласованность с equals"
    `TreeSet`/`TreeMap` считают элементы равными по `compareTo() == 0`,
    полностью игнорируя `equals`. Если сравнение учитывает не все значимые
    поля (например, сортируем пользователей по имени, а имена совпали) —
    TreeSet посчитает разных пользователей «дубликатами» и молча отбросит
    второго. Правило: сравнение, используемое в сортированных коллекциях,
    должно различать всё, что различает `equals`, — например, добавлять
    `thenComparing(User::getId)` как последний критерий.

## Итерирование

`for-each` работает с любым `Iterable` и разворачивается компилятором
в итератор:

```java
for (Order order : orders) { ... }
// эквивалент:
Iterator<Order> it = orders.iterator();
while (it.hasNext()) { Order order = it.next(); ... }
```

Для `Map` итерирование идёт по представлениям, и правильный выбор —
`entrySet`, если нужны и ключ, и значение (обход `keySet` с `get(key)`
внутри — двойная работа):

```java
for (Map.Entry<Long, Order> e : orders.entrySet()) {
    process(e.getKey(), e.getValue());
}
```

## Fail-fast и ConcurrentModificationException

Итераторы обычных коллекций — **fail-fast**: коллекция считает структурные
изменения (`modCount`), и если во время обхода она изменилась в обход
итератора, следующий `next()` кидает `ConcurrentModificationException`.
Это защита: продолжать обход по изменившейся структуре — значит получать
непредсказуемые результаты, лучше упасть сразу.

Классика — удаление в цикле:

```java
for (Order o : orders) {
    if (o.isCancelled()) orders.remove(o); // CME на следующей итерации
}
```

Правильные способы:

```java
orders.removeIf(Order::isCancelled);          // лучший вариант

Iterator<Order> it = orders.iterator();       // или через итератор
while (it.hasNext()) {
    if (it.next().isCancelled()) it.remove(); // remove у ИТЕРАТОРА — законен
}
```

Две важные оговорки:

- CME — **не** про многопоточность как таковую: одного потока достаточно
  (пример выше). А в многопоточном коде исключение, наоборот, не гарантировано —
  это best-effort-проверка. Потокобезопасный обход дают только concurrent-коллекции.
- `set(i, x)` в `ArrayList` — не структурное изменение, оно CME не вызывает;
  ломают обход `add`/`remove`.

## Как ответить на интервью

Коротко: `Comparable` — естественный порядок, зашитый в класс; `Comparator` —
внешние порядки, собираются через `comparing`/`thenComparing`/`reversed`.
Сортировка — стабильный TimSort за O(n log n). В `TreeSet`/`TreeMap` равенство
определяет `compareTo`, а не `equals`, — несогласованность молча теряет
элементы. Итераторы fail-fast: изменение коллекции в обход итератора во время
обхода даёт `ConcurrentModificationException` даже в одном потоке; удалять —
через `removeIf` или `iterator.remove()`.
