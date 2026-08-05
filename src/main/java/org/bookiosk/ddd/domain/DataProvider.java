package org.bookiosk.ddd.domain;

/**
 * Functional interface for lazy-loading external data in DomainService.
 * Defined in Domain layer, implementation injected by Application layer
 * (wired to Adaptor). This keeps Domain layer pure while enabling
 * on-demand external data queries during business logic execution.
 *
 * <p>Usage pattern:
 * <pre>{@code
 * // 1. Define in domain layer (e.g., ExchangeRateProvider extends DataProvider<String, BigDecimal>)
 * // 2. Reference in Param object
 * // 3. Application layer wires Adaptor call into Param
 * // 4. DomainService calls via interface without knowing implementation
 * }</pre>
 *
 * @param <P> parameter type for the data query
 * @param <R> result type of the data query
 */
@FunctionalInterface
public interface DataProvider<P, R> {

    /**
     * Provide data for the given parameter.
     * Called by DomainService during business logic execution.
     *
     * @param param query parameter
     * @return queried data
     */
    R provide(P param);
}
