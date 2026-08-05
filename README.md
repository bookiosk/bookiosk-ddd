# bookiosk-ddd

Production-ready DDD (Domain-Driven Design) framework based on Hexagonal Architecture.

## Quick Start

### Maven

```xml
<dependency>
    <groupId>io.github.bookiosk</groupId>
    <artifactId>ddd-framework</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.bookiosk:ddd-framework:1.0.2'
```

Zero external dependencies — pure Java 8+.

## Package Layout

```
org.bookiosk.ddd
├── domain/              # Domain layer — BaseAggregate, BaseEntity, BaseValue, BaseParam, BaseResult
├── model/               # Shared — ResultDO, Field
├── repository/          # Repository interfaces — Repository, AggregateRepository
├── service/             # DomainService marker interface
├── application/         # Application layer — ApplicationCmdService, ApplicationQueryService
├── client/              # Client layer — BaseDTO
├── enums/               # Enum contract — IEnum, IEnumConverter
├── exception/           # Exception hierarchy — BizException, AggregateException, RepositoryException
└── infrastructure/      # Infrastructure abstractions — LevelLock
```

## Related Projects

| Project | Purpose |
|---------|---------|
| [ddd-check](https://github.com/bookiosk/ddd-check) | Claude Code / Cursor skill — DDD compliance checking rules |
| [easy-ddd](https://github.com/bookiosk/easy-ddd) | Developer documentation (bilingual CN/EN) |

## License

[Apache License, Version 2.0](LICENSE)
