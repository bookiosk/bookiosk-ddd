package org.bookiosk.ddd.domain;

/**
 * Marks a domain object whose changes can be detected.
 *
 * <p>Implemented by aggregating the {@link org.bookiosk.ddd.model.Field}
 * wrappers declared on the object's properties — a {@link BaseEntity} is
 * changed when any reachable {@code Field} property is changed.
 */
public interface Changeable {

    /** Whether any property of this object (and its nested objects) has changed. */
    boolean isChanged();
}
