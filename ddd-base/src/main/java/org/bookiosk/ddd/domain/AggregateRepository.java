package org.bookiosk.ddd.domain;

import java.io.Serializable;

/**
 * Base repository interface for aggregate persistence.
 * Defined in Domain layer, implemented in Infrastructure layer.
 *
 * @param <T> the aggregate type (must extend BaseAggregate)
 * @param <ID> the aggregate identity type
 */
public interface AggregateRepository<T extends BaseAggregate<ID>, ID extends Serializable> {
}
