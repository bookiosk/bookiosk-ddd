package org.bookiosk.ddd.domain;

/**
 * Marker interface for Domain Gateway interfaces.
 * Gateways abstract external capabilities (RPC, 3rd-party API, cache) the domain needs.
 * Defined in Domain layer, implemented in Infrastructure layer.
 *
 * Dependencies always point: Infrastructure → Domain (never Domain → Infrastructure).
 *
 * <pre>{@code
 * public interface InventoryGateway extends GatewayI {
 *     boolean checkStock(String skuId, int quantity);
 * }
 *
 * public interface PaymentGateway extends GatewayI {
 *     PaymentResult charge(PaymentParam param);
 * }
 * }</pre>
 */
public interface GatewayI {
}
