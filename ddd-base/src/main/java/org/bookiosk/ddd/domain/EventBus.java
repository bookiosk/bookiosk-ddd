package org.bookiosk.ddd.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Central domain event dispatcher.
 * <p>
 * Maintains a registry of event types to their handlers. Handlers are invoked
 * in registration order. If any handler throws an exception, processing stops
 * immediately and the exception propagates to the caller.
 * <p>
 * Thread-safe: uses {@link ConcurrentHashMap} for the handler map and
 * {@link CopyOnWriteArrayList} for each handler list.
 */
public class EventBus {

    private final Map<Class<? extends DomainEventI>, List<EventHandlerI<?>>> handlerMap;

    public EventBus() {
        this.handlerMap = new ConcurrentHashMap<>();
    }

    /**
     * Synchronously fire a single domain event.
     * All registered handlers are invoked in registration order.
     * If any handler throws, the exception propagates and remaining handlers are skipped.
     *
     * @param event the domain event to fire
     * @param <T>   the event type
     */
    @SuppressWarnings("unchecked")
    public <T extends DomainEventI> void fire(T event) {
        List<EventHandlerI<?>> handlers = handlerMap.get(event.getClass());
        if (handlers == null || handlers.isEmpty()) {
            return;
        }
        for (EventHandlerI<?> handler : handlers) {
            ((EventHandlerI<T>) handler).handle(event);
        }
    }

    /**
     * Synchronously fire a list of domain events.
     * Each event is dispatched independently via {@link #fire(DomainEventI)}.
     *
     * @param events the list of domain events to fire
     * @param <T>    the event type
     */
    public <T extends DomainEventI> void fireAll(List<T> events) {
        if (events == null || events.isEmpty()) {
            return;
        }
        for (T event : events) {
            fire(event);
        }
    }

    /**
     * Asynchronously fire a domain event on a new thread.
     * The calling thread returns immediately; handlers run on a separate thread.
     * Exceptions thrown by handlers are not propagated to the caller.
     *
     * @param event the domain event to fire asynchronously
     * @param <T>   the event type
     */
    public <T extends DomainEventI> void asyncFire(T event) {
        new Thread(() -> fire(event)).start();
    }

    /**
     * Register an event handler for a specific event type.
     * Called during application startup to wire up handlers.
     *
     * @param eventType the event class the handler processes
     * @param handler   the handler instance
     * @param <T>       the event type
     */
    public <T extends DomainEventI> void register(Class<T> eventType, EventHandlerI<T> handler) {
        handlerMap.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(handler);
    }
}
