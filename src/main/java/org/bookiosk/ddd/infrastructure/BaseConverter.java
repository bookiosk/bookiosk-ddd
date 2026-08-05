package org.bookiosk.ddd.infrastructure;

/**
 * Marker class for infrastructure-layer converters.
 * Converts between persistent objects (PO) and domain
 * aggregates/entities. Pure technical mapping — no business logic.
 *
 * <p>Each converter defines its own conversion methods with
 * concrete source/target types. Naming: {@code {Business}Converter}.
 */
public abstract class BaseConverter {
}
