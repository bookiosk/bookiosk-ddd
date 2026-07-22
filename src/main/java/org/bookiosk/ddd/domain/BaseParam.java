package org.bookiosk.ddd.domain;

import java.io.Serializable;

/**
 * Base class for all domain method parameter objects.
 * Named {MethodName}Param, used as input to DomainService and aggregate methods.
 */
public abstract class BaseParam implements Serializable {

    private static final long serialVersionUID = 1L;
}
