package org.bookiosk.ddd.domain;

import java.io.Serializable;

/**
 * Base class for all value objects.
 * Value objects have no identity, are immutable, and equality is based on attribute values.
 * Methods should be calculation or judgment only — return new instances, never mutate.
 */
public abstract class BaseValue implements Serializable {

    private static final long serialVersionUID = 1L;
}
