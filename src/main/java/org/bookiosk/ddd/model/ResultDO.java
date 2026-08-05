package org.bookiosk.ddd.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Universal operation result wrapper.
 *
 * <p>Used in two modes:
 * <ul>
 *   <li><b>Branch (分支型)</b>: returned from DomainService/Adaptor when the caller needs
 *       the failure data to make a decision — use {@link #buildFailResult(String, String, Object)}
 *       to carry data.</li>
 *   <li><b>Contract envelope</b>: returned from AppService to clients. Blocking exceptions
 *       ({@code BizException}/{@code AggregateException}/{@code RepositoryException}) are
 *       caught at the AppService choke point and converted here.</li>
 * </ul>
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

    /**
     * 分支型失败结果 —— 携带 data 供调用方分支决策（如重复下单返回已有订单、缓存未命中返回 key）。
     */
    public static <T> ResultDO<T> buildFailResult(String code, String msg, T data) {
        return new ResultDO<>(false, Objects.requireNonNull(code, "code"), Objects.requireNonNull(msg, "msg"), data);
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
