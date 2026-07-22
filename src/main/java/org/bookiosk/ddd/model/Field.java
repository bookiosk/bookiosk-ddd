package org.bookiosk.ddd.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Immutable single-value wrapper for entity properties.
 * Every mutation creates a new Field instance — never mutates in-place.
 *
 * @param <T> the wrapped value type
 */
public final class Field<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final T value;

    private Field(T value) {
        this.value = value;
    }

    public static <T> Field<T> of(T value) {
        return value == null ? empty() : new Field<>(value);
    }

    public static <T> Field<T> empty() {
        return new Field<>(null);
    }

    public T get() { return value; }

    public boolean isPresent() { return value != null; }

    public <R> Field<R> map(java.util.function.Function<? super T, ? extends R> mapper) {
        return isPresent() ? Field.of(mapper.apply(value)) : Field.empty();
    }

    public T orElse(T other) {
        return value != null ? value : other;
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
    public String toString() { return "Field{" + value + "}"; }
}
