# Обработка ошибок

Ошибки — часть контракта API: клиент должен получать предсказуемый статус
и полезное тело, а не стектрейс или голый 500. В Spring MVC для этого есть
центральный механизм — `@ControllerAdvice`.

## Что происходит без обработчика

Исключение из контроллера летит вверх до Boot'овского обработчика
по умолчанию: клиент получает 500 с безликим JSON. Спринговые исключения
маппятся сами (нет маппинга — 404/405, кривое тело — 400), но доменные
исключения приложения без обработчика — всегда 500.

## @ControllerAdvice: центральная точка

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    ProblemDetail notFound(OrderNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail validation(MethodArgumentNotValidException e) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setProperty("errors", e.getFieldErrors().stream()
            .map(f -> f.getField() + ": " + f.getDefaultMessage()).toList());
        return pd;
    }

    @ExceptionHandler(Exception.class)                 // последний рубеж
    ProblemDetail unexpected(Exception e) {
        log.error("Unhandled exception", e);           // логируем с трейсом
        return ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

- `@RestControllerAdvice` — глобальный перехватчик для всех контроллеров;
  `@ExceptionHandler` объявляет, какой тип исключений ловит метод
  (выбирается самый специфичный).
- Логика проста: **доменное исключение → HTTP-статус + тело**. Сервисы
  бросают свои исключения, ничего не зная про HTTP; веб-слой переводит.
- Обработчик `Exception.class` — последний рубеж: неожиданные ошибки
  логируются со стектрейсом, клиенту — 500 без деталей (внутренности
  наружу не отдаём — и по безопасности, и по контракту).

## ProblemDetail — стандартный формат ошибки

Spring 6 / Boot 3 поддерживают RFC 7807 (Problem Details) из коробки:
единый формат тела ошибки вместо самодельных `ErrorResponse`:

```json
{
  "type": "about:blank",
  "title": "Not Found",
  "status": 404,
  "detail": "Заказ 42 не найден",
  "instance": "/api/orders/42"
}
```

Свой формат тоже допустим — важно, чтобы он был **единым по всему API**
и содержал машиночитаемый код ошибки для клиента.

## Какие статусы отдавать

Практичный маппинг доменных ситуаций:

| Ситуация | Статус |
|---|---|
| Кривой запрос: формат, валидация | 400 |
| Не аутентифицирован / нет прав | 401 / 403 |
| Ресурс не найден | 404 |
| Конфликт состояния (повторная отмена заказа, дубликат) | 409 |
| Ошибка бизнес-правила над валидными данными | 422 |
| Неожиданная ошибка сервера | 500 |

Ошибки валидации отдают **списком по полям** — клиент подсветит форму,
а не будет гадать по одной строке.

## Что ещё стоит знать

- `@ExceptionHandler` можно объявить и внутри контроллера — тогда он
  действует только на него; глобальный advice — обычный выбор.
- `@ResponseStatus(HttpStatus.NOT_FOUND)` прямо на классе исключения —
  быстрый способ без advice, но формат тела не контролирует.
- Исключения из **фильтров** (например, Security) до `@ControllerAdvice`
  не доходят — они выброшены до Spring MVC; у Security свои обработчики
  (`AuthenticationEntryPoint`, `AccessDeniedHandler`).

## Как ответить на интервью

Коротко: центральная обработка — `@RestControllerAdvice`
с `@ExceptionHandler`-методами: доменные исключения переводятся
в HTTP-статусы и единый формат тела (стандартный — ProblemDetail,
RFC 7807), валидация — 400 со списком ошибок по полям, последний рубеж —
обработчик `Exception` с логированием и «пустым» 500. Сервисы бросают
доменные исключения и не знают про HTTP; перевод — забота веб-слоя.
Нюанс: исключения из сервлетных фильтров (Security) до advice не доходят.
