package org.bookiosk.ddd.domain.statemachine;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Represents a single transition between states in response to an event.
 *
 * <p>A transition may carry:
 * <ul>
 *   <li>a {@link #getGuard() guard} ({@link Predicate}) — evaluated before the transition fires;
 *       if it returns {@code false} the transition is rejected.</li>
 *   <li>an {@link #getAction() action} ({@link Consumer}) — executed when the transition fires.</li>
 * </ul>
 *
 * <p><b>Note on serialization:</b> {@link Predicate} and {@link Consumer} lambdas are not
 * serializable by default. If you need to serialize a state machine, cast your lambdas to
 * {@code (Serializable &amp; Predicate&lt;C&gt;)} or use named classes.
 *
 * @param <S> state type
 * @param <E> event type
 * @param <C> context type
 */
class Transition<S, E, C> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The state in which this transition originates. */
    private final S source;

    /** The state to which this transition leads (same as source for {@link TransitionType#INTERNAL}). */
    private final S target;

    /** Optional guard evaluated before the transition fires. */
    private final Predicate<C> guard;

    /** Optional side-effect executed when the transition fires. */
    private final Consumer<C> action;

    /** Whether this is an EXTERNAL or INTERNAL transition. */
    private final TransitionType type;

    Transition(S source, S target, Predicate<C> guard, Consumer<C> action, TransitionType type) {
        this.source = source;
        this.target = target;
        this.guard = guard;
        this.action = action;
        this.type = type;
    }

    S getSource() {
        return source;
    }

    S getTarget() {
        return target;
    }

    Predicate<C> getGuard() {
        return guard;
    }

    Consumer<C> getAction() {
        return action;
    }

    TransitionType getType() {
        return type;
    }

    /**
     * Classification of a transition.
     */
    public enum TransitionType {
        /**
         * The state changes from source to target — {@code fireEvent} returns the <b>new</b> state.
         */
        EXTERNAL,

        /**
         * The state does <b>not</b> change — only the action runs.
         * {@code fireEvent} returns the <b>same</b> source state.
         */
        INTERNAL
    }
}
