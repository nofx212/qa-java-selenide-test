# qa-java-selenide-test

[![CI](https://github.com/USERNAME/qa-java-selenide-test/actions/workflows/ci.yml/badge.svg)](https://github.com/USERNAME/qa-java-selenide-test/actions/workflows/ci.yml)
[![Allure Report](https://img.shields.io/badge/Allure-Report-orange)](https://USERNAME.github.io/qa-java-selenide-test/)
![Java](https://img.shields.io/badge/Java-21-blue)
![Selenide](https://img.shields.io/badge/Selenide-7.15.0-green)
![JUnit5](https://img.shields.io/badge/JUnit-5-25A162)
![Maven](https://img.shields.io/badge/Maven-3.x-C71A36)

Портфолио‑проект автоматизированного UI/SQL тестирования веб‑приложения [EpicVIN](https://epicvin.com/) на стеке **Java + Selenide + JUnit 5 + Allure + MySQL**.

> Демонстрационный набор лучших тестов из основного приватного проекта. Содержит подобранные сценарии, показывающие архитектуру PageObject, параметризацию JUnit5, работу с Allure, подключение к БД и интеграцию с CI (GitHub Actions + Jenkins).

---

## Содержание
- [Стек](#стек)
- [Структура проекта](#структура-проекта)
- [Запуск локально](#запуск-локально)
- [CI/CD](#cicd)
- [Allure отчёт](#allure-отчёт)
- [Что внутри](#что-внутри)

## Стек

| Категория | Технология |
|---|---|
| Язык | Java 21 |
| Сборка | Maven |
| UI Framework | Selenide 7.15.0 |
| Test Runner | JUnit 5 (Jupiter) |
| Параметризация | `@ParameterizedTest`, `@ValueSource` |
| Параллелизм | `junit-platform.properties` (fixed, 4 потока) |
| Отчёты | Allure 2.30 |
| БД | MySQL (mysql‑connector‑j) |
| Драйвер | WebDriverManager |
| Конфиги | dotenv‑java |
| HTTP | Apache HttpClient 5 |
| Утилиты | AssertJ, commons‑lang3, SLF4J |

## Структура проекта

```
qa-java-selenide-test/
├── .github/workflows/        # GitHub Actions: CI + публикация Allure
├── .jenkins/                 # Демонстрационный Jenkinsfile
├── src/main/java/
│   ├── annotations/epicvin/  # Кастомные мета‑аннотации тестов (@EpicvinTest и др.)
│   ├── base/                 # Базовые классы для общих PageObject
│   ├── clearAccount/         # Утилиты предочистки тестовых аккаунтов
│   ├── constants/            # Загрузка конфигурации из .env
│   ├── decorator/            # JUnit5 расширения (логи, префиксы вывода)
│   ├── enums/                # Перечисления (типы невалидных email и т.п.)
│   ├── generators/           # Генераторы тестовых данных
│   ├── interfaces/           # Контракты flow (Login, Register, Report, …)
│   ├── MD5hash/              # MD5 утилита для сверки токенов
│   ├── pages/epicvin/        # PageObject‑модель сайта
│   ├── payments/             # Обработчики платёжных шлюзов (Stripe, PayPal, Yuno)
│   └── sql/                  # Пул соединений MySQL и набор SQL запросов
├── src/main/resources/
│   └── sqlEpicvin/           # SQL запросы (loadSqlQuery)
├── src/test/java/
│   ├── base/BaseTest.java    # Конфигурация Selenide + Allure listener
│   └── epicvin/              # Тестовые наборы по разделам сайта
└── src/test/resources/
    ├── allure.properties
    └── junit-platform.properties
```

## Запуск локально

### 1. Требования
- Java **21**
- Maven **3.8+**
- Chrome (driver подтягивается автоматически через WebDriverManager)
- MySQL (только для SQL тестов)

### 2. Конфигурация
Скопируй шаблон и заполни значения под свою тестовую среду:
```bash
cp .env.example .env
```

### 3. Команды Maven
```bash
# Все тесты
mvn clean test

# Только тесты с тегом epicvin
mvn test -Dgroups=epicvin

# Конкретный класс
mvn test -Dtest=MainPageTest

# Headless режим (для CI)
mvn test -Dheadless=true
```

### 4. Allure отчёт
```bash
mvn allure:serve     # сгенерировать и открыть в браузере
mvn allure:report    # сгенерировать в target/site/allure-maven-plugin
```

## CI/CD

### GitHub Actions
Два workflow в `.github/workflows/`:

| Workflow | Триггер | Что делает |
|---|---|---|
| `ci.yml` | push, PR, manual | `mvn clean compile test-compile`. Опционально запускает тесты, если в репозитории включена переменная `RUN_TESTS=true` и заданы Secrets |
| `allure-report.yml` | после успешного CI / manual | Запускает тесты, генерирует Allure отчёт и публикует на ветку `gh-pages` |

Чтобы тесты реально запускались в CI:
1. В **Settings → Secrets and variables → Actions** добавь переменную `RUN_TESTS = true`.
2. Добавь Secrets: `BASE_URL_EPICVIN`, `VALID_EMAIL`, `VALID_PASSWORD`, `DATABASE_URL_EPICVIN`, `DATABASE_USER`, `DATABASE_PASSWORD` и др.

### Jenkins
В `.jenkins/Jenkinsfile` — декларативный pipeline с параметрами:
- `RUN_TESTS` — фактический запуск тестов
- `HEADLESS` — режим браузера
- `TEST_GROUP` — JUnit5 тег

Подразумевает Jenkins‑credential `qa-java-selenide-test-env` (file) с содержимым `.env`.

## Allure отчёт

После запуска CI публичный отчёт доступен по адресу:
```
https://USERNAME.github.io/qa-java-selenide-test/
```
*(замени `USERNAME` на свой ник)*

## Что внутри

### Тесты в `src/test/java/epicvin/`

| Раздел | Класс | Покрытие |
|---|---|---|
| Smoke / Главная | `Main/MainPageTest` | Хедер, футер, поиск, баннеры |
| Аутентификация | `Authentication/LoginPageTest` | Вход, валидации, ошибки |
| Аутентификация | `Authentication/RegistrationPageTest` | 15 типов невалидных email (параметризация) |
| Аутентификация | `Authentication/ResetPasswordPageTest` | Восстановление пароля |
| Precheck | `Precheck/PrecheckPageTest` | Чекаут перед оплатой |
| Precheck | `Precheck/VinNotFoundPageTest` | Сценарий отсутствующего VIN |
| Report | `Report/ReportPageTest` | Просмотр отчёта по VIN |
| Report | `Report/SampleVinPageTest` | Sample‑отчёт |
| Cars | `Cars/AdvancedSearchPageTest` | Поиск с фильтрами |
| Cars | `Cars/LotPageTest` | Карточка лота |
| Footer | `Footer/VinDecoder/VinDecoderPageTest` | VIN декодер |
| Footer | `Footer/RecallCheck/RecallCheckPageTest` | Recall чек |
| Account | `Account/DashboardPageTest` | Дашборд пользователя |
| Account | `Account/MyReportsPageTest` | Список отчётов |
| Account | `Account/BillingPageTest` | Платежи (Stripe / PayPal / Yuno) |
| Account | `Account/SubscriptionsPageTest` | Подписки |
| SQL | `Sql/SqlEpicvinTest` | Прямые проверки в БД (`getEndAt`, `getNextPaymentDate*`, `getDoubleSub` и др.) |

### Что демонстрирует код
- **PageObject** — изолированные классы страниц с приватными локаторами и публичным API
- **Custom annotations** — мета‑аннотации `@EpicvinTest`, `@EpicvinPaymentTest`, `@EpicvinSqlTest` объединяют `@Test`, `@Tag`, `@ExtendWith`, `@Execution`
- **Parameterized tests** — `@EpicvinRegistrationTest` запускает один метод с 15 вариантами невалидного email
- **JUnit5 Extensions** — `LogsExtension` перехватывает stdout и добавляет префикс с именем теста (нужно для параллельного запуска)
- **Selenide listener** — Allure прикладывает скриншоты и page source при падениях
- **Параллельный запуск** — `junit-platform.properties` (4 потока), отдельные тесты помечены `@Execution(SAME_THREAD)` (платёжные)
- **SQL слой** — пул соединений `ThreadLocal<Map<String, Connection>>`, SQL запросы выносятся в `.sql` ресурсы и подгружаются по имени
- **Платёжные шлюзы** — отдельные handler‑классы для Stripe, PayPal, Yuno (изолируют логику работы с iframe и 3DS)

---

> Реальные тестовые данные и эндпоинты эксклюзивны для приватной среды. После клонирования проект скомпилируется, но тесты не запустятся без своего `.env`. См. `.env.example`.
