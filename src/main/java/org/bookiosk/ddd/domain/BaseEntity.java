package org.bookiosk.ddd.domain;

import java.io.Serializable;

/**
 * Base class for all entities.
 * Entities have identity but exist within an aggregate boundary.
 * All entity properties MUST use Field wrapper.
 *
 * <p>{@link #setId} is public ONLY for Infrastructure-layer hydration (Repository/Converter
 * on load, or ID backfill after insert). External business code must not reassign identity.
 *
 * @param <ID> the entity identity type
 */
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private ID id;

    public ID getId() { return id; }

    /** Infrastructure hydration only — Repository/Converter set the ID on load or backfill. */
    public void setId(ID id) { this.id = id; }
}
