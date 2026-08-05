package org.bookiosk.ddd.exception;

/**
 * Blocking business exception (阻断型) thrown by Repository implementations
 * when data access fails (DB / cache unavailable, optimistic-lock miss, etc.).
 *
 * <p>Propagates through DomainService / Adaptor without being caught — only the
 * Application Service (AppService) catches it and converts it to
 * {@code ResultDO.fail(code, msg)} at the single choke point.
 */
public class RepositoryException extends BizException {

    private static final long serialVersionUID = 1L;

    public RepositoryException(String msg) {
        super("REPOSITORY_ERROR", msg);
    }

    public RepositoryException(String code, String msg) {
        super(code, msg);
    }
}
