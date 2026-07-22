package org.bookiosk.ddd.exception;

/**
 * Thrown by Repository implementations when data access fails.
 * Caught at infrastructure boundary and converted to ResultDO failure.
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
