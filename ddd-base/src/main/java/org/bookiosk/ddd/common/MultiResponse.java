package org.bookiosk.ddd.common;

import java.util.Collections;
import java.util.List;

/**
 * Response containing a list of data objects.
 * Never returns null list — empty list when no results.
 *
 * @param <T> element type
 */
public class MultiResponse<T> extends Response {

    private static final long serialVersionUID = 1L;

    private final List<T> data;

    private MultiResponse(boolean success, String errCode, String errMessage, List<T> data) {
        super(success, errCode, errMessage);
        this.data = data == null ? Collections.emptyList() : Collections.unmodifiableList(data);
    }

    public static <T> MultiResponse<T> of(List<T> data) {
        return new MultiResponse<>(true, "", "", data);
    }

    public static <T> MultiResponse<T> fail(String errCode, String errMessage) {
        return new MultiResponse<>(false, errCode, errMessage, null);
    }

    public List<T> getData() { return data; }
    public boolean isEmpty() { return data.isEmpty(); }
    public boolean isNotEmpty() { return !data.isEmpty(); }
    public int size() { return data.size(); }

    @Override
    public String toString() {
        return isSuccess() ? "MultiResponse{size=" + data.size() + "}" : "MultiResponse{fail, errCode=" + getErrCode() + "}";
    }
}
