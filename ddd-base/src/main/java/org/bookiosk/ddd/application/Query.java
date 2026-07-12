package org.bookiosk.ddd.application;

import java.io.Serializable;

import org.bookiosk.ddd.common.Response;

/**
 * Base class for all read queries.
 * Queries bypass the Domain layer and call Mapper/DAO directly for performance.
 *
 * <pre>{@code
 * public class OrderGetQry extends Query<SingleResponse<OrderDTO>> {
 *     private String orderId;
 *     // getters, setters
 * }
 * }</pre>
 *
 * @param <R> the expected response type
 */
public abstract class Query<R extends Response> implements Serializable {

    private static final long serialVersionUID = 1L;
}
