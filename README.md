# Banking Application

## Как запустить проект

### Что нужно, чтобы всё заработало:
- Java 17+
- Maven
- PostgreSQL (я использовал 12+)
- Docker (если хотите прогонять тесты с Testcontainers)

### Запуск:

1. Создаёте базу:
```sql
CREATE DATABASE banking;
```

2. Настройте подключение к базе данных в `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/banking
    username: your_username
    password: your_password
```

3. Собираете и запускаете:
```bash
mvn clean install
mvn spring-boot:run
```

### Swagger-доки:
- UI: http://localhost:8080/swagger-ui.html
- JSON: http://localhost:8080/api-docs

## Что использовал

### Основной стек:
- Java 11
- Spring Boot
- PostgreSQL
- Maven

### Плюс дополнительно:
- JWT — аутентификация без сессий, через токен (включаю USER_ID в claims)
- Flyway — миграции базы
- Spring Cache — кэш на DAO и API слоях
- Swagger/OpenAPI — авто-дока к API
- Testcontainers — интеграционные тесты на живой PostgreSQL в Docker
- Lombok — чтобы меньше писать руками (геттеры, конструкторы и т.п.)

## Почему сделал так

### Авторизация и безопасность:
- JWT-токены — удобно, без сессий
- В переводах денег использую pessimistic locking — чтобы не было проблем при одновременных запросах
- Пароли шифруются через BCrypt

### Производительность:
- Добавил кэширование для юзеров и поиска
- Индексация по полям, по которым идёт фильтрация
- Ленивая загрузка (lazy loading), чтобы не тащить всё сразу из базы

### Целостность данных:
- Денежные операции обернуты в транзакции
- BALANCE никогда не уходит в минус (проверки и в коде, и на уровне базы)
- Добавлен контроль за избыточным начислением процентов (лимит 207% от начального баланса)

### Тестирование:
- Тесты крутятся в Docker с настоящей PostgreSQL (через Testcontainers) — чтобы всё было как в жизни

### Ошибки:
- Глобальный обработчик ошибок
- Возвращаю нормальные сообщения и коды, чтобы клиенту было понятно, что пошло не так










