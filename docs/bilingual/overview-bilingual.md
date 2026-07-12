# Easy-DDD 框架总览 / Easy-DDD Framework Overview

> 中文 / English bilingual reference for developers

---

## 什么是 Easy-DDD？ / What is Easy-DDD?

一套**开箱即用的 DDD（领域驱动设计）落地规范**，基于六边形架构，覆盖从架构设计到各层编码的完整指南。

A **production-ready DDD (Domain-Driven Design) implementation standard** based on Hexagonal Architecture, covering complete guidance from architecture design to per-layer coding.

帮你解决 DDD 落地时最常见的问题：**知道理论，但不知道代码该怎么写、放在哪里。**

Solves the most common DDD adoption problem: **you know the theory, but don't know how to write the code or where to put it.**

---

## 架构一览 / Architecture at a Glance

```
调用顺序 / Call Order (outside → inside):

inAdaptor → Application → Domain → Repository (DB/Cache/Message)
                                 → outAdaptor (3rd-party services)
```

### 六层架构 / Six Layers

| 层 / Layer | 中文职责 | English Responsibility |
|-----------|---------|----------------------|
| **Client** | 对外 RPC 接口定义，DTO 自包含 | External API definitions, DTOs must be self-contained |
| **Adaptor** | 防腐层，隔离外部技术细节 | Anti-corruption layer, isolate external technical details |
| **Application** | 场景编排，不稳定的业务场景 | Scene orchestration, unstable business scenarios |
| **Domain** | 核心业务逻辑，稳定不变 | Core business logic, stable and invariant |
| **Infrastructure** | 技术实现，数据库/缓存/消息 | Technical implementation, DB/Cache/Messaging |
| **Model** | 内部共享模型、枚举 | Internal shared models and enums |

### 依赖规则 / Dependency Rules

```
client      →  禁止依赖任何内部模块 / MUST NOT depend on internal modules
adaptor     →  实现 application 定义的接口 / implements application-defined interfaces
application →  依赖 domain, client, model / depends on domain, client, model
domain      →  仅依赖 model / only depends on model
infrastructure → 实现 domain 定义的接口, 依赖 domain, model / implements domain interfaces, depends on domain, model
model       →  无依赖 / no dependencies
```

---

## 四种开发模式 / Four Development Modes

| 模式 / Mode | 聚合根 / Aggregate | 业务逻辑在哪 / Logic Location | 改状态 / Mutates | 典型场景 / Typical Use |
|------------|-------------------|------------------------|-----------------|----------------------|
| **写模式** / Write | 有 Yes | 聚合根方法 / Aggregate methods | 是 Yes | 订单创建、状态变更 / Order creation, status change |
| **读模式** / Read | 有(数据载体) / Yes(carrier) | 无 / None | 否 No | 订单查询 / Order query |
| **规则+计算** / Rule+Calc | 有 Yes | 聚合根方法 / Aggregate methods | 否 No | 补贴规则匹配 / Subsidy rule matching |
| **纯计算** / Pure Calc | 无 No | DomainService | 否 No | 费用计算、搜索控制 / Fee calc, search control |

### 模式选择 / Mode Selection

```
需要修改数据状态？/ Modifies data state?
  ├── 是/Yes → 写模式 / Write Mode
  └── 否/No → 有业务逻辑？/ Has business logic?
       ├── 否/No → 读模式 / Read Mode
       └── 是/Yes → 基于规则？/ Rule-based?
            ├── 是/Yes → 规则+计算 / Rule+Calculate
            └── 否/No → 纯计算 / Pure Calculate
```

---

## 调用链速查 / Call Chain Quick Reference

| 模式 / Mode | 调用链 / Call Chain |
|------------|-------------------|
| **写 / Write** | `Input Adaptor → AppService → DomainService → Aggregate.method() → Repository.save()` |
| **读(领域内) / Read(intra)** | `Input Adaptor → QueryAppService → Repository.query() → DTO` |
| **读(跨领域) / Read(cross)** | `Input Adaptor → QueryAppService → Adaptor → DTO` |
| **纯计算 / Pure Calc** | `Input Adaptor → QueryAppService → DomainService → Result` |
| **规则+计算 / Rule+Calc** | `Input Adaptor → QueryAppService → DomainService → Repository → Aggregate → Result` |

