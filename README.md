# qa-java-selenide-test

[![CI](https://github.com/USERNAME/qa-java-selenide-test/actions/workflows/ci.yml/badge.svg)](https://github.com/USERNAME/qa-java-selenide-test/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-blue)
![Selenide](https://img.shields.io/badge/Selenide-7.15.0-green)
![JUnit5](https://img.shields.io/badge/JUnit-5-25A162)
![Maven](https://img.shields.io/badge/Maven-3.x-C71A36)

Портфолио‑проект автоматизированного UI/SQL тестирования веб‑приложения [EpicVIN](https://epicvin.com/) на стеке **Java + Selenide + JUnit 5 + Allure + MySQL**.

> Демонстрационный набор тестов из основного приватного проекта. Содержит подобранные сценарии, показывающие архитектуру PageObject, параметризацию JUnit 5, работу с Allure, подключение к БД и интеграцию с CI (GitHub Actions + Jenkins).

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
├── .github/workflows/        # GitHub Actions: CI + Allure
├── .jenkins/                 # Демонстрационный Jenkinsfile
├── src/main/java/
│   ├── annotations/epicvin/  # Кастомные мета‑аннотации тестов (@EpicvinTest и др.)
│   ├── base/                 # Базовые классы для общих PageObject
│   ├── clearAccount/         # Утилиты предочистки тестовых аккаунтов
│   ├── constants/            # Загрузка конфигурации из .env
│   ├── decorator/            # JUnit 5 расширения (логи, префиксы вывода)
│   ├── enums/                # Перечисления (типы невалидных email и т.п.)
│   ├── generators/           # Генераторы тестовых данных
│   ├── interfaces/           # Контракты flow (Login, Register, Report, …)
│   ├── MD5hash/              # MD5 утилита для сверки токенов
│   ├── pages/epicvin/        # PageObject‑модель сайта
│   │   ├── Account/                  # Dashboard, MyReports, Subscriptions
│   │   ├── Authentication/           # Login, Register, ResetPassword
│   │   ├── Decoder/VinDecoder/       # VIN декодер
│   │   ├── Main/                     # Главная страница
│   │   ├── PaymentProcesses/         # FullReport / Dealer processor pages
│   │   ├── Precheck/                 # Чекаут перед оплатой
│   │   ├── Price/                    # Прайсинг
│   │   ├── Report/                   # Report, ReportInaccuracy, SampleVin
│   │   └── SocialReviews/            # Google / Trustpilot
│   ├── payments/             # Обработчики платёжных шлюзов (PayPal, Yuno)
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

`.env.example` сгруппирован по секциям: Base URLs, internal cleanup endpoints, test VINs, test accounts (включая `TEST_EMAIL_BASE` для генерации email), test cards, PayPal, БД, sensitive test data (`TEST_USER_IDS`, `EXCLUDED_AUCTIONS`), basic auth для dev окружения.

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

### 4. Allure отчёт локально
```bash
mvn allure:serve     # сгенерировать и открыть в браузере
mvn allure:report    # сгенерировать в target/site/allure-maven-plugin
```

## CI/CD

### GitHub Actions
Два workflow в `.github/workflows/`:

| Workflow | Триггер | Что делает |
|---|---|---|
| `ci.yml` | `push: main`, `pull_request: main`, `workflow_dispatch` | На любом событии — `mvn clean compile test-compile`. Запуск тестов c secrets — **только** на `push: main` и `workflow_dispatch` (т.е. не на PR), и только если в репозитории включена переменная `RUN_TESTS=true` |
| `allure-report.yml` | после успешного `CI` / manual | Запускает тесты, генерирует Allure отчёт, складывает в `gh-pages` |

Чтобы тесты реально запускались в CI:
1. **Settings → Secrets and variables → Actions → Variables** — добавь `RUN_TESTS=true`.
2. **Settings → Secrets and variables → Actions → Secrets** — заведи нужные secrets из `.env.example` (`BASE_URL_EPICVIN`, `VALID_EMAIL`, `VALID_PASSWORD`, `DATABASE_URL_EPICVIN`, `DATABASE_USER`, `DATABASE_PASSWORD` и т.д.).

> На PR из любого репозитория тесты с secrets не запускаются — только компиляция. Это защита от утечки secrets через подменённый PR-код.

### Jenkins
В `.jenkins/Jenkinsfile` — декларативный pipeline с параметрами:
- `RUN_TESTS` — фактический запуск тестов
- `HEADLESS` — режим браузера
- `TEST_GROUP` — JUnit 5 тег

Подразумевает Jenkins‑credential `qa-java-selenide-test-env` (file) с содержимым `.env`.

## Allure отчёт

Публичная ссылка на Allure намеренно **не публикуется** — тесты бегут на проде, скриншоты могут засветить тестовые данные и внутренние эндпоинты.

Получить отчёт можно так:
- **Локально**: `mvn allure:serve` после `mvn test`.
- **Из CI run**: артефакт `allure-results` прикладывается к каждому запуску `ci.yml` (если `RUN_TESTS=true`). Открывается через UI GitHub Actions → Run → Artifacts (доступ только авторизованным).
- **При личной демонстрации**: генерится локально, шарится экран.

Из соображений безопасности в `BaseTest.java` отключён `savePageSource` — Allure складывает только скриншоты, без HTML‑дампов страниц.

## Что внутри

### Тесты в `src/test/java/epicvin/`

12 тестовых файлов / 51 метод / 65 фактических прогонов JUnit (с учётом параметризации).

| Раздел | Класс | Покрытие |
|---|---|---|
| Smoke / Главная | `Main/MainPageTest` | Хедер, футер, поиск, баннеры, языки |
| Аутентификация | `Authentication/LoginPageTest` | Вход, валидации, ошибки |
| Аутентификация | `Authentication/RegistrationPageTest` | Регистрация + 15 типов невалидных email (параметризация) |
| Аутентификация | `Authentication/ResetPasswordPageTest` | Восстановление пароля |
| Decoder | `Decoder/VinDecoder/VinDecoderPageTest` | VIN декодер |
| Precheck | `Precheck/PrecheckPageTest` | Чекаут перед оплатой |
| Report | `Report/ReportPageTest` | Просмотр отчёта по VIN |
| Report | `Report/SampleVinPageTest` | Sample‑отчёт |
| Account | `Account/DashboardPageTest` | Дашборд пользователя |
| Account | `Account/MyReportsPageTest` | Список отчётов |
| Account | `Account/SubscriptionsPageTest` | Подписки |
| SQL | `Sql/SqlEpicvinTest` | Прямые проверки в БД (`getEndAt`, `getNextPaymentDate*`, `getDoubleSub`, `getDoubleTrials`, лимиты репортов и т.д.) |

### Что демонстрирует код
- **PageObject** — изолированные классы страниц с приватными локаторами и публичным API.
- **Custom annotations** — мета‑аннотации `@EpicvinTest`, `@EpicvinPaymentTest`, `@EpicvinSqlTest`, `@EpicvinRegistrationTest` объединяют `@Test`/`@ParameterizedTest`, `@Tag`, `@ExtendWith`, `@Execution`.
- **Parameterized tests** — `@EpicvinRegistrationTest` запускает один метод с 15 вариантами невалидного email (`@ValueSource`).
- **JUnit 5 Extensions** — `LogsExtension` перехватывает `System.out`/`err` и добавляет префикс `[testName] [project]` (нужно для параллельного запуска).
- **Selenide listener** — Allure прикладывает скриншоты на падениях (без HTML page source — отключено для безопасности).
- **Параллельный запуск** — `junit-platform.properties` (fixed, 4 потока), отдельные тесты помечены `@Execution(SAME_THREAD)` (платёжные).
- **SQL слой** — пул соединений `ThreadLocal<Map<String, Connection>>`, SQL запросы выносятся в `.sql` ресурсы и подгружаются через `loadSqlQueryEpicvin(...)`.
- **Подстановка чувствительных данных в SQL** — в SQL файлах используются плейсхолдеры (`${TEST_USER_IDS}`, `${EXCLUDED_AUCTIONS}`), которые загрузчик заменяет на значения из `.env`. Списки тестовых юзеров и аукционов не попадают в публичный репозиторий.
- **Платёжные шлюзы** — отдельные handler‑классы для PayPal и Yuno (изолируют логику работы с iframe и 3DS).

---

> Реальные тестовые данные и эндпоинты эксклюзивны для приватной среды. После клонирования проект скомпилируется, но тесты не запустятся без своего `.env` — см. `.env.example`. Публичная ссылка на Allure намеренно не публикуется.
