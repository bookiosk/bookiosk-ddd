package org.bookiosk.ddd.common;

import java.util.Objects;

/**
 * Assertion utility to replace manual if-throw validation blocks.
 * All methods throw {@link BizException} when the check fails.
 *
 * <pre>{@code
 * // Instead of:
 * if (customer == null) throw new BizException("B_CUSTOMER_notFound", "Customer not found");
 *
 * // Use:
 * Assert.notNull(customer, OrderErrorCode.ORDER_NOT_EXIST);
 * Assert.isTrue(amount > 0, OrderErrorCode.NEGATIVE_AMOUNT);
 * }</pre>
 */
public final class Assert {

    private Assert() {}

    public static void notNull(Object obj, ErrorCodeI errorCode) {
        if (obj == null) throw newBizException(errorCode);
    }

    public static void notNull(Object obj, ErrorCodeI errorCode, String message) {
        if (obj == null) throw newBizException(errorCode, message);
    }

    public static void isTrue(boolean condition, ErrorCodeI errorCode) {
        if (!condition) throw newBizException(errorCode);
    }

    public static void isTrue(boolean condition, ErrorCodeI errorCode, String message) {
        if (!condition) throw newBizException(errorCode, message);
    }

    public static void notEmpty(String str, ErrorCodeI errorCode) {
        if (str == null || str.isEmpty()) throw newBizException(errorCode);
    }

    public static void notEmpty(String str, ErrorCodeI errorCode, String message) {
        if (str == null || str.isEmpty()) throw newBizException(errorCode, message);
    }

    public static void notBlank(String str, ErrorCodeI errorCode) {
        if (str == null || str.trim().isEmpty()) throw newBizException(errorCode);
    }

    public static void notBlank(String str, ErrorCodeI errorCode, String message) {
        if (str == null || str.trim().isEmpty()) throw newBizException(errorCode, message);
    }

    private static BizException newBizException(ErrorCodeI errorCode) {
        return new BizException(errorCode.getErrCode(), errorCode.getErrDesc());
    }

    private static BizException newBizException(ErrorCodeI errorCode, String message) {
        return new BizException(errorCode.getErrCode(),
            Objects.requireNonNull(message, "message"));
    }
}
