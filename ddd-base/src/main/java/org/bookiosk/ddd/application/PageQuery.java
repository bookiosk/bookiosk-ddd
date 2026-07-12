package org.bookiosk.ddd.application;

import org.bookiosk.ddd.common.Response;

/**
 * Base class for paginated read queries.
 *
 * <pre>{@code
 * public class OrderPageQry extends PageQuery<PageResponse<OrderDTO>> {
 *     private String customerId;
 *     // getters, setters
 * }
 * }</pre>
 *
 * @param <R> the expected response type (typically PageResponse)
 */
public abstract class PageQuery<R extends Response> extends Query<R> {

    private static final long serialVersionUID = 1L;

    private int pageIndex = 1;
    private int pageSize = 20;

    public int getPageIndex() { return pageIndex; }
    public void setPageIndex(int pageIndex) { this.pageIndex = Math.max(1, pageIndex); }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = Math.min(200, Math.max(1, pageSize)); }

    /** Offset for SQL LIMIT clause */
    public int getOffset() { return (pageIndex - 1) * pageSize; }
}
