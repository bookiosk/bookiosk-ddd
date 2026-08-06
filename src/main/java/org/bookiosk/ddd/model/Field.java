package org.bookiosk.ddd.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

/**
 * Mutable single-value holder for entity properties with change tracking.
 *
 * <p>{@link #set} mutates in place and marks the field as changed. Change is
 * detected by comparing the new value against the current one via
 * {@link #equalsValue}: setting an equal value is a no-op and does not mark the
 * field dirty. The {@code changed} flag defaults to {@code false} and is only
 * set when the value actually changes.
 *
 * <p>The flag is intentionally never reset in place. Per the framework contract
 * a saved aggregate/entity must not be reused — {@code Repository.save()} then
 * re-query ({@code findById}) for further work, which reconstructs the object
 * graph with fresh flags.
 *
 * @param <T> the wrapped value type
 */
public final class Field<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T value;
    private boolean changed = false;

    private Field(T value) {
        this.value = value;
    }

    public static <T> Field<T> of(T value) {
        return new Field<>(value);
    }

    public static <T> Field<T> empty() {
        return new Field<>(null);
    }

    public T get() { return value; }

    public boolean isPresent() { return value != null; }

    /** Null-safe comparison of the current value against {@code other}. */
    public boolean equalsValue(T other) {
        return Objects.equals(value, other);
    }

    /**
     * Sets a new value. Marks this field as changed unless the new value equals
     * the current one.
     */
    public void set(T newValue) {
        if (equalsValue(newValue)) {
            return;
        }
        this.value = newValue;
        this.changed = true;
    }

    /** Whether the value differs from the one at construction (or last load). */
    public boolean isChanged() { return changed; }

    public T orElse(T other) {
        return value != null ? value : other;
    }

    public <R> Field<R> map(Function<? super T, ? extends R> mapper) {
        return isPresent() ? Field.of(mapper.apply(value)) : Field.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field<?> field = (Field<?>) o;
        return Objects.equals(value, field.value);
    }

    @Override
    public int hashCode() { return Objects.hash(value); }

    @Override
    public String toString() { return "Field{" + value + ", changed=" + changed + "}"; }
}