---

## 命名规范速查 / Naming Convention Quick Reference

### Domain 层 / Domain Layer

| 类型 / Type | 命名 / Naming | 继承 / Extends | 示例 / Example |
|------------|--------------|---------------|--------|
| 聚合根 / Aggregate | `{Noun}Aggregate` | `BaseAggregate` | `OrderAggregate` |
| 实体 / Entity | `{Noun}Entity` | `BaseEntity` | `OrderItemEntity` |
| 值对象 / Value Object | `{Noun}Value` | `BaseValue` | `PassengerValue` |
| 领域服务(写) / DomainService(Write) | `{Aggregate}DomainService` | — | `OrderDomainService` |
| 领域服务(计算) / DomainService(Calc) | `{Verb}DomainService` | — | `SearchControlDomainService` |
| 参数对象 / Param | `{Method}Param` | `BaseParam` | `ConfirmPaymentParam` |
| 结果对象 / Result | `{Method}Result` | `BaseResult` | `FeeCalculateResult` |
| 仓储接口 / Repository | `{Business}Repository` | `AggregateRepository` | `OrderRepository` |

### Application 层 / Application Layer

| 类型 / Type | 命名 / Naming | 继承 / Extends | 示例 / Example |
|------------|--------------|---------------|--------|
| 应用服务(写) / AppService(Write) | `{Aggregate}AppService` | `ApplicationCmdService` | `OrderAppService` |
| 应用服务(读) / AppService(Read) | `{Aggregate}QueryAppService` | `ApplicationQueryService` | `OrderQueryAppService` |
| 应用服务(计算) / AppService(Calc) | `{Verb}QueryAppService` | `ApplicationQueryService` | `FeePreCalculateQueryAppService` |
| 转换器 / Assembler | `{Aggregate}Assembler` | — | `OrderAssembler` |

### 其他层 / Other Layers

| 层 / Layer | 类型 / Type | 命名 / Naming | 示例 / Example |
|-----------|------------|--------------|--------|
| Adaptor | Input | `{Business}Controller` / `HsfServiceImpl` / `MessageConsumer` | `OrderController` |
| Adaptor | Output | `{Business}Adaptor` | `LogisticsAdaptor` |
| Infrastructure | 仓储实现 / Repo Impl | `{Business}RepositoryImpl` | `OrderRepositoryImpl` |
| Infrastructure | 持久化对象 / PO | `{Table}PO` | `OrderPO` |
| Infrastructure | Mapper | `{Table}Mapper` | `OrderMapper` |
| Infrastructure | 转换器 / Converter | `{Business}Converter` | `OrderConverter` |
| Client | 请求 / Request | `{Method}RequestDTO` | `CreateOrderRequestDTO` |
| Client | 响应 / Response | `{Method}ResponseDTO` | `CreateOrderResponseDTO` |
| Client | 共享对象 / Shared DTO | `{Concept}DTO` | `PassengerDTO` |

---

## 核心基类速查 / Core Base Classes Quick Reference

