package org.bookiosk.ddd.domain;

import java.io.Serializable;
import java.util.UUID;

/**
 * Base class for all domain events.
 *
 * <p>Provides common fields for tracing, idempotency, and observability.
 * Every domain event inherits these automatically — no need to define them per event.
 *
 * <p>Events are named in past tense: {@code OrderConfirmedEvent},
 * {@code PaymentReceivedEvent}, etc.
 *
 * <p>Published via {@link EventBus} and handled by {@link EventHandler}.
 */
public abstract class DomainEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Globally unique event ID (UUID), used for idempotency and deduplication. */
    private final String eventId;

    /** Trace ID propagated from the initiating request, links events across services. */
    private String traceId;

    /** Message ID for async messaging scenarios, correlates publish and consume logs. */
    private String msgId;

    /** Timestamp when the event occurred, set at construction time. */
    private final long occurredAt;

    /** Simple name of the source aggregate class. */
    private String aggregateType;

    /** Business identity of the source aggregate. */
    private String aggregateId;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID().toString().replace("-", "");
        this.occurredAt = System.currentTimeMillis();
    }

    // -- Getters --

    public final String getEventId() { return eventId; }
    public final long getOccurredAt() { return occurredAt; }

    public String getTraceId() { return traceId; }
    public String getMsgId() { return msgId; }
    public String getAggregateType() { return aggregateType; }
    public String getAggregateId() { return aggregateId; }

    // -- Setters (for use by EventBus implementation or aggregate when publishing) --

    public void setTraceId(String traceId) { this.traceId = traceId; }
    public void setMsgId(String msgId) { this.msgId = msgId; }
    public void setAggregateType(String aggregateType) { this.aggregateType = aggregateType; }
    public void setAggregateId(String aggregateId) { this.aggregateId = aggregateId; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{"
                + "eventId='" + eventId + '\''
                + ", traceId='" + traceId + '\''
                + ", msgId='" + msgId + '\''
                + ", aggregateType='" + aggregateType + '\''
                + ", aggregateId='" + aggregateId + '\''
                + '}';
    }
}
