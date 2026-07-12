package org.bookiosk.ddd.domain.statemachine;

/**
 * Thrown by {@link StateMachine#fireEvent} when no valid transition is found or
 * a guard condition fails.
 */
public class StateMachineException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String code;

    public StateMachineException(String message) {
        this("STATE_MACHINE_ERROR", message);
    }

    public StateMachineException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}
