package org.bookiosk.ddd.exception;

/**
 * Blocking business exception (阻断型) thrown when business validation fails.
 *
 * <p>Thrown by DomainService / Adaptor. It propagates through inner layers WITHOUT
 * being caught — only the Application Service (AppService) catches it and converts
 * it to a {@code ResultDO.fail(code, msg)} at the single choke point.
 *
 * <p>Counterpart of branch-style failures: when the caller needs the failure data
 * to make a decision, return {@code ResultDO.fail(code, msg, data)} instead of throwing.
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String code;

    public BizException(String msg) {
        this("BIZ_ERROR", msg);
    }

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public String getCode() { return code; }

    public String getMsg() { return getMessage(); }
}
