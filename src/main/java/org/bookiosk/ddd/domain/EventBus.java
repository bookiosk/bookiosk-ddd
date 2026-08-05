package org.bookiosk.ddd.domain;

import java.util.List;

/**
 * Domain event bus — publish/subscribe for domain events.
 *
 * <p>Publishers and handlers share the same EventBus instance.
 * Publishing is synchronous by default (same thread, same transaction).
 * Async dispatch is an implementation concern in the Infrastructure layer.
 *
 * <p>On {@link #publish(DomainEvent)}, the bus auto-populates
 * {@link DomainEvent#traceId} and {@link DomainEvent#msgId} before
 * dispatching to handlers, enabling end-to-end tracing across
 * publisher and consumers.
 *
 * <p>Interface defined in Domain layer; implementation in Infrastructure
 * (e.g. Guava EventBus, Spring ApplicationEventPublisher, or
 * custom in-memory dispatcher).
 *
 * <h3>Usage</h3>
 * <pre>{@code
 * eventBus.register(OrderConfirmedEvent.class, event -> {
 *     log.info("Handling event: traceId={}, eventId={}", event.getTraceId(), event.getEventId());
 *     notificationService.send(event.getOrderId());
 * });
 *
 * eventBus.publish(new OrderConfirmedEvent(orderId));
 * }</pre>
 */
public interface EventBus {

    /**
     * Register a handler for an event type.
     * Multiple handlers may be registered for the same type;
     * they are invoked in registration order.
     *
     * @param eventType the event class
     * @param handler   the handler (supports lambda)
     */
    <E extends DomainEvent> void register(Class<E> eventType, EventHandler<E> handler);

    /**
     * Remove all handlers for an event type.
     */
    <E extends DomainEvent> void unregister(Class<E> eventType);

    /**
     * Publish an event to all registered handlers for its class hierarchy.
     * Auto-populates traceId and msgId on the event before dispatch.
     */
    void publish(DomainEvent event);

    /**
     * Publish multiple events. Each is dispatched to its handlers
     * before the next event is processed.
     */
    void publishAll(List<DomainEvent> events);
}
