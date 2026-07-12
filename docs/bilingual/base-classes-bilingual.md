# 基类与核心类型参考 / Base Classes & Core Types Reference

> 中英文对照，方便开发者快速查阅 / Bilingual quick reference for developers

---

## ResultDO\<T\> — 统一结果包装器 / Universal Result Wrapper

**所在层 / Layer:** `model` (共享 / shared)

所有层的方法统一返回 `ResultDO<T>`，异常不跨层传播。

All methods across all layers return `ResultDO<T>`. Exceptions never cross layer boundaries.

```java
// 成功返回 / Success
ResultDO.buildSuccessResult(data);
ResultDO.buildSuccessResult(null);  // void 操作 / void operations

// 失败返回 / Failure
ResultDO.buildFailResult("ERROR_CODE", "错误描述 / Error description");

// 判断成功 / Check success
if (result.isSuccess()) { ... }

// 获取数据 / Get data
T data = result.getData();

// 获取错误信息 / Get error info
String code = result.getCode();
String msg = result.getMsg();
```

---

## Field\<T\> / FieldSet\<T\> / FieldList\<T\> — 不可变属性包装器 / Immutable Property Wrappers

**所在层 / Layer:** `model` (共享 / shared)

**为什么需要？/ Why needed?** Entity 属性必须使用 Field 包装，强制不可变性——每次更新创建新实例，绝不原地修改。支持变更追踪和空安全。

Entity properties MUST use Field wrappers. Enforces immutability — every update creates a new instance, never mutates in-place. Enables change tracking and null safety.

```java
// Field<T> — 单值包装器 / Single value wrapper
Field<Long> price = Field.of(100L);
Long value = price.get();                // 100
Field<Long> newPrice = price.map(v -> v * 2);  // Field.of(200L) — 新实例 / new instance

// FieldSet<T> — 集合包装器 / Set wrapper
FieldSet<String> tags = FieldSet.of(Set.of("A", "B"));
FieldSet<String> newTags = tags.add("C");  // 新实例，原集合不变 / new instance, original unchanged

// FieldList<T> — 列表包装器 / List wrapper
FieldList<OrderItem> items = FieldList.of(itemList);
FieldList<OrderItem> newItems = items.add(newItem);  // 新实例 / new instance
```

**Entity 中的使用 / Usage in Entity:**
```java
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemEntity extends BaseEntity<Long> {
    private Field<Long> orderId;
    private Field<String> productName;
    private Field<Long> price;           // 不是 Long，是 Field<Long> / not Long, Field<Long>

    public void updatePrice(Long newPrice) {
        if (newPrice == null || newPrice <= 0) {
            throw new AggregateException("价格必须大于0 / Price must be > 0");
        }
        this.price = Field.of(newPrice);  // 创建新 Field，不修改原值 / creates new Field, not mutating
    }
}
```

---

## Domain 层基类 / Domain Layer Base Classes

### BaseAggregate\<ID\> — 聚合根基类

```java
public abstract class BaseAggregate<ID extends Serializable> implements Serializable {
    private ID id;
}
```

**命名 / Naming:** `{名词/Noun}Aggregate` 如 `OrderAggregate`
**职责 / Role:** 维护一组实体和值对象的一致性边界 / Maintains consistency boundary for entities and value objects
**规则 / Rules:**
- 只能通过聚合根方法访问内部对象 / Internal objects only accessible via aggregate methods
- 聚合之间通过 ID 引用，不用对象引用 / Aggregates reference each other by ID, not object reference
- 方法名必须是动词 / Method names must be verbs

### BaseEntity\<ID\> — 实体基类

```java
public abstract class BaseEntity<ID extends Serializable> implements Serializable {
    private ID id;
}
```

**命名 / Naming:** `{名词/Noun}Entity` 如 `OrderItemEntity`
**关键规则 / Critical Rule:** 所有属性必须使用 `Field<T>`, `FieldSet<T>`, `FieldList<T>` / All properties MUST use Field wrappers

### BaseValue — 值对象基类

```java
public abstract class BaseValue implements Serializable {
    // 无 ID / No identity
}
```

**命名 / Naming:** `{名词/Noun}Value` 如 `PassengerValue`
**特征 / Characteristics:**
- 无唯一标识 / No unique identifier
- 不可变 / Immutable
- 相等性基于属性值 / Equality based on attribute values
- 方法只能是计算类、判断类 / Methods can only be calculation or judgment

### BaseParam — 参数基类

```java
public abstract class BaseParam implements Serializable { }
```

**命名 / Naming:** `{方法名/MethodName}Param` 如 `ConfirmPaymentParam`
**用途 / Usage:** DomainService 和聚合根方法入参 / DomainService and aggregate method input

### BaseResult — 结果基类

```java
public abstract class BaseResult implements Serializable { }
```

**命名 / Naming:** `{方法名/MethodName}Result` 如 `FeeCalculateResult`
**特点 / Feature:** 可以有充血方法（行为+数据）/ May contain rich methods (behavior + data)

