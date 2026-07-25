# Содержание

Структура конспекта: блоки и разделы внутри них. Каждая тема живёт
в собственном файле. Нажми на раздел, чтобы раскрыть список тем.

## Java

??? note "1. Java Core"
    - [ООП и наследование](java/core/oop-inheritance.md)
    - [Устройство класса](java/core/class-structure.md)
    - [`Object`, `equals` и `hashCode`](java/core/object-equals-hashcode.md)
    - [Строки, примитивы и обёртки](java/core/strings-primitives-wrappers.md)
    - [Исключения](java/core/exceptions.md)
    - [Дженерики](java/core/generics.md)
    - [`null`, `Optional` и неизменяемость](java/core/null-optional-immutability.md)
    - [Аннотации и рефлексия](java/core/annotations-reflection.md)
    - [Современная Java](java/core/modern-java.md)

??? note "2. Коллекции"
    - [Обзор коллекций](java/collections/overview.md)
    - [List](java/collections/list.md)
    - [Map](java/collections/map.md)
    - [Set](java/collections/set.md)
    - [Сравнение, сортировка и итерирование](java/collections/comparing-sorting-iterating.md)

??? note "3. Stream API"
    - [Лямбды и функциональный стиль](java/streams/lambdas.md)
    - [Устройство и операции стримов](java/streams/stream-operations.md)

??? note "4. Многопоточность"
    - [Основы многопоточности](java/concurrency/basics.md)
    - [Проблемы многопоточности](java/concurrency/problems.md)
    - [Синхронизация и блокировки](java/concurrency/synchronization.md)
    - [Пулы потоков и асинхронность](java/concurrency/thread-pools.md)
    - [Потокобезопасные коллекции](java/concurrency/concurrent-collections.md)

??? note "5. JVM и память"
    - [Устройство JVM](java/jvm/jvm-structure.md)
    - [Память и сборка мусора](java/jvm/memory-gc.md)

## Языки и платформы

??? note "6. Kotlin"
    - [Синтаксис и отличия от Java](langs/kotlin/syntax-vs-java.md)
    - [Null safety](langs/kotlin/null-safety.md)
    - [Классы и объекты](langs/kotlin/classes-objects.md)
    - [Функциональные возможности](langs/kotlin/functional.md)
    - [Корутины](langs/kotlin/coroutines.md)
    - [Kotlin и Spring](langs/kotlin/kotlin-spring.md)

??? note "7. Quarkus"
    - [Что такое Quarkus и зачем он](langs/quarkus/what-is-quarkus.md)
    - [Разработка на Quarkus](langs/quarkus/development.md)

??? note "8. JavaEE"
    - [Что такое JavaEE и Jakarta EE](langs/javaee/what-is-javaee.md)
    - [Основные технологии](langs/javaee/core-technologies.md)

## Spring

??? note "9. Spring Core"
    - [IoC и внедрение зависимостей](spring/core/ioc-di.md)
    - [Бины и контекст](spring/core/beans-context.md)
    - [Жизненный цикл бина](spring/core/bean-lifecycle.md)
    - [Скоупы](spring/core/scopes.md)
    - [Разрешение зависимостей](spring/core/dependency-resolution.md)

??? note "10. Spring Boot"
    - [Зачем нужен Spring Boot](spring/boot/why-spring-boot.md)
    - [Автоконфигурация](spring/boot/autoconfiguration.md)
    - [Конфигурация приложения](spring/boot/configuration.md)
    - [Запуск и жизненный цикл приложения](spring/boot/startup-lifecycle.md)

??? note "11. Spring MVC"
    - [Путь запроса](spring/mvc/request-path.md)
    - [Контроллеры и маппинг параметров](spring/mvc/controllers-mapping.md)
    - [Сериализация и валидация](spring/mvc/serialization-validation.md)
    - [Обработка ошибок](spring/mvc/error-handling.md)
    - [Фильтры и интерсепторы](spring/mvc/filters-interceptors.md)

??? note "12. Spring AOP и транзакции"
    - [AOP и прокси](spring/aop/aop-proxies.md)
    - [Транзакции в Spring](spring/aop/transactions.md)
    - [Распространение и изоляция](spring/aop/propagation-isolation.md)
    - [Где `@Transactional` ломается](spring/aop/transactional-pitfalls.md)