| 基类 / Base Class | 所在层 / Layer | 用途 / Purpose |
|------------------|---------------|--------|
| `ResultDO<T>` | model (共享/shared) | 统一操作结果包装器 / Universal result wrapper |
| `BaseAggregate<ID>` | domain | 聚合根基类 / Aggregate root base |
| `BaseEntity<ID>` | domain | 实体基类，属性必须用 Field<T> / Entity base, properties must use Field<T> |
| `BaseValue` | domain | 值对象基类，不可变，无ID / Value object base, immutable, no ID |
| `BaseParam` | domain | 领域方法参数基类 / Domain method parameter base |
| `BaseResult` | domain | 领域方法结果基类，可包含充血方法 / Domain result base, supports rich methods |
| `BaseDTO` | client | DTO 基类，实现 Serializable / DTO base, implements Serializable |
| `Field<T>` | model (共享/shared) | 不可变单值包装器 / Immutable single-value wrapper |
| `FieldSet<T>` | model (共享/shared) | 不可变集合包装器 / Immutable set wrapper |
| `FieldList<T>` | model (共享/shared) | 不可变列表包装器 / Immutable list wrapper |
| `AggregateRepository<T,ID>` | domain | 仓储基类接口 / Repository base interface |
| `ApplicationCmdService` | application | 命令服务标记接口 / Command service marker |
| `ApplicationQueryService` | application | 查询服务标记接口 / Query service marker |
| `AggregateException` | domain | 聚合根校验异常 / Aggregate validation exception |
| `BizException` | domain | 业务逻辑异常 / Business logic exception |
| `LevelLock` | infrastructure | 分布式锁抽象 / Distributed lock abstraction |

---

## 关键规则速记 / Key Rules at a Glance

### 领域层铁律 / Domain Layer Iron Rules

1. ❌ **禁止设计模式** / Design patterns FORBIDDEN — 业务分支用 `if/else` 直接处理 / use `if/else` for business branches
2. ❌ **禁止依赖框架** / Framework dependencies FORBIDDEN — Spring 和静态工具类例外 / Spring DI and static utils excepted
3. ✅ **异常不跨层** / Exceptions don't cross layers — 统一通过 `ResultDO` 返回 / always return via `ResultDO`
4. ✅ **实体属性用 Field<T>** / Entity properties use `Field<T>` — 强制不可变 / enforces immutability

### 应用层铁律 / Application Layer Iron Rules

1. ❌ **禁止包含核心业务逻辑** / Core business logic FORBIDDEN — 只做编排 / orchestration only
2. ✅ **CQRS 读写分离** / CQRS required — Command 禁止调 Query / Command must not call Query
3. ✅ **参数自校验** / Self-validating params — `requestDTO.check()` 返回 `ResultDO`
4. ✅ **不抛异常** / Don't throw exceptions — 统一返回 `ResultDO` / always return `ResultDO`

### 适配器层铁律 / Adaptor Layer Iron Rules

1. ✅ **接口按 Application 需要定义** / Interfaces defined by Application needs — 不按第三方接口定义 / not by 3rd-party API shape
2. ❌ **禁止业务逻辑** / Business logic FORBIDDEN — 只做协议转换 / protocol conversion only
3. ✅ **允许设计模式** / Design patterns ALLOWED — 技术路由场景 / for technical routing

### 基础设施层铁律 / Infrastructure Layer Iron Rules

1. ❌ **禁止业务逻辑** / Business logic FORBIDDEN — 只管"存"和"取" / only "store" and "retrieve"
2. ❌ **禁止访问第三方外部服务** / External 3rd-party service calls FORBIDDEN — 那是 Adaptor 的职责 / that's Adaptor's job
3. ✅ **异常转 ResultDO** / Exceptions → ResultDO — 禁止向上抛技术异常 / don't propagate technical exceptions

---

## 文档索引 / Document Index

| 文档 / Document | 内容 / Content |
|---------------|--------|
| [overview.md](../ddd-en/overview.md) | 架构总览 / Architecture Overview |
| [domain-layer.md](../ddd-en/domain-layer.md) | 领域层规范 / Domain Layer Spec |
| [application-layer.md](../ddd-en/application-layer.md) | 应用层规范 / Application Layer Spec |
| [adaptor-layer.md](../ddd-en/adaptor-layer.md) | 适配器层规范 / Adaptor Layer Spec |
| [infrastructure-layer.md](../ddd-en/infrastructure-layer.md) | 基础设施层规范 / Infrastructure Layer Spec |
| [client-layer.md](../ddd-en/client-layer.md) | Client 层规范 / Client Layer Spec |
| [model-layer.md](../ddd-en/model-layer.md) | Model 层规范 / Model Layer Spec |
| [base-classes-reference.md](../ddd-en/base-classes-reference.md) | 基类和核心类型参考 / Base Classes & Core Types Reference |
