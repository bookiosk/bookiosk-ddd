package org.bookiosk.ddd.domain;

import java.io.Serializable;

/**
 * Base class for all entities.
 * Entities have identity but exist within an aggregate boundary.
 * All entity properties MUST use Field, FieldSet, or FieldList wrappers.
 *
 * @param <ID> the entity identity type
 */
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private ID id;

    public ID getId() { return id; }
    public void setId(ID id) { this.id = id; }
}
