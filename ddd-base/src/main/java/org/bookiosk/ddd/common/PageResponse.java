package org.bookiosk.ddd.common;

import java.util.Collections;
import java.util.List;

/**
 * Response containing paginated data.
 *
 * @param <T> element type
 */
public class PageResponse<T> extends Response {

    private static final long serialVersionUID = 1L;

    private final List<T> data;
    private final int totalCount;
    private final int pageSize;
    private final int pageIndex;

    private PageResponse(boolean success, String errCode, String errMessage,
                         List<T> data, int totalCount, int pageSize, int pageIndex) {
        super(success, errCode, errMessage);
        this.data = data == null ? Collections.emptyList() : Collections.unmodifiableList(data);
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    public static <T> PageResponse<T> of(List<T> data, int totalCount, int pageSize, int pageIndex) {
        return new PageResponse<>(true, "", "", data, totalCount, pageSize, pageIndex);
    }

    public static <T> PageResponse<T> fail(String errCode, String errMessage) {
        return new PageResponse<>(false, errCode, errMessage, null, 0, 0, 0);
    }

    public List<T> getData() { return data; }
    public int getTotalCount() { return totalCount; }
    public int getPageSize() { return pageSize; }
    public int getPageIndex() { return pageIndex; }
    public int getTotalPages() { return pageSize > 0 ? (totalCount + pageSize - 1) / pageSize : 0; }
    public boolean isEmpty() { return data.isEmpty(); }
    public boolean isNotEmpty() { return !data.isEmpty(); }
    public boolean hasMore() { return pageIndex < getTotalPages(); }

    @Override
    public String toString() {
        return isSuccess()
            ? "PageResponse{page=" + pageIndex + "/" + getTotalPages() + ", total=" + totalCount + "}"
            : "PageResponse{fail, errCode=" + getErrCode() + "}";
    }
}
