package org.bookiosk.ddd.domain;

import java.io.Serializable;

/**
 * Contract for all entities.
 *
 * <p>An entity has an identity ({@link #getId()}) and lives inside an aggregate
 * boundary. The interface exposes only identity (id) get/set and the soft-delete
 * flag (deleted) get/set; all other behavior belongs to the concrete class.
 */
public interface Entity<ID extends Serializable> {

    ID getId();

    void setId(ID id);

    boolean getDeleted();

    void setDeleted(boolean deleted);
}