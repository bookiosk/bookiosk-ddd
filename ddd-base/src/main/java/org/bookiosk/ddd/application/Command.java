package org.bookiosk.ddd.application;

import java.io.Serializable;

import org.bookiosk.ddd.common.Response;

/**
 * Base class for all write commands.
 * Every command declares its return type at compile time via the generic parameter.
 *
 * <pre>{@code
 * public class CreateOrderCmd extends Command<SingleResponse<OrderId>> {
 *     private String customerId;
 *     private List<OrderItemDTO> items;
 *     // getters, setters
 * }
 * }</pre>
 *
 * @param <R> the expected response type (SingleResponse, MultiResponse, PageResponse, etc.)
 */
public abstract class Command<R extends Response> implements Serializable {

    private static final long serialVersionUID = 1L;
}
