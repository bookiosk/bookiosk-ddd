package org.bookiosk.ddd.domain;

/**
 * Handler interface for processing domain events.
 * Each implementation handles a specific type of domain event.
 *
 * @param <T> the domain event type this handler processes
 */
public interface EventHandlerI<T extends DomainEventI> {

    /**
     * Handle the given domain event.
     * Implementations should be idempotent where possible.
     *
     * @param event the domain event to process
     */
    void handle(T event);
}
