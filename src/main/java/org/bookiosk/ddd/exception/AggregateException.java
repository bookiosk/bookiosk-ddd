package org.bookiosk.ddd.exception;

/**
 * Thrown by Aggregate roots and Entities when internal validation fails.
 * Caught by DomainService and converted to ResultDO failure.
 */
public class AggregateException extends BizException {

    private static final long serialVersionUID = 1L;

    public AggregateException(String msg) {
        super("AGGREGATE_ERROR", msg);
    }

    public AggregateException(String code, String msg) {
        super(code, msg);
    }
}
