package org.bookiosk.ddd.domain;

import org.bookiosk.ddd.common.BizException;

import java.util.function.Function;

/**
 * Runtime dispatcher that locates and instantiates the correct
 * {@link ExtensionPointI} implementation for a given {@link BizScenario}.
 *
 * <pre>{@code
 * ExtensionExecutor executor = new ExtensionExecutor(repository);
 *
 * // variant 1: get the extension and call methods yourself
 * PaymentValidatorExtPt ext = executor.execute(PaymentValidatorExtPt.class, bizScenario);
 * ext.validate(ctx);
 *
 * // variant 2: pass a function that receives the extension
 * String result = executor.execute(MyExtPt.class, bizScenario, MyExtPt::doSomething);
 * }</pre>
 */
public class ExtensionExecutor {

    private final ExtensionRepository repository;

    public ExtensionExecutor(ExtensionRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("repository must not be null");
        }
        this.repository = repository;
    }

    /**
     * Look up and instantiate the extension implementation.
     *
     * @param <T>           the extension-point interface type
     * @param extensionPtClz the extension-point interface class
     * @param bizScenario    the business scenario to match
     * @return a new instance of the matching extension implementation
     * @throws BizException if no matching extension is found or instantiation fails
     */
    public <T extends ExtensionPointI> T execute(Class<T> extensionPtClz, BizScenario bizScenario) {
        Class<?> implClz = repository.find(extensionPtClz, bizScenario);
        if (implClz == null) {
            throw new BizException("EXTENSION_NOT_FOUND",
                "No extension found for " + extensionPtClz.getName()
                    + " with bizScenario=" + bizScenario);
        }
        try {
            return extensionPtClz.cast(implClz.getDeclaredConstructor().newInstance());
        } catch (ReflectiveOperationException e) {
            throw new BizException("EXTENSION_INSTANTIATE_ERROR",
                "Failed to instantiate extension " + implClz.getName() + ": " + e.getMessage());
        }
    }

    /**
     * Look up, instantiate, and apply the given function to the extension.
     *
     * @param <T>           the extension-point interface type
     * @param <R>           the result type of the function
     * @param extensionPtClz the extension-point interface class
     * @param bizScenario    the business scenario to match
     * @param function       the function to apply to the extension instance
     * @return the result of applying {@code function}
     */
    public <T extends ExtensionPointI, R> R execute(
            Class<T> extensionPtClz,
            BizScenario bizScenario,
            Function<T, R> function) {
        T extension = execute(extensionPtClz, bizScenario);
        return function.apply(extension);
    }
}
