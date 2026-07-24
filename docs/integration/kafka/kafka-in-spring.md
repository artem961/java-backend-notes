# Kafka в Spring

Spring for Apache Kafka (`spring-kafka`) прячет низкоуровневый клиент за
удобными абстракциями: `KafkaTemplate` для отправки и `@KafkaListener` для
чтения.

## Отправка — KafkaTemplate

```java
@Service
class OrderEvents {
    private final KafkaTemplate<String, OrderCreated> template;

    void publish(OrderCreated e) {
        template.send("orders", e.accountId(), e); // ключ = accountId
    }
}
```

Ключ (`accountId`) определяет партицию — события по одному счёту идут по
порядку. Сериализацию значения настраивают (часто JSON, на проде — Avro +
Schema Registry).

## Чтение — @KafkaListener

```java
@KafkaListener(topics = "orders", groupId = "billing")
public void onOrder(OrderCreated e) {
    // обработка
}
```

- `groupId` — группа консьюмеров; разные группы читают топик независимо.
- Spring сам управляет пулом слушателей, поллингом и (при настройке) коммитом
  offset.

## Надёжность и ошибки

- **Коммит offset** — настраивают режим (ручной/после обработки), чтобы
  получить at-least-once и не терять сообщения.
- **Ошибки обработки** — есть `DefaultErrorHandler` с ретраями и **backoff**;
  после исчерпания попыток «отравленное» сообщение отправляют в **Dead Letter
  Topic (DLT)**, чтобы не блокировать очередь.
- **Идемпотентность** обработки — на стороне приложения (запоминать
  обработанные id), потому что режим at-least-once допускает повторы.

## Сериализация и схемы

JSON прост, но не следит за совместимостью схем. На проде для контроля
эволюции сообщений часто используют **Avro + Schema Registry** — тогда продюсер
и консьюмеры договариваются о схеме централизованно.

!!! note "Честно про опыт"
    Kafka в продакшене не эксплуатировал — разбирал и собирал на пет-проекте
    (`KafkaTemplate`, `@KafkaListener`, DLT). Говорю про модель и типовые
    настройки надёжности, а не про тонкости эксплуатации кластера.

## Как ответить на интервью

Коротко: в Spring отправляют через `KafkaTemplate.send(topic, key, value)`, где
ключ задаёт партицию и порядок, а читают через `@KafkaListener` с `groupId`.
Spring берёт на себя поллинг и коммит offset; для надёжности настраивают коммит
после обработки (at-least-once) и обработчик ошибок с ретраями и backoff, а
неисправимые сообщения уводят в Dead Letter Topic, чтобы не застревала очередь.
Идемпотентность обработки — на приложении. На проде вместо JSON часто берут
Avro + Schema Registry для контроля эволюции схем.
