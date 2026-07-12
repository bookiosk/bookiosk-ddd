package org.bookiosk.ddd.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Base repository interface for aggregate persistence.
 * Defined in Domain layer, implemented in Infrastructure layer.
 *
 * @param <T> aggregate type (must extend BaseAggregate)
 * @param <ID> aggregate identity type
 * @param <Q> query condition type for conditional lookups
 */
public interface AggregateRepository<T extends BaseAggregate<ID>, ID extends Serializable, Q> {

    void save(T aggregate);

    Optional<T> findById(ID id);

    List<T> findAll(Q query);

    void remove(ID id);

    boolean existsById(ID id);
}
