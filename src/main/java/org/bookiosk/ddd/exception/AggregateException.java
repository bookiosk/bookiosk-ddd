package org.bookiosk.ddd.exception;

/**
 * Blocking business exception (阻断型) thrown by Aggregate roots and Entities
 * when internal invariant validation fails.
 *
 * <p>Propagates through inner layers (DomainService / Adaptor / Infrastructure)
 * without being caught — only the Application Service (AppService) catches it
 * and converts it to {@code ResultDO.fail(code, msg)} at the single choke point.
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
