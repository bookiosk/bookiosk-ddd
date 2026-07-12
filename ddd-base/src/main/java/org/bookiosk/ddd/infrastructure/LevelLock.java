package org.bookiosk.ddd.infrastructure;

/**
 * Distributed lock abstraction for concurrency safety in write operations.
 * Constructed by Repository implementation in the Infrastructure layer.
 *
 * <pre>{@code
 * LevelLock lock = orderRepository.buildLock("order:confirmPayment:" + orderId);
 * try {
 *     if (!lock.tryLock()) {
 *         return ResultDO.buildFailResult("LOCK_FAIL", "Failed to acquire lock");
 *     }
 *     // ... business logic ...
 * } finally {
 *     lock.unlock();
 * }
 * }</pre>
 */
public class LevelLock {

    private final String lockKey;

    public LevelLock(String lockKey) {
        this.lockKey = lockKey;
    }

    /** Attempt to acquire the lock (non-blocking). Override for real implementation. */
    public boolean tryLock() {
        return true;
    }

    /** Release the lock. Override for real implementation. */
    public void unlock() {
    }

    public String getLockKey() {
        return lockKey;
    }
}
