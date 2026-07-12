package org.bookiosk.ddd.domain.statemachine;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class StateMachine<S, E, C> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String machineId;
    private final Map<S, State<S, E, C>> stateMap;

    StateMachine(String machineId, Map<S, State<S, E, C>> stateMap) {
        this.machineId = machineId;
        this.stateMap = Collections.unmodifiableMap(new LinkedHashMap<>(stateMap));
    }

    public String getMachineId() {
        return machineId;
    }

    public S fireEvent(S sourceState, E event, C context) {
        State<S, E, C> state = stateMap.get(sourceState);
        if (state == null) {
            throw new StateMachineException("StateMachine[" + machineId + "]: no state: " + sourceState);
        }
        Transition<S, E, C> t = state.getTransition(event);
        if (t == null) {
            throw new StateMachineException("StateMachine[" + machineId + "]: no transition from " + sourceState + " on " + event);
        }
        if (t.getGuard() != null && !t.getGuard().test(context)) {
            throw new StateMachineException("StateMachine[" + machineId + "]: guard rejected " + sourceState + " -> " + event);
        }
        if (t.getAction() != null) {
            t.getAction().accept(context);
        }
        if (t.getType() == Transition.TransitionType.INTERNAL) {
            return sourceState;
        }
        return t.getTarget();
    }

    public boolean verify(S sourceState, E event) {
        State<S, E, C> state = stateMap.get(sourceState);
        return state != null && state.getTransition(event) != null;
    }

    public String generatePlantUML() {
        StringBuilder sb = new StringBuilder();
        sb.append("@startuml ").append(machineId);
        sb.append(System.lineSeparator());
        sb.append("' StateMachine: ").append(machineId);
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        for (Map.Entry<S, State<S, E, C>> se : stateMap.entrySet()) {
            for (Map.Entry<E, Transition<S, E, C>> te : se.getValue().getTransitions().entrySet()) {
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
        return obj == null ? "null" : obj.toString().replaceAll("\"", "");
    }

    @Override
    public String toString() {
        return "StateMachine{machineId=" + machineId + ", states=" + stateMap.size() + "}";
    }
}
