# qa-java-selenide-test

[![CI](https://github.com/nofx212/qa-java-selenide-test/actions/workflows/ci.yml/badge.svg)](https://github.com/nofx212/qa-java-selenide-test/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-blue)
![Selenide](https://img.shields.io/badge/Selenide-7.15.0-green)
![JUnit5](https://img.shields.io/badge/JUnit-5-25A162)
![Maven](https://img.shields.io/badge/Maven-3.x-C71A36)

A portfolio project for automated UI and SQL testing of the [EpicVIN](https://epicvin.com/) web application. Stack: **Java + Selenide + JUnit 5 + Allure + MySQL**.

> A curated set of tests from a larger private project. It shows the PageObject architecture, JUnit 5 parameterization, Allure reporting, database access and CI integration (GitHub Actions + Jenkins).

---

## Table of contents
- [Stack](#stack)
- [Project structure](#project-structure)
- [Local run](#local-run)
- [CI/CD](#cicd)
- [Allure report](#allure-report)
- [What is inside](#what-is-inside)

## Stack

| Category | Technology |
|---|---|
| Language | Java 21 |
| Build | Maven |
| UI Framework | Selenide 7.15.0 |
| Test Runner | JUnit 5 (Jupiter) |
| Parameterization | `@ParameterizedTest`, `@ValueSource` |
| Parallelism | `junit-platform.properties` (fixed, 4 threads) |
| Reports | Allure 2.30 |
| Database | MySQL (mysql‑connector‑j) |
| Driver | WebDriverManager |
| Config | dotenv‑java |
| HTTP | Apache HttpClient 5 |
| Utilities | AssertJ, commons‑lang3, SLF4J |

## Project structure

```
qa-java-selenide-test/
├── .github/workflows/        # GitHub Actions: CI + Allure
├── .jenkins/                 # Jenkinsfile (demo)
├── src/main/java/
│   ├── annotations/epicvin/  # Custom test meta-annotations (@EpicvinTest etc.)
│   ├── base/                 # Base classes for shared PageObjects
│   ├── clearAccount/         # Test account cleanup utilities
│   ├── constants/            # .env-based configuration
│   ├── decorator/            # JUnit 5 extensions (logs, output prefixes)
│   ├── enums/                # Enums (invalid email types, etc.)
│   ├── generators/           # Test data generators
│   ├── interfaces/           # Flow contracts (Login, Register, Report, …)
│   ├── MD5hash/              # MD5 helper for token checks
│   ├── pages/epicvin/        # PageObject model
│   │   ├── Account/                  # Dashboard, MyReports, Subscriptions
│   │   ├── Authentication/           # Login, Register, ResetPassword
│   │   ├── Decoder/VinDecoder/       # VIN decoder
│   │   ├── Main/                     # Home page
│   │   ├── PaymentProcesses/         # FullReport / Dealer processor pages
│   │   ├── Precheck/                 # Pre-payment checkout
│   │   ├── Price/                    # Pricing
│   │   ├── Report/                   # Report, ReportInaccuracy, SampleVin
│   │   └── SocialReviews/            # Google / Trustpilot
│   ├── payments/             # Payment gateway handlers (PayPal, Yuno)
│   └── sql/                  # MySQL connection pool and SQL request loader
├── src/main/resources/
│   └── sqlEpicvin/           # SQL queries (loadSqlQuery)
├── src/test/java/
│   ├── base/BaseTest.java    # Selenide config + Allure listener
│   └── epicvin/              # Test suites grouped by site section
└── src/test/resources/
    ├── allure.properties
    └── junit-platform.properties
```

## Local run

### 1. Requirements
- Java **21**
- Maven **3.8+**
- Chrome (the driver is downloaded automatically by WebDriverManager)
- MySQL (only for SQL tests)

### 2. Configuration
Copy the template and fill in the values for your test environment:
```bash
cp .env.example .env
```

`.env.example` is grouped into sections: Base URLs, internal cleanup endpoints, test VINs, test accounts (including `TEST_EMAIL_BASE` for generated emails), test cards, PayPal, database, sensitive test data (`TEST_USER_IDS`, `EXCLUDED_AUCTIONS`), and basic auth for the dev environment.

### 3. Maven commands
```bash
# All tests
mvn clean test

# Only tests with the epicvin tag
mvn test -Dgroups=epicvin

# A specific class
mvn test -Dtest=MainPageTest

# Headless mode (for CI)
mvn test -Dheadless=true
```

### 4. Local Allure report
```bash
mvn allure:serve     # generate and open in the browser
mvn allure:report    # generate into target/site/allure-maven-plugin
```

## CI/CD

### GitHub Actions
Two workflows in `.github/workflows/`:

| Workflow | Trigger | What it does |
|---|---|---|
| `ci.yml` | `push: main`, `pull_request: main`, `workflow_dispatch` | On every event — `mvn clean compile test-compile`. Tests with secrets run **only** on `push: main` and `workflow_dispatch` (i.e. not on PRs), and only when the repository variable `RUN_TESTS=true` is set |
| `allure-report.yml` | after a successful `CI` / manual | Runs the tests, builds the Allure report, pushes it to the `gh-pages` branch |

To actually run the tests in CI:
1. **Settings → Secrets and variables → Actions → Variables** — add `RUN_TESTS=true`.
2. **Settings → Secrets and variables → Actions → Secrets** — add the secrets listed in `.env.example` (`BASE_URL_EPICVIN`, `VALID_EMAIL`, `VALID_PASSWORD`, `DATABASE_URL_EPICVIN`, `DATABASE_USER`, `DATABASE_PASSWORD`, etc.).

> Tests with secrets do not run on pull requests from any repository — only compilation runs. This protects secrets from being leaked through modified PR code.

### Jenkins
`.jenkins/Jenkinsfile` contains a declarative pipeline with parameters:
- `RUN_TESTS` — whether to actually run the tests
- `HEADLESS` — browser mode
- `TEST_GROUP` — JUnit 5 tag

It expects a Jenkins credential `qa-java-selenide-test-env` (file type) that contains the contents of `.env`.

## Allure report

A public Allure URL is intentionally **not published** — the tests run against production, and screenshots could expose test data and internal endpoints.

How to get the report:
- **Locally**: `mvn allure:serve` after `mvn test`.
- **From a CI run**: the `allure-results` artifact is attached to every `ci.yml` run (when `RUN_TESTS=true`). Download it from the GitHub Actions UI → Run → Artifacts (visible to authorized users only).
- **For a live demo**: generated locally and shared via screen sharing.

For security reasons `savePageSource` is disabled in `BaseTest.java` — Allure stores screenshots only, no full HTML page dumps.

## What is inside

### Tests in `src/test/java/epicvin/`

12 test files / 51 methods / 65 actual JUnit runs (counting parameterization).

| Section | Class | Coverage |
|---|---|---|
| Smoke / Home | `Main/MainPageTest` | Header, footer, search, banners, languages |
| Authentication | `Authentication/LoginPageTest` | Login, validations, errors |
| Authentication | `Authentication/RegistrationPageTest` | Sign up + 15 invalid email cases (parameterized) |
| Authentication | `Authentication/ResetPasswordPageTest` | Password recovery |
| Decoder | `Decoder/VinDecoder/VinDecoderPageTest` | VIN decoder |
| Precheck | `Precheck/PrecheckPageTest` | Pre-payment checkout |
| Report | `Report/ReportPageTest` | VIN report view |
| Report | `Report/SampleVinPageTest` | Sample report |
| Account | `Account/DashboardPageTest` | User dashboard |
| Account | `Account/MyReportsPageTest` | Reports list |
| Account | `Account/SubscriptionsPageTest` | Subscriptions |
| SQL | `Sql/SqlEpicvinTest` | Direct DB checks (`getEndAt`, `getNextPaymentDate*`, `getDoubleSub`, `getDoubleTrials`, report limits, etc.) |

### What the code shows
- **PageObject** — isolated page classes with private locators and a public API.
- **Custom annotations** — `@EpicvinTest`, `@EpicvinPaymentTest`, `@EpicvinSqlTest`, `@EpicvinRegistrationTest` combine `@Test`/`@ParameterizedTest`, `@Tag`, `@ExtendWith`, `@Execution`.
- **Parameterized tests** — `@EpicvinRegistrationTest` runs a single method with 15 invalid email variants (`@ValueSource`).
- **JUnit 5 Extensions** — `LogsExtension` intercepts `System.out`/`err` and adds a `[testName] [project]` prefix (needed for parallel runs).
- **Selenide listener** — Allure attaches screenshots on failures (no HTML page source — disabled for security).
- **Parallel execution** — `junit-platform.properties` (fixed, 4 threads); some tests are marked `@Execution(SAME_THREAD)` (payment ones).
- **SQL layer** — `ThreadLocal<Map<String, Connection>>` connection pool, SQL queries live in `.sql` resources and are loaded through `loadSqlQueryEpicvin(...)`.
- **Sensitive data substitution in SQL** — SQL files use placeholders (`${TEST_USER_IDS}`, `${EXCLUDED_AUCTIONS}`); the loader replaces them with values from `.env`. Lists of test users and excluded auctions never reach the public repo.
- **Payment gateways** — separate handler classes for PayPal and Yuno (encapsulating iframe and 3DS flows).

---

> Real test data and endpoints belong to a private environment. After cloning, the project will compile, but the tests will not run without your own `.env` — see `.env.example`. The public Allure URL is intentionally not published.
