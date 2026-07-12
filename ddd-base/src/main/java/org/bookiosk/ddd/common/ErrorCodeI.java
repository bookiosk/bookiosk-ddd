package org.bookiosk.ddd.common;

/**
 * Error code interface. Implement as enum in each bounded context.
 *
 * <pre>{@code
 * public enum OrderErrorCode implements ErrorCodeI {
 *     ORDER_NOT_EXIST("B_ORDER_notExist", "Order {0} does not exist"),
 *     NEGATIVE_AMOUNT("B_ORDER_negativeAmount", "Amount must be positive");
 *
 *     private final String errCode;
 *     private final String errDesc;
 *     // constructor, getters
 * }
 * }</pre>
 *
 * Naming convention: B_ (business error, user-correctable) / S_ (system error, not user-correctable)
 */
public interface ErrorCodeI {

    String getErrCode();

    String getErrDesc();
}
