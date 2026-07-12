package org.bookiosk.ddd.infrastructure;

/**
 * Distributed lock abstraction for concurrency safety in write operations.
 * Implementations should use Redis, ZooKeeper, or database locks.
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
public abstract class LevelLock {

    private final String lockKey;

    protected LevelLock(String lockKey) {
        this.lockKey = lockKey;
    }

    /** Attempt to acquire the lock (non-blocking). */
    public abstract boolean tryLock();

    /** Release the lock. */
    public abstract void unlock();

    public String getLockKey() {
        return lockKey;
    }
}
