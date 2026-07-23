# Redis в Spring

Два уровня работы с Redis из Spring: высокоуровневый **Spring Cache**
(аннотации, кэш «прозрачно») и низкоуровневый **RedisTemplate** (прямой доступ
к структурам).

## Spring Cache: аннотации

Абстракция кэша поверх любого провайдера (Redis, Caffeine). Включается
`@EnableCaching`, дальше — аннотации на методах:

```java
@Cacheable(value = "users", key = "#id")
public User getUser(Long id) { ... }   // cache-aside автоматически

@CacheEvict(value = "users", key = "#user.id")
public void update(User user) { ... }  // инвалидация при записи
```

- **`@Cacheable`** — реализует cache-aside: есть в кэше → вернуть, нет →
  выполнить метод и положить результат. Ручной код заглядывания в кэш не
  нужен.
- **`@CacheEvict`** — удалить ключ (инвалидация при изменении).
- **`@CachePut`** — выполнить метод и обновить кэш его результатом.

Важная деталь: `@Cacheable` работает через **AOP-прокси** — те же грабли, что
у `@Transactional`: самовызов внутри класса кэш не задействует, метод должен
вызываться через бин (см. раздел про AOP). И конфигурируют TTL и сериализацию
на кэш-менеджере — по умолчанию TTL нет, ключи живут вечно, это надо задать.

## RedisTemplate: прямой доступ

Когда нужны сами структуры Redis (счётчики, sorted set, TTL вручную, свои
ключи) — `RedisTemplate` / `StringRedisTemplate`:

```java
redisTemplate.opsForValue().increment("views:" + id);
redisTemplate.opsForValue().set("k", v, Duration.ofMinutes(10));
redisTemplate.opsForZSet().add("leaderboard", user, score);
```

`opsForValue`/`opsForHash`/`opsForList`/`opsForZSet` — доступ к каждому типу.
Так реализуют счётчики, rate limiting, лидерборды — то, что не покрывается
аннотациями кэша.

## Клиенты и сериализация

- Клиент по умолчанию в Spring Boot — **Lettuce** (неблокирующий, на Netty),
  реже Jedis.
- **Сериализация**: по умолчанию JDK-сериализация — нечитаемо и хрупко. Обычно
  настраивают JSON (`GenericJackson2JsonRedisSerializer`), чтобы значения в
  Redis были человекочитаемыми и совместимыми между версиями.

## Как ответить на интервью

Коротко: два уровня. Spring Cache — аннотации `@Cacheable`/`@CacheEvict`/
`@CachePut` поверх Redis: `@Cacheable` даёт cache-aside автоматически, без
ручного кода; работает через AOP-прокси (те же грабли самовызова, что у
`@Transactional`), TTL и JSON-сериализацию надо настроить явно. RedisTemplate
— прямой доступ к структурам (`opsForValue`/`opsForZSet`) для счётчиков, rate
limiting, лидербордов. Клиент по умолчанию — Lettuce, сериализацию меняют с
JDK на JSON.
