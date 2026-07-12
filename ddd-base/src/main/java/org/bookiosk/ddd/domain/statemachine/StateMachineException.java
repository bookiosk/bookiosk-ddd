package org.bookiosk.ddd.domain.statemachine;

/**
 * Thrown by {@link StateMachine#fireEvent} when no valid transition is found or
 * a guard condition fails.
 *
 * <p>This is an unchecked exception ({@link RuntimeException}) so callers choose
 * whether to catch it or let it propagate. Typical use: wrap the call in the
 * application layer and convert to a domain-appropriate result.
 *
 * @see StateMachine#fireEvent
 */
public class StateMachineException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String code;

    /**
     * Constructs an exception with the default error code {@code STATE_MACHINE_ERROR}.
     *
     * @param message human-readable description of the failure
     */
    public StateMachineException(String message) {
        this("STATE_MACHINE_ERROR", message);
    }

    /**
     * Constructs an exception with a specific error code and message.
     *
     * @param code    machine-readable error code
     * @param message human-readable description of the failure
     */
    public StateMachineException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Returns the machine-readable error code.
     *
     * @return error code, never null
     */
    public String getCode() {
        return code;
    }
}
