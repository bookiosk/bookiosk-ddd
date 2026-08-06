package org.bookiosk.ddd.repository;

import org.bookiosk.ddd.domain.BaseAggregate;

import java.io.Serializable;
import java.util.List;

/**
 * Base repository interface for aggregate persistence.
 * Defined in Domain layer, implemented in Infrastructure layer.
 *
 * <p>Contract: after {@code save()} the passed instance is stale — continue
 * working by re-querying via {@code findById}, so change flags always start
 * fresh. Persist incrementally using {@link org.bookiosk.ddd.domain.ChangeableUtil#collectChanged}
 * for scalar {@code Field} properties and diff collection properties against
 * the persisted state.
 *
 * <p>查询约定：有就是有、无就是 null——不使用 Optional。
 * 调用方用 null 判断即可（如 {@code order == null} 表示不存在）。
 *
 * @param <T> aggregate type (must extend BaseAggregate)
 * @param <ID> aggregate identity type
 * @param <Q> query condition type for conditional lookups
 */
public interface AggregateRepository<T extends BaseAggregate<ID>, ID extends Serializable, Q> extends Repository{

    void save(T aggregate);

    T findById(ID id);

    List<T> findAll(Q query);

    void remove(ID id);

    boolean existsById(ID id);
}
