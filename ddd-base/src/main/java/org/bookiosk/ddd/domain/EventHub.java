package org.bookiosk.ddd.domain;

import java.util.List;

/**
 * Static singleton accessor for the domain {@link EventBus}.
 * <p>
 * Provides a simplified API without requiring Spring dependency injection.
 * All calls delegate to a single {@code EventBus} instance held internally.
 * <p>
 * Usage:
 * <pre>{@code
 * // Startup registration
 * EventHub.register(OrderCreatedEvent.class, new SendSmsHandler());
 * EventHub.register(OrderCreatedEvent.class, new AuditLogHandler());
 *
 * // Fire from domain
 * EventHub.fire(new OrderCreatedEvent(orderId));
 * EventHub.asyncFire(new OrderPaidEvent(orderId));
 * }</pre>
 */
public final class EventHub {

    private static final EventBus INSTANCE = new EventBus();

    private EventHub() {
        throw new UnsupportedOperationException("Utility class - do not instantiate");
    }

    /**
     * Register an event handler for the given event type.
     *
     * @param eventType the event class the handler processes
     * @param handler   the handler instance
     * @param <T>       the event type
     */
    public static <T extends DomainEventI> void register(Class<T> eventType, EventHandlerI<T> handler) {
        INSTANCE.register(eventType, handler);
    }

    /**
     * Synchronously fire a single domain event.
     *
     * @param event the domain event to fire
     * @param <T>   the event type
     */
    public static <T extends DomainEventI> void fire(T event) {
        INSTANCE.fire(event);
    }

    /**
     * Synchronously fire a list of domain events.
     *
     * @param events the list of domain events to fire
     * @param <T>    the event type
     */
    public static <T extends DomainEventI> void fireAll(List<T> events) {
        INSTANCE.fireAll(events);
    }

    /**
     * Asynchronously fire a domain event on a new thread.
     * The calling thread returns immediately.
     *
     * @param event the domain event to fire asynchronously
     * @param <T>   the event type
     */
    public static <T extends DomainEventI> void asyncFire(T event) {
        INSTANCE.asyncFire(event);
    }
}