---

## Application 层基类 / Application Layer Base Classes

### ApplicationCmdService — 命令服务标记

```java
public interface ApplicationCmdService { }
```

写模式 AppService 继承此接口 / Write mode AppService extends this interface.

### ApplicationQueryService — 查询服务标记

```java
public interface ApplicationQueryService { }
```

读模式/计算模式 AppService 继承此接口 / Read/Calculate mode AppService extends this interface.

### CQRS 规则 / CQRS Rule

```
Command (写/Write) → ApplicationCmdService
Query  (读/Read)   → ApplicationQueryService

Command 禁止调用 Query / Command must NOT call Query
```

---

## 其他基类 / Other Base Classes

### AggregateRepository\<T, ID\> — 仓储基接口

```java
public interface AggregateRepository<T extends BaseAggregate<ID>, ID extends Serializable> { }
```

**定义在 / Defined in:** `domain` 层
**实现在 / Implemented in:** `infrastructure` 层
**命名 / Naming:** `{业务名/Business}Repository` 如 `OrderRepository`

### BaseDTO — DTO 基类

```java
public abstract class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
}
```

**所在层 / Layer:** `client`
**用途 / Usage:** 所有 RequestDTO, ResponseDTO, 共享 DTO 的基类 / Base for all DTOs

---

## 异常类 / Exception Classes

### AggregateException — 聚合根异常

```java
public class AggregateException extends RuntimeException {
    private final String code;
    private final String msg;
}
```

**抛出位置 / Thrown in:** 聚合根、实体内部校验 / Aggregate, Entity internal validation
**捕获位置 / Caught in:** DomainService → 转换为 `ResultDO` 失败 / converted to `ResultDO` failure

### BizException — 业务异常

```java
public class BizException extends RuntimeException {
    private final String code;
    private final String msg;
}
```

**抛出位置 / Thrown in:** DomainService 业务校验 / DomainService business validation
**捕获位置 / Caught in:** DomainService 自身 → 转换为 `ResultDO` 失败 / converted to `ResultDO` failure

### 异常处理模式 / Exception Handling Pattern

```java
try {
    // 业务逻辑 / Business logic
} catch (BizException e) {
    log.error("业务异常 / Business error, param: {}", param, e);
    return ResultDO.buildFailResult(e.getCode(), e.getMsg());
} catch (AggregateException e) {
    log.error("聚合根异常 / Aggregate error, param: {}", param, e);
    return ResultDO.buildFailResult(e.getCode(), e.getMsg());
} catch (Throwable e) {
    log.error("系统异常 / System error, param: {}", param, e);
    return ResultDO.buildFailResult("SYSTEM_ERROR", "系统异常 / System error");
}
```

---

## LevelLock — 分布式锁 / Distributed Lock

```java
public class LevelLock {
    public LevelLock(String lockKey) { ... }
    public boolean tryLock() { ... }    // 非阻塞 / non-blocking
    public void unlock() { ... }
}
```

**使用模式 / Usage Pattern:**
```java
LevelLock lock = orderRepository.buildLock("order:confirmPayment:" + orderId);
try {
    if (!lock.tryLock()) {
        return ResultDO.buildFailResult("LOCK_FAIL", "获取锁失败，请重试 / Lock failed, retry");
    }
    // ... 业务逻辑 / business logic ...
} finally {
    lock.unlock();
}
```

---

## 继承链总图 / Inheritance Chain Diagram

```
领域模型层级 / Domain Model Hierarchy:
  Serializable
    ├── BaseAggregate<ID>          ← 聚合根 / Aggregate roots
    ├── BaseEntity<ID>             ← 实体（属性用 Field<T>）/ Entities (properties use Field<T>)
    └── BaseValue                  ← 值对象（不可变，无ID）/ Value objects (immutable, no ID)

领域方法 I/O / Domain Method I/O:
  Serializable
    ├── BaseParam                  ← 方法入参 / Method input
    └── BaseResult                 ← 方法返回（支持充血方法）/ Method return (supports rich methods)

Client DTOs:
  Serializable
    └── BaseDTO
         ├── {Name}RequestDTO      ← API 请求参数 / API request
         ├── {Name}ResponseDTO     ← API 响应数据 / API response
         └── {Name}DTO             ← 共享嵌套对象 / Shared nested objects

共享模型 / Shared Model:
  Field<T> / FieldSet<T> / FieldList<T>   ← 不可变属性包装 / Immutable property wrappers
  ResultDO<T>                             ← 统一结果信封 / Universal result envelope

应用服务 / Application Services:
  ApplicationCmdService    ← 命令（写）标记 / Command (write) marker
  ApplicationQueryService  ← 查询（读）标记 / Query (read) marker

领域异常 / Domain Exceptions:
  RuntimeException
    ├── AggregateException  ← 聚合根/实体校验失败 / Aggregate/Entity validation failure
    └── BizException        ← 领域服务业务失败 / Domain service business failure
```
