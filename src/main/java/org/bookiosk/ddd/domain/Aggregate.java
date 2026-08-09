package org.bookiosk.ddd.domain;

/**
 * Marker interface for aggregate roots.
 *
 * <p>An aggregate wraps a group of tightly coupled entities and value objects
 * that are treated as a single unit of consistency. The root is the only
 * object that the outside world may reference; internal entities are never
 * accessed directly.
 *
 * <p>Per the framework contract the aggregate itself is an empty marker —
 * it carries no identity. Each business aggregate defines its own identity
 * (or identities, e.g. an order that spans two DB tables) in the concrete
 * class, so {@code BaseAggregate<ID>} merely declares the ID type used by the
 * repository without holding it.
 */
public interface Aggregate {
}