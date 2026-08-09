package org.bookiosk.ddd.domain;

import java.io.Serializable;

/**
 * Base class for all aggregate roots.
 * Aggregate roots maintain consistency boundaries for entities and value objects.
 * External access to internal objects MUST go through aggregate root methods only.
 *
 * <p>Per the framework contract the aggregate is a pure marker: it declares the
 * ID type used by repositories (see
 * {@link org.bookiosk.ddd.repository.AggregateRepository}) but does NOT hold the
 * identity itself. A business aggregate derives its identity from its business
 * data — e.g. an order in a sales system may pull two DB records (order +
 * line items) and is identified by the order business key.
 *
 * @param <ID> the business identity type (String orderNo, Long userId, value object, etc.)
 */
public abstract class BaseAggregate<ID extends Serializable> implements Aggregate, Serializable {

    private static final long serialVersionUID = 1L;
}
