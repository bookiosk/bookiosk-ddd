package org.bookiosk.ddd.domain;

import java.io.Serializable;

/**
 * Base class for all domain method result objects.
 * Named {MethodName}Result, may contain rich (behavior) methods in addition to data.
 */
public abstract class BaseResult implements Serializable {

    private static final long serialVersionUID = 1L;
}
