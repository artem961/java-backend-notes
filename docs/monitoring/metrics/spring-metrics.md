# Метрики из Spring

Чтобы метрики можно было собирать, сервис должен их **отдавать наружу** в понятном
формате. В Spring Boot это решается почти без кода: **Actuator** плюс библиотека
**Micrometer** выставляют готовый эндпоинт с метриками, который забирает Prometheus.

## Micrometer — фасад для метрик

**Micrometer** — это фасад над метриками, как SLF4J над логами: единый API в коде, а
конкретный бэкенд (Prometheus, Graphite, Datadog…) подключается зависимостью и меняется
без правки кода. В Spring Boot Micrometer встроен, писать вручную интеграцию не нужно.

Чтобы метрики отдавались в формате Prometheus, добавляют стартер:

```xml
<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

После этого Actuator публикует эндпоинт **`/actuator/prometheus`** — текстовый список
метрик, который Prometheus периодически опрашивает (pull).

## Что доступно из коробки

Самое ценное — куча полезных метрик появляется **без единой строки кода**:

- **`http.server.requests`** — количество и длительность HTTP-запросов, с метками по
  эндпоинту, методу и статусу. Это и есть готовый RED по каждому эндпоинту;
- **`jvm.memory.used`**, **`jvm.gc.pause`** — память и паузы сборщика мусора;
- **`jvm.threads.live`** — число потоков;
- **`hikaricp.connections.*`** — состояние пула коннектов к базе (занятые, ожидающие);
- **`system.cpu.usage`** — загрузка CPU.

То есть базовый мониторинг здоровья сервиса включается фактически одной зависимостью.

## Своя метрика

Когда нужно померить что-то своё (например, число оформленных заявок), берут
`MeterRegistry` и заводят счётчик или таймер:

```java
@Service
public class OrderService {
    private final Counter ordersCreated;

    public OrderService(MeterRegistry registry) {
        this.ordersCreated = registry.counter("orders.created");
    }

    public void create(Order order) {
        // ... бизнес-логика ...
        ordersCreated.increment();   // +1 к счётчику
    }
}
```

Для замера времени есть `Timer` (или аннотация `@Timed` над методом) — он сам считает
количество вызовов и распределение длительностей, из которого потом берут перцентили.

## Как это выглядит на эндпоинте

`/actuator/prometheus` отдаёт простой текст «имя метрики с метками → значение»:

```
http_server_requests_seconds_count{uri="/orders",status="200"} 1284
http_server_requests_seconds_sum{uri="/orders",status="200"} 41.7
jvm_memory_used_bytes{area="heap"} 268435456
```

Prometheus раз в несколько секунд забирает этот текст и складывает в свою базу.

## Как ответить на интервью

Коротко: в Spring Boot метрики отдаёт **Actuator** вместе с **Micrometer**. Micrometer —
это фасад над метриками, как SLF4J над логами: единый API, а бэкенд подключается
зависимостью. Добавляешь `micrometer-registry-prometheus`, и появляется эндпоинт
`/actuator/prometheus`, который Prometheus опрашивает по pull. Из коробки идёт куча
полезного без кода: `http.server.requests` — по сути готовый RED по каждому эндпоинту,
метрики памяти и GC, число потоков, состояние пула HikariCP, CPU. Свою метрику заводишь
через `MeterRegistry` — `Counter` для подсчёта событий или `Timer`/`@Timed` для замера
времени. Сам глубоко это не настраивал, но как включается и что даёт из коробки —
понимаю.
