package org.bookiosk.ddd.domain;

/**
 * Handler for a specific type of domain event.
 *
 * <p>Handlers are registered on the {@link EventBus} and invoked
 * when matching events are published.
 *
 * @param <E> the event type this handler processes
 */
@FunctionalInterface
public interface EventHandler<E extends DomainEvent> {

    /**
     * Handle a domain event.
     *
     * @param event the domain event (contains traceId/msgId for logging)
     */
    void handle(E event);
}
