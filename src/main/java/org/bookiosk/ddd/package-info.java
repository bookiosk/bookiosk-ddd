/**
 * Production-ready DDD (Domain-Driven Design) framework based on Hexagonal Architecture.
 *
 * <h2>Package Layout</h2>
 * <pre>
 * org.bookiosk.ddd
 * ├── domain/             Domain layer — BaseAggregate, BaseEntity, BaseValue, BaseParam, BaseResult
 * ├── model/              Shared — ResultDO, Field
 * ├── repository/         Repository interfaces — Repository, AggregateRepository
 * ├── service/            DomainService marker interface
 * ├── application/        Application layer — ApplicationCmdService, ApplicationQueryService
 * ├── client/             Client layer — BaseDTO
 * ├── enums/              Enum contract — IEnum, IEnumConverter
 * ├── exception/          Exception hierarchy — BizException, AggregateException, RepositoryException
 * └── infrastructure/     Infrastructure abstractions — LevelLock, BaseConverter
 * </pre>
 *
 * <h2>Key Design Principles</h2>
 * <ul>
 *   <li>No custom annotations — uses class inheritance and marker interfaces for DDD role identification</li>
 *   <li>CQRS enforced via separate marker interfaces (ApplicationCmdService vs ApplicationQueryService)</li>
 *   <li>Immutable entity properties via Field wrapper</li>
 *   <li>Two-mode failure handling — blocking (throw, caught at AppService choke point) vs
 *       branch (return ResultDO with data)</li>
 *   <li>Repository as Port — defined in domain, implemented in infrastructure</li>
 *   <li>Zero external dependencies — pure Java 8+ standard library</li>
 * </ul>
 *
 * @see <a href="https://github.com/bookiosk/bookiosk-ddd">GitHub Repository</a>
 */
package org.bookiosk.ddd;
