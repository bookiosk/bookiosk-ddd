package org.bookiosk.ddd.common;

import java.io.Serializable;

/**
 * Base response class. All layers return Response subtypes — exceptions never cross boundaries.
 * Check {@link #isSuccess()} before accessing data.
 *
 * <pre>{@code
 * SingleResponse<OrderId> response = orderService.createOrder(cmd);
 * if (response.isSuccess()) {
 *     OrderId orderId = response.getData();
 * }
 * }</pre>
 */
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    private final boolean success;
    private final String errCode;
    private final String errMessage;

    protected Response(boolean success, String errCode, String errMessage) {
        this.success = success;
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public static Response buildSuccess() {
        return new Response(true, "", "");
    }

    public static Response buildFailure(String errCode, String errMessage) {
        return new Response(false, errCode, errMessage);
    }

    public boolean isSuccess() { return success; }
    public boolean isFail() { return !success; }
    public String getErrCode() { return errCode; }
    public String getErrMessage() { return errMessage; }

    @Override
    public String toString() {
        return success ? "Response{success}" : "Response{fail, errCode=" + errCode + ", errMessage=" + errMessage + "}";
    }
}
