package org.bookiosk.ddd.domain;

/**
 * Marker interface for extension point interfaces.
 * <p>
 * Any interface that extends {@code ExtensionPointI} declares a behaviour contract
 * whose implementation can vary by {@link BizScenario} (business identity + use case + scenario).
 * <p>
 * Concrete implementations are annotated with {@link Extension} and registered
 * in the {@link ExtensionRepository} at startup.
 */
public interface ExtensionPointI {
}
