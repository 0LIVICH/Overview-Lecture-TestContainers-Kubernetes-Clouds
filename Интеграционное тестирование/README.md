# Интеграционное тестирование Spring Boot приложения

Этот проект демонстрирует интеграционное тестирование Spring Boot приложения с использованием TestContainers и Docker.

## Описание проекта

Проект содержит Spring Boot приложение с двумя конфигурациями:
- **DEV окружение**: порт 8080, профиль dev
- **PROD окружение**: порт 8081, профиль prod

## Структура проекта

```
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── DemoApplication.java
│   │   │   └── HelloController.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-prod.properties
│   └── test/
│       └── java/com/example/demo/
│           └── DemoApplicationTests.java
├── build.gradle
├── Dockerfile
└── README.md
```

## Сборка проекта

### Шаг 1: Сборка JAR файла
```bash
./gradlew clean build -x test
```

### Шаг 2: Создание Docker образов

#### Dev образ (порт 8080):
1. Убедитесь, что в `application.properties` установлено:
   ```
   server.port=8080
   netology.profile.dev=true
   ```
2. Соберите приложение: `./gradlew clean build -x test`
3. Скопируйте JAR файл: `cp "build/libs/Интеграционное тестирование-0.0.1-SNAPSHOT.jar" build/libs/demo-0.0.1-SNAPSHOT.jar`
4. Соберите образ: `docker build -t devapp .`

#### Prod образ (порт 8081):
1. Убедитесь, что в `application.properties` установлено:
   ```
   server.port=8081
   netology.profile.dev=false
   ```
2. Соберите приложение: `./gradlew clean build -x test`
3. Скопируйте JAR файл: `cp "build/libs/Интеграционное тестирование-0.0.1-SNAPSHOT.jar" build/libs/demo-0.0.1-SNAPSHOT.jar`
4. Обновите Dockerfile (EXPOSE 8081)
5. Соберите образ: `docker build -t prodapp .`

## Запуск контейнеров

```bash
# Запуск dev контейнера
docker run -d -p 8080:8080 --name dev-container devapp

# Запуск prod контейнера
docker run -d -p 8081:8081 --name prod-container prodapp
```

## Тестирование

### Ручное тестирование
```bash
# Тест dev окружения
curl http://localhost:8080/
# Ожидаемый ответ: "Hello from DEV environment!"

# Тест prod окружения
curl http://localhost:8081/
# Ожидаемый ответ: "Hello from PROD environment!"
```

### Автоматическое тестирование
```bash
./gradlew clean test
```

## Интеграционные тесты

Проект содержит интеграционные тесты, которые:
1. Запускают Docker контейнеры с dev и prod образами
2. Выполняют HTTP запросы к приложениям
3. Проверяют корректность ответов

Тесты находятся в `src/test/java/com/example/demo/DemoApplicationTests.java` и используют:
- TestContainers для управления Docker контейнерами
- Spring Boot Test для интеграционного тестирования
- TestRestTemplate для HTTP запросов

## Зависимости

- Spring Boot 3.2.0
- TestContainers 1.19.3
- JUnit 5
- Gradle 8.5

## Требования

- Java 21
- Docker Desktop
- Gradle (или Gradle Wrapper) 