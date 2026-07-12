package org.bookiosk.ddd.common;

import java.io.Serializable;
import java.util.Objects;

/**
 * Universal operation result wrapper.
 * All methods across all layers return ResultDO — exceptions never cross layer boundaries.
 *
 * @param <T> data payload type, Void for operations with no return data
 */
public class ResultDO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final boolean success;
    private final String code;
    private final String msg;
    private final T data;

    private ResultDO(boolean success, String code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultDO<T> buildSuccessResult(T data) {
        return new ResultDO<>(true, "SUCCESS", "", data);
    }

    public static <T> ResultDO<T> buildSuccessResult() {
        return new ResultDO<>(true, "SUCCESS", "", null);
    }

    public static <T> ResultDO<T> buildFailResult(String code, String msg) {
        return new ResultDO<>(false, Objects.requireNonNull(code, "code"), Objects.requireNonNull(msg, "msg"), null);
    }

    public static <T> ResultDO<T> buildFailResult(String msg) {
        return new ResultDO<>(false, "ERROR", Objects.requireNonNull(msg, "msg"), null);
    }

    public boolean isSuccess() { return success; }
    public boolean isFail() { return !success; }
    public String getCode() { return code; }
    public String getMsg() { return msg; }
    public T getData() { return data; }

    @Override
    public String toString() {
        return success
            ? "ResultDO{success, data=" + data + "}"
            : "ResultDO{fail, code=" + code + ", msg=" + msg + "}";
    }
}
