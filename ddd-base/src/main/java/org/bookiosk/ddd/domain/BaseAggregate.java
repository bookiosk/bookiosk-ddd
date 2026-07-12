package org.bookiosk.ddd.domain;

import java.io.Serializable;

/**
 * Base class for all aggregate roots.
 * Aggregate roots maintain consistency boundaries for entities and value objects.
 * External access to internal objects MUST go through aggregate root methods only.
 *
 * @param <ID> the aggregate identity type
 */
public abstract class BaseAggregate<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private ID id;

    public ID getId() { return id; }
    public void setId(ID id) { this.id = id; }
}
