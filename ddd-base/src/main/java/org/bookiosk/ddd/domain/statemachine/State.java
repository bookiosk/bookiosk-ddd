package org.bookiosk.ddd.domain.statemachine;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Internal representation of a state node in the transition graph.
 *
 * <p>Holds a map from {@code event} to {@link Transition}. Instances are
 * created only by {@link StateMachineBuilder} and are immutable after
 * construction completes.
 *
 * @param <S> state type
 * @param <E> event type
 * @param <C> context type
 */
class State<S, E, C> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The identity of this state node. */
    private final S stateId;

    /** Event-to-transition mapping. */
    private final Map<E, Transition<S, E, C>> transitions;

    /**
     * Creates a new state node with the given identity and an empty transition table.
     *
     * @param stateId the state identifier
     */
    State(S stateId) {
        this.stateId = stateId;
        this.transitions = new HashMap<>();
    }

    S getStateId() {
        return stateId;
    }

    /**
     * Registers a transition for the given event.
     *
     * @param event      the trigger event
     * @param transition the transition to execute
     * @return the previously registered transition, or {@code null}
     */
    Transition<S, E, C> addTransition(E event, Transition<S, E, C> transition) {
        return transitions.put(event, transition);
    }

    /**
     * Looks up the transition for the given event.
     *
     * @param event the trigger event
     * @return the registered transition, or {@code null} if none exists
     */
    Transition<S, E, C> getTransition(E event) {
        return transitions.get(event);
    }

    /**
     * Returns an unmodifiable view of all transitions keyed by event.
     *
     * @return read-only transition map
     */
    Map<E, Transition<S, E, C>> getTransitions() {
        return Collections.unmodifiableMap(transitions);
    }
}
