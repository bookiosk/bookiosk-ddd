package org.bookiosk.ddd.common;

/**
 * Response containing a single data object.
 *
 * @param <T> data type
 */
public class SingleResponse<T> extends Response {

    private static final long serialVersionUID = 1L;

    private final T data;

    private SingleResponse(boolean success, String errCode, String errMessage, T data) {
        super(success, errCode, errMessage);
        this.data = data;
    }

    public static <T> SingleResponse<T> of(T data) {
        return new SingleResponse<>(true, "", "", data);
    }

    public static <T> SingleResponse<T> fail(String errCode, String errMessage) {
        return new SingleResponse<>(false, errCode, errMessage, null);
    }

    public T getData() { return data; }

    @Override
    public String toString() {
        return isSuccess() ? "SingleResponse{data=" + data + "}" : "SingleResponse{fail, errCode=" + getErrCode() + "}";
    }
}
