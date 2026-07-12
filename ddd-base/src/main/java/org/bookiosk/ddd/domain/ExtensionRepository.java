package org.bookiosk.ddd.domain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * In-memory registry that maps {@link ExtensionCoordinate}s to concrete
 * {@link ExtensionPointI} implementation classes.
 *
 * Populated at startup by scanning classes annotated with {@link Extension}.
 * Lookups use progressive fallback: if an exact match is not found the
 * repository falls back through increasingly generic coordinates until it
 * reaches the universal default.
 */
public class ExtensionRepository {

    private final Map<ExtensionCoordinate, Class<?>> registry = new LinkedHashMap<>();

    /**
     * Register a concrete extension implementation.
     * The {@code @Extension} annotation on {@code extensionImplClz} provides
     * the coordinate dimensions.
     */
    public void register(Class<?> extensionPtClz, Class<?> extensionImplClz) {
        if (!extensionPtClz.isAssignableFrom(extensionImplClz)) {
            throw new IllegalArgumentException(
                extensionImplClz.getName() + " does not implement " + extensionPtClz.getName());
        }

        Extension ext = extensionImplClz.getAnnotation(Extension.class);
        if (ext == null) {
            throw new IllegalArgumentException(
                extensionImplClz.getName() + " is not annotated with @Extension");
        }

        BizScenario bizScenario = BizScenario.of(ext.bizId(), ext.useCase(), ext.scenario());
        ExtensionCoordinate coordinate = new ExtensionCoordinate(extensionPtClz, bizScenario);

        if (registry.containsKey(coordinate)) {
            throw new IllegalStateException("Duplicate extension registration for " + coordinate);
        }

        registry.put(coordinate, extensionImplClz);
    }

    /**
     * Find the best-matching implementation class using progressive fallback.
     * Fallback chain: exact → scenario default → useCase default → universal default.
     *
     * @return matching Class, or null if none found
     */
    public Class<?> find(Class<?> extensionPtClz, BizScenario bizScenario) {
        // 1. exact match
        Class<?> impl = lookup(extensionPtClz, bizScenario);
        if (impl != null) return impl;

        // 2. scenario → "default"
        if (!BizScenario.DEFAULT_SCENARIO.equals(bizScenario.getScenario())) {
            BizScenario fb1 = BizScenario.of(bizScenario.getBizId(), bizScenario.getUseCase(), BizScenario.DEFAULT_SCENARIO);
            impl = lookup(extensionPtClz, fb1);
            if (impl != null) return impl;
        }

        // 3. useCase → "default"
        if (!BizScenario.DEFAULT_USE_CASE.equals(bizScenario.getUseCase())) {
            BizScenario fb2 = BizScenario.of(bizScenario.getBizId(), BizScenario.DEFAULT_USE_CASE, BizScenario.DEFAULT_SCENARIO);
            impl = lookup(extensionPtClz, fb2);
            if (impl != null) return impl;
        }

        // 4. universal default
        if (!BizScenario.DEFAULT_BIZ_ID.equals(bizScenario.getBizId())) {
            BizScenario fb3 = BizScenario.of(BizScenario.DEFAULT_BIZ_ID, BizScenario.DEFAULT_USE_CASE, BizScenario.DEFAULT_SCENARIO);
            impl = lookup(extensionPtClz, fb3);
            if (impl != null) return impl;
        }

        return null;
    }

    private Class<?> lookup(Class<?> extensionPtClz, BizScenario bizScenario) {
        return registry.get(new ExtensionCoordinate(extensionPtClz, bizScenario));
    }
}
