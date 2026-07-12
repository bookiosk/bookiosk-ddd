package org.bookiosk.ddd.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a concrete implementation of an {@link ExtensionPointI} sub-interface.
 * <p>
 * The annotation's three attributes -- {@link #bizId()}, {@link #useCase()},
 * {@link #scenario()} -- form a 3D {@link BizScenario} coordinate that determines
 * which business context this extension serves.
 * <p>
 * Annotated classes are discovered at startup by a classpath scan and registered
 * in the {@link ExtensionRepository}. At runtime the {@link ExtensionExecutor}
 * dispatches to the correct implementation based on the current {@link BizScenario}.
 * <p>
 * {@code @Inherited} allows subclasses to inherit the annotation so that a base
 * extension class can pass its scenario binding to specialized subclasses.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Extension {

    /** Business identity; defaults to {@code "default"}. */
    String bizId() default "default";

    /** Use case; defaults to {@code "default"}. */
    String useCase() default "default";

    /** Scenario; defaults to {@code "default"}. */
    String scenario() default "default";
}
