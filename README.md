# bookiosk-ddd

Production-ready DDD (Domain-Driven Design) framework based on Hexagonal Architecture.

## Quick Start

### Maven

```xml
<dependency>
    <groupId>io.github.bookiosk</groupId>
    <artifactId>ddd-base</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.bookiosk:ddd-base:1.0.0'
```

Zero external dependencies — pure Java 8+.

## Modules

| Module | Purpose |
|--------|---------|
| [ddd-base/](ddd-base/) | Base classes JAR — publishable to Maven Central |
| [ddd-skill/](ddd-skill/) | Claude Code / Cursor skill — DDD compliance checking rules |
| [docs/](docs/) | Developer documentation (bilingual CN/EN) |

## Package Layout

```
org.bookiosk.ddd
├── common/              # Shared — ResultDO, Field, FieldSet, FieldList
├── domain/              # Domain layer — BaseAggregate, BaseEntity, BaseValue, BaseParam, BaseResult,
│                        #   AggregateRepository, AggregateException, BizException
├── application/         # Application layer — ApplicationCmdService, ApplicationQueryService
├── client/              # Client layer — BaseDTO
└── infrastructure/      # Infrastructure layer — LevelLock
```

## Documentation

- [Architecture Overview](ddd-skill/references/overview.md)
- [Base Classes Reference](ddd-skill/references/base-classes-reference.md)
- [Quick-Start Tutorial](ddd-skill/references/quick-start-tutorial.md)
- [Anti-Patterns](ddd-skill/rules/anti-patterns.md)
- [Bilingual Quick Reference](docs/bilingual/overview-bilingual.md)

## License

[Apache License, Version 2.0](LICENSE)
