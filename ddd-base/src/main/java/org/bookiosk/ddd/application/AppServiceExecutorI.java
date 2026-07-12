package org.bookiosk.ddd.application;

import org.bookiosk.ddd.common.Response;

/**
 * Executor interface — one implementation per use case.
 * Command executors go through Domain layer. Query executors bypass Domain and call Mapper/DAO directly.
 *
 * <pre>{@code
 * // Command executor (goes through Domain)
 * public class OrderCreateCmdExe implements AppServiceExecutorI<CreateOrderCmd, SingleResponse<OrderId>> {
 *     public SingleResponse<OrderId> execute(CreateOrderCmd cmd) {
 *         Order order = Order.create(cmd.getCustomerId(), cmd.getItems());
 *         orderRepository.save(order);
 *         return SingleResponse.of(order.getId());
 *     }
 * }
 *
 * // Query executor (bypasses Domain)
 * public class OrderGetQryExe implements AppServiceExecutorI<OrderGetQry, SingleResponse<OrderDTO>> {
 *     public SingleResponse<OrderDTO> execute(OrderGetQry qry) {
 *         OrderDTO dto = orderMapper.selectById(qry.getOrderId());
 *         return SingleResponse.of(dto);
 *     }
 * }
 * }</pre>
 *
 * @param <REQ> request type (Command or Query)
 * @param <RES> response type (SingleResponse, MultiResponse, PageResponse, etc.)
 */
public interface AppServiceExecutorI<REQ, RES extends Response> {

    RES execute(REQ request);
}
