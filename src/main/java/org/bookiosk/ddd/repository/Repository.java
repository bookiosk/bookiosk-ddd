package org.bookiosk.ddd.repository;

/**
 * Top-level marker interface for all repository interfaces.
 * Parent of {@link AggregateRepository}.
 *
 * <p>Repository interfaces are defined in the Domain layer and
 * implemented in the Infrastructure layer, following the
 * Dependency Inversion Principle (Hexagonal Architecture).
 *
 * @author bookiosk
 */
public interface Repository {
}