??? note "13. Spring Security"
    - [Как устроен Spring Security](spring/security/architecture.md)
    - [Аутентификация](spring/security/authentication.md)
    - [Авторизация](spring/security/authorization.md)
    - [Сессии, токены и JWT](spring/security/sessions-tokens-jwt.md)
    - [OAuth2 и внешние провайдеры](spring/security/oauth2.md)

??? note "14. Spring Data JPA"
    - [Репозитории](spring/data-jpa/repositories.md)
    - [Запросы](spring/data-jpa/queries.md)
    - [Пагинация и сортировка](spring/data-jpa/pagination-sorting.md)
    - [Модификация данных](spring/data-jpa/modifying.md)

??? note "15. Spring Cloud Gateway"
    - [Зачем нужен шлюз](spring/gateway/why-gateway.md)
    - [Маршрутизация](spring/gateway/routing.md)
    - [Фильтры](spring/gateway/filters.md)
    - [Реактивность и эксплуатация](spring/gateway/reactive-operations.md)

## Данные

??? note "16. БД и SQL"
    - [Реляционная модель](data/sql/relational-model.md)
    - [Выборка данных](data/sql/querying.md)
    - [Агрегация и группировка](data/sql/aggregation.md)
    - [Изменение данных и DDL](data/sql/modifying-ddl.md)
    - [Индексы](data/sql/indexes.md)
    - [Производительность запросов](data/sql/query-performance.md)
    - [Транзакции и ACID](data/sql/transactions-acid.md)
    - [Уровни изоляции и аномалии](data/sql/isolation-anomalies.md)
    - [MVCC в PostgreSQL](data/sql/mvcc.md)
    - [Пессимистичные блокировки](data/sql/pessimistic-locks.md)
    - [Оптимистичная и пессимистичная блокировка](data/sql/optimistic-pessimistic.md)

??? note "17. Репликация и шардирование"
    - [Репликация](data/scaling/replication.md)
    - [Чтение с реплик и согласованность](data/scaling/replica-reads.md)
    - [Партиционирование](data/scaling/partitioning.md)
    - [Шардирование](data/scaling/sharding.md)

??? note "18. JPA и Hibernate"
    - [Что такое JPA и ORM](data/jpa/what-is-jpa.md)
    - [Сущности и маппинг](data/jpa/entities-mapping.md)
    - [Persistence context](data/jpa/persistence-context.md)
    - [Связи между сущностями](data/jpa/associations.md)
    - [Ленивая загрузка и N+1](data/jpa/lazy-n-plus-1.md)
    - [Запросы](data/jpa/queries.md)
    - [Блокировки и конкурентный доступ](data/jpa/locking.md)
    - [Кэш второго уровня](data/jpa/second-level-cache.md)

??? note "19. Миграции БД"
    - [Зачем нужны миграции](data/migrations/why-migrations.md)
    - [Flyway и Liquibase](data/migrations/flyway-liquibase.md)
    - [Миграции на живой базе](data/migrations/live-migrations.md)

??? note "20. Redis"
    - [Что такое Redis](data/redis/what-is-redis.md)
    - [Кэширование: стратегии](data/redis/caching-strategies.md)
    - [Проблемы кэширования](data/redis/caching-problems.md)
    - [Redis не только кэш](data/redis/beyond-cache.md)
    - [Redis в Spring](data/redis/redis-in-spring.md)
    - [Персистентность и надёжность](data/redis/persistence.md)

??? note "21. S3 и MinIO"
    - [Объектное хранилище](data/s3/object-storage.md)
    - [S3 и MinIO](data/s3/s3-minio.md)
    - [Работа из приложения](data/s3/from-application.md)
    - [Presigned URL](data/s3/presigned-url.md)

## Взаимодействие

