package org.bookiosk.ddd.exception;

/**
 * Thrown when DomainService business logic validation fails.
 * Caught by DomainService itself and converted to ResultDO failure.
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
}
