---
name: bookiosk-ddd-patterns
description: Coding patterns and conventions extracted from bookiosk-ddd — DDD framework with COLA-inspired architecture.
version: 1.0.0
source: local-git-analysis
analyzed_commits: 9
repository: bookiosk-ddd
language: Java 8
---

# bookiosk-ddd Patterns

## Commit Conventions

This project uses **conventional commits**. 7 of 9 commits follow the format:

```
feat:     New features (3 commits)
fix:      Bug fixes (1 commit)
chore:    Build/config maintenance (3 commits)
```

Allowed types: `feat`, `fix`, `refactor`, `docs`, `test`, `chore`, `perf`, `ci`.

Every commit includes: `Co-Authored-By: Claude Opus 4.7 <noreply@anthropic.com>`.

## Code Architecture

### Maven Multi-Module Layout

```
bookiosk-ddd/                    # Parent POM (io.github.bookiosk:bookiosk-ddd)
├── ddd-base/                    # Base classes JAR (io.github.bookiosk:ddd-base)
│   └── src/main/java/org/bookiosk/ddd/
│       ├── common/              # Shared types — ResultDO, Field wrappers, Assert, ErrorCodeI
│       ├── domain/              # Domain layer — aggregates, entities, value objects, repo, events
│       ├── application/         # Application layer — Command, Query, Executor, AppService
│       ├── client/              # Client layer — BaseDTO
│       └── infrastructure/      # Infrastructure layer — LevelLock
├── ddd-skill/                   # Claude Code / Cursor DDD compliance checking rules
├── docs/bilingual/              # CN/EN bilingual quick-reference docs
├── pom.xml                      # Parent POM with central-publishing-maven-plugin 0.9.0
├── README.md
└── LICENSE                      # Apache 2.0
```

### Package Dependency Rules

```
common ← domain ← application ← client
                ↖ infrastructure
```

- `common` depends on nothing (pure Java 8)
- `domain` depends only on `common`
- `application` depends on `common` and `domain`
- `client` depends on nothing internal (DTOs only)
- `infrastructure` depends on `domain`, `common`

### File Naming

- Java files: `PascalCase.java` matching class name
- Doc files: `kebab-case.md` — no number prefixes (AI-friendly lookup)
- Bilingual docs: `*-bilingual.md` suffix

### Class Naming Conventions

| Suffix | Meaning | Example |
|--------|---------|---------|
| `I` | Interface (marker or functional) | `GatewayI`, `ExtensionPointI`, `ErrorCodeI`, `AppServiceExecutorI` |
| `DO` | Data Object | `ResultDO<T>` |
| `DTO` | Data Transfer Object (upper-case) | `BaseDTO` |
| `Cmd` | Command (write request) | `CreateOrderCmd` |
| `Qry` | Query (read request) | `OrderGetQry` |
| `Exe` | Executor (use case handler) | `OrderCreateCmdExe` |
| `ExtPt` | Extension Point | `PaymentValidatorExtPt` |
| No suffix | Aggregate, Entity, Value Object | `Order`, `OrderItem`, `Address` |

## Zero External Dependencies

This project has **zero external dependencies** — pure Java 8 standard library only.

### Banned Libraries in Domain Layer

- No Spring
- No Lombok
- No Hibernate/JPA
- No Jackson
- No Guava

### Allowed in Infrastructure Layer

Persistence frameworks, HTTP clients, message queues — anything that implements domain interfaces.

## Code Conventions

### Immutability (CRITICAL)

```java
// ALWAYS: final fields, no setters, factory methods only
public final class BizScenario {
    private final String bizId;
    private BizScenario(String bizId) { this.bizId = bizId; }
    public static BizScenario of(String bizId) { return new BizScenario(bizId); }
}
```

### Identity Protection

```java
// Aggregate/Entity setId must be protected — only the aggregate itself assigns identity
public abstract class BaseAggregate<ID> {
    private ID id;
    public ID getId() { return id; }
    protected void setId(ID id) { this.id = id; }  // NOT public
}
```

### Null Safety

```java
// Factory methods convert null to empty, never store null
public static <T> Field<T> of(T value) {
    return value == null ? empty() : new Field<>(value);
}
```

### Exception Hierarchy

```
RuntimeException
├── BizException (common)       — Business rule violations, B_ prefix codes
└── AggregateException (domain) — Aggregate internal validation failures
```

### Error Codes

- `B_` prefix: Business errors (user-correctable)
- `S_` prefix: System errors (infrastructure, not user-correctable)
- Use `ErrorCodeI` interface, implement as enum
- `Assert.notNull()`, `Assert.isTrue()` replace manual if-throw

### Response Pattern

```java
// Never return null or throw across layer boundaries
Response.buildSuccess();
SingleResponse.of(data);
MultiResponse.of(list);
PageResponse.of(data, totalCount, pageSize, pageIndex);

// Failure with typed response
SingleResponse.fail("ERR_CODE", "message");
```

## Workflows

### Adding a New Aggregate

1. Create aggregate in `domain/` extending `BaseAggregate<ID>`
2. Define repository interface in `domain/` extending `AggregateRepository<T, ID, Q>`
3. Create `GatewayI` interfaces for external dependencies
4. Implement repository in infrastructure
5. Create `Command` + `AppServiceExecutorI` in application
6. Add `BaseDTO` subclasses in client for external API

### Adding a State Machine

1. Define state enum (`OrderStatus`) and event enum (`OrderEvent`)
2. Build machine via `StateMachineBuilder.create().externalTransition().from(...).to(...).on(...).build(machineId)`
3. Register: `StateMachineFactory.register(machine)`
4. Fire: `machine.fireEvent(currentState, event, context)`

### Publishing to Maven Central

```bash
mvn versions:set -DnewVersion=X.Y.Z     # Remove -SNAPSHOT
mvn clean deploy                          # Upload to Central Portal
# Visit https://central.sonatype.com/publishing/deployments → Publish
mvn versions:set -DnewVersion=X.Y.Z-SNAPSHOT  # Revert
```

Requires: GPG key, Central Portal token in `~/.m2/settings.xml` with `<id>central</id>`.

## Testing Patterns

- Target: 80%+ coverage per module
- Framework: JUnit 5 + Mockito (no dependencies in ddd-base itself)
- Test location: `src/test/java/` mirroring main source structure
- Unit tests for: aggregate invariants, state machine transitions, extension fallback, event dispatch
- Integration tests for: repository implementations, event handlers
