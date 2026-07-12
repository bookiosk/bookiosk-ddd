package org.bookiosk.ddd.domain.statemachine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Static registry for {@link StateMachine} instances.
 *
 * <p>State machines are typically built once at startup (using {@link StateMachineBuilder})
 * and registered here. Application code retrieves them by id via {@link #get}.
 *
 * <pre>{@code
 * StateMachineFactory.register(
 *     StateMachineBuilder.<Status, Event, Context>create()
 *         .externalTransition()
 *             .from(Status.DRAFT).to(Status.SUBMITTED).on(Event.SUBMIT)
 *             .build()
 *         .build("order-workflow"));
 *
 * StateMachine<Status, Event, Context> machine = StateMachineFactory.get("order-workflow");
 * }</pre>
 *
 * <p>Thread-safe (backed by {@link ConcurrentHashMap}).
 */
public final class StateMachineFactory {

    private static final Map<String, StateMachine<?, ?, ?>> machines = new ConcurrentHashMap<>();

    private StateMachineFactory() {
    }

    @SuppressWarnings("unchecked")
    public static <S, E, C> StateMachine<S, E, C> get(String machineId) {
        StateMachine<?, ?, ?> machine = machines.get(machineId);
        if (machine == null) {
            throw new StateMachineException("No StateMachine registered: " + machineId);
        }
        return (StateMachine<S, E, C>) machine;
    }

    public static <S, E, C> void register(StateMachine<S, E, C> machine) {
        machines.put(machine.getMachineId(), machine);
    }

    public static void unregister(String machineId) {
        machines.remove(machineId);
    }

    public static int size() {
        return machines.size();
    }

    public static void clear() {
        machines.clear();
    }
}
