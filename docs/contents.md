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
    - Зачем нужен Spring Boot
    - Автоконфигурация
    - Конфигурация приложения
    - Запуск и жизненный цикл приложения

??? note "11. Spring MVC"
    - Путь запроса
    - Контроллеры и маппинг параметров
    - Сериализация и валидация
    - Обработка ошибок
    - Фильтры и интерсепторы

??? note "12. Spring AOP и транзакции"
    - AOP и прокси
    - Транзакции в Spring
    - Распространение и изоляция
    - Где `@Transactional` ломается

??? note "13. Spring Security"
    - Как устроен Spring Security
    - Аутентификация
    - Авторизация
    - Сессии, токены и JWT
    - OAuth2 и внешние провайдеры

??? note "14. Spring Data JPA"
    - Репозитории
    - Запросы
    - Пагинация и сортировка
    - Модификация данных

??? note "15. Spring Cloud Gateway"
    - Зачем нужен шлюз
    - Маршрутизация
    - Фильтры
    - Реактивность и эксплуатация

## Данные

??? note "16. БД и SQL"
    - Реляционная модель
    - Выборка данных
    - Агрегация и группировка
    - Изменение данных и DDL
    - Индексы
    - Транзакции и изоляция
    - Блокировки
    - Производительность запросов

??? note "17. JPA и Hibernate"
    - Что такое JPA и ORM
    - Сущности и маппинг
    - Persistence context
    - Связи между сущностями
    - Ленивая загрузка и N+1
    - Запросы
    - Кэш второго уровня

??? note "18. Миграции БД"
    - Зачем нужны миграции
    - Flyway и Liquibase
    - Миграции на живой базе

??? note "19. Redis"
    - Что такое Redis
    - Кэширование: стратегии
    - Проблемы кэширования
    - Redis не только кэш
    - Redis в Spring
    - Персистентность и надёжность

??? note "20. S3 и MinIO"
    - Объектное хранилище
    - S3 и MinIO
    - Работа из приложения
    - Presigned URL

## Взаимодействие

??? note "21. HTTP"
    - Как работает HTTP
    - Методы и коды ответа
    - Заголовки и тело
    - Куки и сессии
    - Кэширование в HTTP
    - TLS и HTTPS
    - Обратный прокси и балансировщик
    - CORS

??? note "22. REST"
    - Что такое REST
    - Проектирование ресурсов и данных
    - Ошибки и статусы
    - Идемпотентность и надёжность
    - Версионирование и эволюция

??? note "23. gRPC"
    - Что такое gRPC и зачем
    - Protobuf и контракт
    - Виды вызовов
    - gRPC на практике

??? note "24. WebSocket"
    - Что такое WebSocket
    - Установка соединения
    - WebSocket в Spring
    - Эксплуатация и ограничения

??? note "25. SSE"
    - Что такое SSE
    - SSE в Spring
    - Что выбрать: WebSocket, SSE или Long Polling

??? note "26. Long Polling"
    - Как устроен long polling

??? note "27. SMTP"
    - Как работает почта
    - Отправка писем из приложения

??? note "28. Kafka"
    - Что такое Kafka и зачем
    - Устройство
    - Продюсер
    - Консьюмер
    - Гарантии доставки
    - Kafka в Spring

??? note "29. Avro и Schema Registry"
    - Зачем нужна схема
    - Avro
    - Schema Registry
    - Эволюция схем

??? note "30. OpenAPI и Swagger"
    - Что такое OpenAPI
    - Code-first и contract-first

??? note "31. TypeSpec"
    - Что такое TypeSpec
    - Генерация и процесс работы

## Инженерия

??? note "32. Проектирование и паттерны"
    - SOLID
    - Остальные принципы
    - Основные паттерны GoF
    - Паттерны в Spring
    - Слои приложения
    - Монолит и микросервисы
    - Надёжность распределённых систем
    - Согласованность данных

??? note "33. Тестирование"
    - Виды тестов и подходы
    - JUnit 5
    - Моки и Mockito
    - Тестирование Spring-приложений
    - Testcontainers
    - Хорошие и плохие тесты

??? note "34. Сборка проектов"
    - Maven
    - Gradle
    - Зависимости и конфликты

??? note "35. Git"
    - Как устроен Git
    - Повседневная работа
    - Командные процессы
    - Когда всё сломалось

??? note "36. Инструменты и библиотеки"
    - Lombok
    - MapStruct
    - Thymeleaf
    - Jackson
    - Рабочие инструменты
    - ИИ-инструменты в разработке

## Инфраструктура

??? note "37. Docker"
    - Контейнеры и образы
    - Dockerfile и сборка
    - Запуск и compose

??? note "38. Kubernetes"
    - Зачем нужен Kubernetes
    - Основные объекты
    - Приложение в кластере

??? note "39. Linux"
    - Основы работы в терминале
    - Процессы и ресурсы
    - Диагностика приложения на сервере

??? note "40. CI/CD"
    - Что такое CI/CD
    - GitHub Actions и GitLab CI
    - Выкатка

## Мониторинг и наблюдаемость

??? note "41. Наблюдаемость и микросервисы"
    - Что такое наблюдаемость
    - Мониторинг распределённой системы
    - Здоровье сервиса

??? note "42. Метрики приложения и бизнес-метрики"
    - Метрики приложения
    - Как отдавать метрики из Spring
    - Бизнес-метрики

??? note "43. Prometheus и алертинг"
    - Как работает Prometheus
    - Запросы к метрикам
    - Алертинг

??? note "44. Grafana"
    - Дашборды
    - Разбор инцидента по графикам

??? note "45. Логирование"
    - Логирование в Java
    - Что и как логировать
    - Логи в продакшене

## Отдельные страницы

- [Вопросы с собеседований](interview-questions.md) — задачи и ситуации
  для самопроверки, вне структуры разделов