??? note "22. HTTP"
    - [Как работает HTTP](integration/http/how-http-works.md)
    - [Методы и коды ответа](integration/http/methods-status-codes.md)
    - [Заголовки и тело](integration/http/headers-body.md)
    - [Куки и сессии](integration/http/cookies-sessions.md)
    - [Кэширование в HTTP](integration/http/caching.md)
    - [TLS и HTTPS](integration/http/tls-https.md)
    - [Обратный прокси и балансировщик](integration/http/reverse-proxy-balancer.md)
    - [CORS](integration/http/cors.md)

??? note "23. REST"
    - [Что такое REST](integration/rest/what-is-rest.md)
    - [Проектирование ресурсов и данных](integration/rest/resource-design.md)
    - [Ошибки и статусы](integration/rest/errors-statuses.md)
    - [Идемпотентность и надёжность](integration/rest/idempotency-reliability.md)
    - [Версионирование и эволюция](integration/rest/versioning.md)

??? note "24. gRPC"
    - [Что такое gRPC и зачем](integration/grpc/what-is-grpc.md)
    - [Protobuf и контракт](integration/grpc/protobuf-contract.md)
    - [Виды вызовов](integration/grpc/call-types.md)
    - [gRPC на практике](integration/grpc/grpc-in-practice.md)

??? note "25. WebSocket"
    - [Что такое WebSocket](integration/websocket/what-is-websocket.md)
    - [Установка соединения](integration/websocket/handshake.md)
    - [WebSocket в Spring](integration/websocket/websocket-in-spring.md)
    - [Эксплуатация и ограничения](integration/websocket/operations-limits.md)

??? note "26. SSE"
    - [Что такое SSE](integration/sse/what-is-sse.md)
    - [SSE в Spring](integration/sse/sse-in-spring.md)
    - [Что выбрать: WebSocket, SSE или Long Polling](integration/sse/websocket-vs-sse-vs-polling.md)

??? note "27. Long Polling"
    - [Как устроен long polling](integration/long-polling/long-polling.md)

??? note "28. SMTP"
    - [Как работает почта](integration/smtp/how-email-works.md)
    - [Отправка писем из приложения](integration/smtp/sending-from-application.md)

??? note "29. Kafka"
    - [Что такое Kafka и зачем](integration/kafka/what-is-kafka.md)
    - [Устройство](integration/kafka/architecture.md)
    - [Продюсер](integration/kafka/producer.md)
    - [Консьюмер](integration/kafka/consumer.md)
    - [Гарантии доставки](integration/kafka/delivery-guarantees.md)
    - [Kafka в Spring](integration/kafka/kafka-in-spring.md)

??? note "30. Avro и Schema Registry"
    - [Зачем нужна схема](integration/avro/why-schema.md)
    - [Avro](integration/avro/avro.md)
    - [Schema Registry](integration/avro/schema-registry.md)
    - [Эволюция схем](integration/avro/schema-evolution.md)

??? note "31. OpenAPI и Swagger"
    - [Что такое OpenAPI](integration/openapi/what-is-openapi.md)
    - [Code-first и contract-first](integration/openapi/code-first-contract-first.md)

??? note "32. TypeSpec"
    - [Что такое TypeSpec](integration/typespec/what-is-typespec.md)
    - [Генерация и процесс работы](integration/typespec/generation-workflow.md)

## Инженерия

??? note "33. Проектирование и паттерны"
    - [SOLID](engineering/design/solid.md)
    - [Другие принципы проектирования](engineering/design/principles.md)
    - [Порождающие паттерны](engineering/design/patterns-creational.md)
    - [Структурные паттерны](engineering/design/patterns-structural.md)
    - [Поведенческие паттерны](engineering/design/patterns-behavioral.md)
    - [Паттерны в Spring](engineering/design/patterns-in-spring.md)
    - [Слои приложения](engineering/design/layers.md)
    - [Монолит и микросервисы](engineering/design/monolith-vs-microservices.md)
    - [Проблемы распределённых систем и CAP](engineering/design/distributed-challenges.md)
    - [Устойчивость: таймауты, ретраи, circuit breaker](engineering/design/resilience.md)
    - [Согласованность данных и Saga](engineering/design/consistency-saga.md)
    - [Outbox и надёжная публикация событий](engineering/design/outbox.md)
    - [Идемпотентность и дедупликация](engineering/design/idempotency.md)
    - [Коммуникация сервисов](engineering/design/service-communication.md)
    - [Событийная архитектура](engineering/design/event-driven.md)

