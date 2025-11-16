# Repository Guidelines

## Project Structure & Module Organization
- Root uses Gradle Kotlin DSL with Kotlin 2.2 / JDK 21. Modules: `common` (shared domain/application/dataaccess), domain services (`order-service`, `payment-service`, `restaurant-service`, `customer-service`) split into `*-domain`, `*-application`, `*-dataaccess`, `*-messaging`, and `*-container` (Spring Boot entrypoints). `infrastructure` holds shared tech (Kafka, saga) and Avro model generation.
- Standard layout: `src/main/kotlin` for production code, `src/test/kotlin` for unit/integration tests, and `src/main/resources` for configs (application YAML, SQL, Avro schemas).
- Keep cross-module dependencies flowing inward (domain core free of infra concerns; application layer orchestrates; dataaccess/messaging provide adapters).

## Build, Test, and Development Commands
- `./gradlew clean build` – full build and tests for all modules.
- `./gradlew test` – run all tests; add `:module:path:test` (e.g., `:order-service:order-domain:order-application-service:test`) for targeted runs.
- `./gradlew :order-service:order-container:bootRun` (similarly `:payment-service:payment-container:bootRun`, `:restaurant-service:restaurant-container:bootRun`) – start a service locally; ensure required infra (DB/Kafka) is up.
- `./gradlew :infrastructure:kafka:kafka-model:generateAvroKotlin` – regenerate Kafka model classes from Avro schemas.

## Coding Style & Naming Conventions
- Follow Kotlin conventions (4-space indent, braces on same line, expressive `val` over `var` when possible). Package names start with `com.food.ordering.system`.
- Class/file names align with roles: `*ApplicationService`, `*DomainService`, `*Repository`, DTOs as `*Request/*Response` or command/query objects.
- Respect hexagonal boundaries: domain core is pure; keep persistence/messaging logic inside adapter modules (`*dataaccess`, `*messaging`).

## Testing Guidelines
- Tests live beside modules under `src/test/kotlin`; naming pattern `*Test`. Use JUnit 5 with Mockito and Spring Boot test support (see `OrderApplicationServiceTest` for setup/mocking style).
- Prefer fast domain tests without Spring context; reserve `@SpringBootTest` for wiring/adapter checks. Seed fixtures with clear constants and verify both happy-path and validation errors.
- Run targeted module tests before PRs; ensure new behaviors include assertions and negative cases.

## Commit & Pull Request Guidelines
- Commit messages are short summaries (e.g., “restaurant service 구현”, “saga + 기타 이슈 수정”). Keep the first line imperative/past-tense friendly and under ~72 chars; include issue IDs when relevant.
- PRs should describe intent, scope, and impact, list affected modules, and note any migrations or infra needs. Add logs/screenshots for user-visible changes and mention how to reproduce/verify (commands, sample payloads).
