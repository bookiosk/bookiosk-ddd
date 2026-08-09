package org.bookiosk.ddd.domain;

import org.bookiosk.ddd.model.Field;

import java.io.Serializable;

/**
 * Base class for all entities.
 * Entities have identity and a soft-delete flag ({@link #getDeleted()}) but
 * live within an aggregate boundary.
 * All entity properties MUST use the {@link Field} wrapper so that changes can
 * be tracked for incremental persistence.
 *
 * <p>{@link #isAppend()} derives newness from identity: a freshly constructed
 * entity has no id yet, so it is treated as an insert; once the id is assigned
 * (on load or backfill) it becomes an update.
 *
 * <p>{@link #setId} is public ONLY for Infrastructure-layer hydration (Repository/Converter
 * on load, or ID backfill after insert). External business code must not reassign identity.
 *
 * <p>Per the framework contract, after {@code save()} the instance is stale —
 * re-query via the Repository to continue working, so change flags always start
 * fresh.
 *
 * @param <ID> the entity identity type
 */
public abstract class BaseEntity<ID extends Serializable> implements Entity<ID>, Changeable, Serializable {

    private static final long serialVersionUID = 1L;

    private ID id;

    private boolean deleted;

    @Override
    public ID getId() { return id; }

    /** Infrastructure usage only — Repository/Converter set the ID on load or backfill. */
    @Override
    public void setId(ID id) { this.id = id; }

    @Override
    public boolean getDeleted() { return deleted; }

    @Override
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    /** Whether this entity is not yet persisted (identity not assigned). */
    public boolean isAppend() { return id == null; }

    @Override
    public boolean isChanged() { return ChangeableUtil.isChanged(this); }

    protected final <T> T get(Field<T> field) { return field.get(); }

    protected final <T> void set(Field<T> field, T value) { field.set(value); }
}