??? note "34. Тестирование"
    - [Виды тестов и пирамида](engineering/testing/test-types.md)
    - [JUnit 5](engineering/testing/junit5.md)
    - [Моки и Mockito](engineering/testing/mockito.md)
    - [Тестирование Spring-приложений](engineering/testing/spring-testing.md)
    - [Testcontainers](engineering/testing/testcontainers.md)
    - [Хорошие и плохие тесты](engineering/testing/good-tests.md)

??? note "35. Сборка проектов"
    - [Maven](engineering/build/maven.md)
    - [Gradle](engineering/build/gradle.md)
    - [Зависимости и конфликты](engineering/build/dependencies.md)

??? note "36. Git"
    - [Как устроен Git](engineering/git/how-git-works.md)
    - [Повседневная работа](engineering/git/daily-work.md)
    - [Командные процессы](engineering/git/team-workflow.md)
    - [Когда всё сломалось](engineering/git/recovery.md)

??? note "37. Инструменты и библиотеки"
    - [Lombok](engineering/tools/lombok.md)
    - [MapStruct](engineering/tools/mapstruct.md)
    - [Thymeleaf](engineering/tools/thymeleaf.md)
    - [Jackson](engineering/tools/jackson.md)
    - [Рабочие инструменты](engineering/tools/work-tools.md)
    - [ИИ-инструменты в разработке](engineering/tools/ai-tools.md)

## Инфраструктура

??? note "38. Docker"
    - [Контейнеры и образы](infrastructure/docker/containers-images.md)
    - [Dockerfile и сборка](infrastructure/docker/dockerfile.md)
    - [Запуск и compose](infrastructure/docker/compose.md)

??? note "39. Kubernetes"
    - [Зачем нужен Kubernetes](infrastructure/k8s/why-kubernetes.md)
    - [Основные объекты](infrastructure/k8s/objects.md)
    - [Приложение в кластере](infrastructure/k8s/app-in-cluster.md)

??? note "40. Linux"
    - [Основы работы в терминале](infrastructure/linux/terminal-basics.md)
    - [Процессы и ресурсы](infrastructure/linux/processes.md)
    - [Диагностика приложения на сервере](infrastructure/linux/diagnostics.md)

??? note "41. CI/CD"
    - [Что такое CI/CD](infrastructure/cicd/what-is-cicd.md)
    - [GitHub Actions и GitLab CI](infrastructure/cicd/pipelines.md)
    - [Выкатка](infrastructure/cicd/deployment.md)

## Мониторинг и наблюдаемость

??? note "42. Наблюдаемость и три столпа"
    - [Мониторинг и наблюдаемость](monitoring/observability/three-pillars.md)
    - [Здоровье сервиса](monitoring/observability/health.md)

??? note "43. Метрики"
    - [Метрики важные бэкендеру](monitoring/metrics/backend-metrics.md)
    - [Метрики из Spring](monitoring/metrics/spring-metrics.md)

??? note "44. Prometheus и алертинг"
    - [Как работает Prometheus](monitoring/prometheus/how-prometheus-works.md)
    - [PromQL — запросы к метрикам](monitoring/prometheus/promql.md)
    - [Алертинг](monitoring/prometheus/alerting.md)

??? note "45. Grafana и дашборды"
    - [Дашборды бэкендера](monitoring/grafana/dashboards.md)
    - [Разбор инцидента по графикам](monitoring/grafana/incident.md)

??? note "46. Распределённый трейсинг"
    - [Трейсинг запросов](monitoring/tracing/request-tracing.md)
    - [Инструменты трейсинга](monitoring/tracing/tools.md)

??? note "47. Логирование"
    - [Логирование в Java](monitoring/logging/logging-in-java.md)
    - [Что и как логировать](monitoring/logging/what-to-log.md)
    - [Логи в продакшене](monitoring/logging/logs-in-prod.md)

## Отдельные страницы

- [Вопросы с собеседований](interview-questions.md) — задачи и ситуации
  для самопроверки, вне структуры разделов
