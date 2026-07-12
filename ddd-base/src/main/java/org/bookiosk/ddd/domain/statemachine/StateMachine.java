package org.bookiosk.ddd.domain.statemachine;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A thread-safe, <b>stateless</b> state machine.
 *
 * <p>The machine itself holds only the transition graph. It does <b>not</b> store
 * the current state. Callers pass the current state into {@link #fireEvent} and
 * receive the new state as a return value. This makes the machine reusable across
 * multiple domain objects without shared mutable state.
 *
 * <h3>Usage</h3>
 * <pre>{@code
 * StateMachine<OrderStatus, OrderEvent, OrderContext> machine =
 *     StateMachineFactory.get("order");
 *
 * OrderStatus next = machine.fireEvent(currentStatus, OrderEvent.PAY, context);
 * }</pre>
 *
 * <h3>Thread safety</h3>
 * The transition graph is built once via {@link StateMachineBuilder} and never
 * mutated afterward. Instances are safe for concurrent use.
 *
 * @param <S> state type
 * @param <E> event type
 * @param <C> context type
 * @see StateMachineBuilder
 * @see StateMachineFactory
 */
public class StateMachine<S, E, C> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String machineId;
    private final Map<S, State<S, E, C>> stateMap;

    /**
     * Package-private constructor; instances are created by {@link StateMachineBuilder#build}.
     */
    StateMachine(String machineId, Map<S, State<S, E, C>> stateMap) {
        this.machineId = machineId;
        this.stateMap = Collections.unmodifiableMap(new LinkedHashMap<>(stateMap));
    }

    /**
     * Returns the unique identifier for this machine.
     */
    public String getMachineId() {
        return machineId;
    }

    /**
     * Fires an event against the given source state and context.
     *
     * <p>Processing steps:
     * <ol>
     *   <li>Look up the source state in the transition graph.</li>
     *   <li>Find the transition registered for the given event.</li>
     *   <li>Evaluate the guard (if present). If it returns {@code false},
     *       a {@link StateMachineException} is thrown.</li>
     *   <li>Execute the action (if present).</li>
     *   <li>Return the <b>new</b> state for EXTERNAL transitions, or the
     *       <b>same</b> source state for INTERNAL transitions.</li>
     * </ol>
     *
     * @param sourceState the current state before the event
     * @param event       the trigger event
     * @param context     domain context for guard evaluation and action execution
     * @return the new state after the transition
     * @throws StateMachineException if no transition exists or the guard rejects
     */
    public S fireEvent(S sourceState, E event, C context) {
        State<S, E, C> state = stateMap.get(sourceState);
        if (state == null) {
            throw new StateMachineException(
                    "StateMachine[" + machineId + "]: no state: " + sourceState);
        }
        Transition<S, E, C> t = state.getTransition(event);
        if (t == null) {
            throw new StateMachineException(
                    "StateMachine[" + machineId + "]: no transition from "
                            + sourceState + " on " + event);
        }
        if (t.getGuard() != null && !t.getGuard().test(context)) {
            throw new StateMachineException(
                    "StateMachine[" + machineId + "]: guard rejected "
                            + sourceState + " -> " + event);
        }
        if (t.getAction() != null) {
            t.getAction().accept(context);
        }
        if (t.getType() == Transition.TransitionType.INTERNAL) {
            return sourceState;
        }
        return t.getTarget();
    }

    /**
     * Verifies that a transition exists from {@code sourceState} on {@code event}.
     *
     * <p>Checks only the <b>existence</b> of a transition, not whether the guard
     * would pass. Useful for tests and startup-time validation.
     *
     * @param sourceState the source state
     * @param event       the trigger event
     * @return {@code true} if a transition is registered, {@code false} otherwise
     */
    public boolean verify(S sourceState, E event) {
        State<S, E, C> state = stateMap.get(sourceState);
        return state != null && state.getTransition(event) != null;
    }

    /**
     * Generates a <a href="https://plantuml.com/state-diagram">PlantUML</a>
     * state diagram from the transition graph.
     *
     * <p>Output includes guard and action annotations. Use to visualize the
     * machine during development or include in documentation.
     *
     * @return PlantUML source as a string
     */
    public String generatePlantUML() {
        StringBuilder sb = new StringBuilder();
        sb.append("@startuml ").append(machineId);
        sb.append(System.lineSeparator());
        sb.append("' StateMachine: ").append(machineId);
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        for (Map.Entry<S, State<S, E, C>> se : stateMap.entrySet()) {
            for (Map.Entry<E, Transition<S, E, C>> te
                    : se.getValue().getTransitions().entrySet()) {
                sb.append(plantUmlName(se.getKey()));
                sb.append(" --> ");
                sb.append(plantUmlName(te.getValue().getTarget()));
                sb.append(" : ");
                sb.append(plantUmlName(te.getKey()));
                if (te.getValue().getType() == Transition.TransitionType.INTERNAL) {
                    sb.append(" [internal]");
                }
                if (te.getValue().getGuard() != null) {
                    sb.append(" [guard]");
                }
                if (te.getValue().getAction() != null) {
                    sb.append(" [action]");
                }
                sb.append(System.lineSeparator());
            }
        }
        sb.append("@enduml");
        sb.append(System.lineSeparator());
        return sb.toString();
    }

    private static String plantUmlName(Object obj) {
        return obj == null ? "null" : obj.toString();
    }

    @Override
    public String toString() {
        return "StateMachine{machineId=" + machineId
                + ", states=" + stateMap.size() + "}";
    }
}
