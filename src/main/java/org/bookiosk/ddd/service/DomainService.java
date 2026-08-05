package org.bookiosk.ddd.service;

/**
 * Marker interface for all domain services.
 * Signals that a class encapsulates pure domain business logic.
 *
 * <p>Naming convention:
 * <ul>
 *   <li>Write mode: {@code {Aggregate}DomainService} (e.g. {@code OrderDomainService})</li>
 *   <li>Pure Calculate / Rule+Calculate mode: {@code {Verb}DomainService} (e.g. {@code FeeCalculateDomainService})</li>
 * </ul>
 *
 * <p>Constraints:
 * <ul>
 *   <li>Must not depend on Adaptor layer</li>
 *   <li>Must not use design patterns (Strategy, Factory, etc.) for business routing</li>
 * </ul>
 *
 * <p>Return type is per-scenario, NOT locked to ResultDO:
 * <ul>
 *   <li>Blocking (阻断型): return the domain object directly and throw
 *       {@link org.bookiosk.ddd.exception.BizException}/{@link org.bookiosk.ddd.exception.AggregateException}
 *       on failure — the exception propagates to AppService which converts it to ResultDO</li>
 *   <li>Branch (分支型): return {@link org.bookiosk.ddd.model.ResultDO} carrying failure data
 *       so the caller can make a branch/fallback decision</li>
 * </ul>
 *
 * @author bookiosk
 */
public interface DomainService {
}
