package org.bookiosk.ddd.common;

/**
 * Thrown when business logic validation fails.
 * Caught by application layer and converted to Response failure.
 * Use with {@link Assert} for concise validation.
 *
 * Convention: error codes prefixed with B_ (business, user-correctable) or S_ (system, non-user-correctable).
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
