package org.bookiosk.ddd.client;

import java.io.Serializable;

/**
 * Base class for all Data Transfer Objects (DTOs) in the Client layer.
 * All RequestDTO, ResponseDTO, and shared nested DTOs extend this.
 * Implements Serializable for RPC transmission.
 */
public abstract class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
}
