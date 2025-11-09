## Quick orientation for AI coding agents

This repo is a Spring Boot (Java 21) web application using Spring Data JPA, Spring Security, Thymeleaf and Flyway migrations. Below are concise, actionable notes an AI agent should follow to be immediately productive in this codebase.

### Contract (inputs/outputs)
- Input: edits to Java, SQL (Flyway), Thymeleaf templates or configuration in `src/main/resources`.
- Output: buildable Spring Boot app (use `./mvnw.cmd spring-boot:run` on Windows) and non-breaking changes to APIs and templates.

### How to run & reproduce locally (Windows)
- Ensure Java 21 is installed.
- Provide DB credentials with a `.env` file in repo root (see `README.md` and `src/main/resources/application.properties`). Required env vars: `ORACLEHOST`, `ORACLEPORT`, `ORACLEDATABASE`, `ORACLEUSER`, `ORACLEPASSWORD`.
- Run with the Maven wrapper on Windows PowerShell:
```powershell
./mvnw.cmd spring-boot:run
```
- Or build & run the jar:
```powershell
./mvnw.cmd clean package; java -jar target/Mottracker-0.0.1-SNAPSHOT.jar
```

### Key files and patterns (read these first)
- `pom.xml` — dependencies (Spring Boot, Flyway, Oracle JDBC) and `flyway-maven-plugin`.
- `src/main/resources/application.properties` — DB, Flyway and logging settings. Important: `spring.config.import=optional:file:.env[.properties]`.
- `src/main/resources/db/migration/` — Flyway migration scripts. Flyway is the canonical source of DB shape and initial data.
- `src/main/java/br/com/fiap/Mottracker/` — main packages: `configuration`, `controller`, `service`, `model`, `repository`, `dto`, `enums`, `templates`.
- `MottrackerApplication.java` — main class (`@EnableCaching` present).

### Project-specific conventions & gotchas
- Flyway is enabled by default. If migrations must be re-run the repo includes a comment advising to `DELETE FROM flyway_schema_history; COMMIT;` — this is destructive and should be used only when you understand the consequences.
- Environment variables are loaded via an optional `.env` file. Many developers create `.env` in repo root with the values described in `README.md`.
- Enum handling: entities mix `EnumType.STRING` and `EnumType.ORDINAL`.
  - `Moto` uses `@Enumerated(EnumType.STRING)` — see `src/main/java/br/com/fiap/Mottracker/model/Moto.java`.
  - `Camera` uses `@Enumerated(EnumType.ORDINAL)` — see `src/main/java/br/com/fiap/Mottracker/model/Camera.java`.
  - Symptom to watch for: errors like `No enum constant br.com.fiap.Mottracker.enums.Estados.1` indicate a mapping mismatch (code expecting enum name but DB contains numeric ordinal or different string). When you see this:
    - Inspect `src/main/java/br/com/fiap/Mottracker/enums/` for allowed names.
    - Inspect migration SQL in `db/migration` for inserted enum values.
    - Prefer `EnumType.STRING` for stable DB readability unless the schema is intentionally ordinal.

### Common developer flows & quick checks
- Initialize the app on a fresh DB: run the app, then visit `/setup/init` to create permissions and `/admin/create-admin` to create an admin user (both endpoints are intentionally public). See `SetupController` and `AdminController`.
- Use Swagger at `/swagger-ui.html` to explore API shapes and example bodies (controllers are annotated for OpenAPI).
- Pagination and filters: controllers accept Spring Data pageable parameters (`page`, `size`, `sort`). See `MotoController#getAll` and `getByEstado`.
- Caching: controllers use `@Cacheable("motos")` and `@CacheEvict` for invalidation — keep cache keys consistent when editing controllers.

### Debugging tips (fast wins)
- If Flyway migrations fail or you need a clean run in dev, confirm `spring.flyway.enabled=true` and (carefully) clear `flyway_schema_history` in the DB to force reapply — only in dev/test.
- Logs: `application.properties` sets detailed Spring Security debug logging. Use logs to trace authentication filter chains and role assignments.
- Enum errors: when you see `No enum constant ...`, check both the enum class and the DB value (migration script or existing rows). Example files:
  - `src/main/java/br/com/fiap/Mottracker/enums/Estados.java`
  - `src/main/resources/db/migration/*` (search for inserts to MT_MOTO_JAVA or MT_CAMERA_JAVA)

### Security & sensitive data notes
- `pom.xml` includes a Flyway plugin block with credentials — avoid checking secrets into commits; prefer migrating that config to CI or a secure vault and using `.env` for local dev.

### If you change DB-related code
- Update matching Flyway migrations or add a new migration. Do not edit old migration files that have already been applied in production-like environments.

If anything here is unclear or you'd like me to expand a section (example: list of controllers, typical request/response shapes, or a short troubleshooting checklist for enum/ Flyway failures), tell me which parts to expand and I'll update this file.
