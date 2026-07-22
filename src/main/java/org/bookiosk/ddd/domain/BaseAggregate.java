package org.bookiosk.ddd.domain;

import java.io.Serializable;

/**
 * Base class for all aggregate roots.
 * Aggregate roots maintain consistency boundaries for entities and value objects.
 * External access to internal objects MUST go through aggregate root methods only.
 *
 * The {@code ID} is the business unique identifier (natural key), NOT the DB auto-increment key.
 * DB surrogate keys belong in the Infrastructure layer (PO) and must not leak into the domain.
 * For sharding scenarios, the aggregate ID doubles as the shard key.
 *
 * @param <ID> the business identity type (String orderNo, Long userId, value object, etc.)
 */
public abstract class BaseAggregate<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private ID id;

    public ID getId() { return id; }
    protected void setId(ID id) { this.id = id; }
}
