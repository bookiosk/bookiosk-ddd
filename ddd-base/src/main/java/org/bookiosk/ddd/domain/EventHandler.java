package org.bookiosk.ddd.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Runtime annotation for auto-registration of event handlers.
 * <p>
 * When {@link #value()} is left at the default {@code None.class}, the event type
 * is inferred from the generic parameter of {@link EventHandlerI} implemented by the class.
 * <p>
 * Use the explicit form when a single handler class needs to declare a specific event type
 * that differs from what the generic alone would resolve to.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventHandler {

    /**
     * The event type this handler processes.
     * Defaults to {@code None.class}, which means "infer from the generic parameter of EventHandlerI".
     */
    Class<? extends DomainEventI> value() default None.class;

    /**
     * Sentinel class used to indicate that the event type should be inferred
     * from the {@code EventHandlerI} generic parameter.
     */
    final class None implements DomainEventI {
        private None() {
            throw new UnsupportedOperationException("None is a sentinel, not an event type");
        }
    }
}
