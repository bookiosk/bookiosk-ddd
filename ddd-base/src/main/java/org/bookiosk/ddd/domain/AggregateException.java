package org.bookiosk.ddd.domain;

/**
 * Thrown by Aggregate roots and Entities when internal validation fails.
 * Caught by DomainService and converted to ResultDO failure.
 */
public class AggregateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String code;

    public AggregateException(String msg) {
        this("AGGREGATE_ERROR", msg);
    }

    public AggregateException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public String getCode() { return code; }
}
