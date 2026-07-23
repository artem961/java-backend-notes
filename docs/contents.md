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
    - Что такое JPA и ORM
    - Сущности и маппинг
    - Persistence context
    - Связи между сущностями
    - Ленивая загрузка и N+1
    - Запросы
    - Блокировки и конкурентный доступ
    - Кэш второго уровня

??? note "19. Миграции БД"
    - Зачем нужны миграции
    - Flyway и Liquibase
    - Миграции на живой базе

??? note "20. Redis"
    - Что такое Redis
    - Кэширование: стратегии
    - Проблемы кэширования
    - Redis не только кэш
    - Redis в Spring
    - Персистентность и надёжность

??? note "21. S3 и MinIO"
    - Объектное хранилище
    - S3 и MinIO
    - Работа из приложения
    - Presigned URL

## Взаимодействие

??? note "22. HTTP"
    - Как работает HTTP
    - Методы и коды ответа
    - Заголовки и тело
    - Куки и сессии
    - Кэширование в HTTP
    - TLS и HTTPS
    - Обратный прокси и балансировщик
    - CORS

??? note "23. REST"
    - Что такое REST
    - Проектирование ресурсов и данных
    - Ошибки и статусы
    - Идемпотентность и надёжность
    - Версионирование и эволюция

??? note "24. gRPC"
    - Что такое gRPC и зачем
    - Protobuf и контракт
    - Виды вызовов
    - gRPC на практике

??? note "25. WebSocket"
    - Что такое WebSocket
    - Установка соединения
    - WebSocket в Spring
    - Эксплуатация и ограничения

??? note "26. SSE"
    - Что такое SSE
    - SSE в Spring
    - Что выбрать: WebSocket, SSE или Long Polling

??? note "27. Long Polling"
    - Как устроен long polling

??? note "28. SMTP"
    - Как работает почта
    - Отправка писем из приложения

??? note "29. Kafka"
    - Что такое Kafka и зачем
    - Устройство
    - Продюсер
    - Консьюмер
    - Гарантии доставки
    - Kafka в Spring

??? note "30. Avro и Schema Registry"
    - Зачем нужна схема
    - Avro
    - Schema Registry
    - Эволюция схем

??? note "31. OpenAPI и Swagger"
    - Что такое OpenAPI
    - Code-first и contract-first

??? note "32. TypeSpec"
    - Что такое TypeSpec
    - Генерация и процесс работы

## Инженерия

??? note "33. Проектирование и паттерны"
    - SOLID
    - Остальные принципы
    - Основные паттерны GoF
    - Паттерны в Spring
    - Слои приложения
    - Монолит и микросервисы
    - Надёжность распределённых систем
    - Согласованность данных

??? note "34. Тестирование"
    - Виды тестов и подходы
    - JUnit 5
    - Моки и Mockito
    - Тестирование Spring-приложений
    - Testcontainers
    - Хорошие и плохие тесты

??? note "35. Сборка проектов"
    - Maven
    - Gradle
    - Зависимости и конфликты

??? note "36. Git"
    - Как устроен Git
    - Повседневная работа
    - Командные процессы
    - Когда всё сломалось

??? note "37. Инструменты и библиотеки"
    - Lombok
    - MapStruct
    - Thymeleaf
    - Jackson
    - Рабочие инструменты
    - ИИ-инструменты в разработке

## Инфраструктура

??? note "38. Docker"
    - Контейнеры и образы
    - Dockerfile и сборка
    - Запуск и compose

??? note "39. Kubernetes"
    - Зачем нужен Kubernetes
    - Основные объекты
    - Приложение в кластере

??? note "40. Linux"
    - Основы работы в терминале
    - Процессы и ресурсы
    - Диагностика приложения на сервере

??? note "41. CI/CD"
    - Что такое CI/CD
    - GitHub Actions и GitLab CI
    - Выкатка

## Мониторинг и наблюдаемость

??? note "42. Наблюдаемость и микросервисы"
    - Что такое наблюдаемость
    - Мониторинг распределённой системы
    - Здоровье сервиса

??? note "43. Метрики приложения и бизнес-метрики"
    - Метрики приложения
    - Как отдавать метрики из Spring
    - Бизнес-метрики

??? note "44. Prometheus и алертинг"
    - Как работает Prometheus
    - Запросы к метрикам
    - Алертинг

??? note "45. Grafana"
    - Дашборды
    - Разбор инцидента по графикам

??? note "46. Логирование"
    - Логирование в Java
    - Что и как логировать
    - Логи в продакшене

## Отдельные страницы

- [Вопросы с собеседований](interview-questions.md) — задачи и ситуации
  для самопроверки, вне структуры разделов
