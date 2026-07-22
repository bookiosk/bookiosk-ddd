package org.bookiosk.ddd.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Immutable set wrapper for entity collection properties.
 * Every add/remove returns a new FieldSet — original is never mutated.
 *
 * @param <T> element type
 */
public final class FieldSet<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Set<T> values;

    private FieldSet(Set<T> values) {
        this.values = Collections.unmodifiableSet(new HashSet<>(values));
    }

    public static <T> FieldSet<T> of(Set<T> values) {
        return values == null || values.isEmpty() ? empty() : new FieldSet<>(values);
    }

    public static <T> FieldSet<T> empty() { return new FieldSet<>(Collections.emptySet()); }

    public Set<T> get() { return values; }

    public FieldSet<T> add(T element) {
        Set<T> newValues = new HashSet<>(this.values);
        newValues.add(element);
        return new FieldSet<>(newValues);
    }

    public FieldSet<T> remove(T element) {
        Set<T> newValues = new HashSet<>(this.values);
        newValues.remove(element);
        return new FieldSet<>(newValues);
    }

    public FieldSet<T> filter(Predicate<? super T> predicate) {
        return new FieldSet<>(this.values.stream().filter(predicate).collect(Collectors.toSet()));
    }

    public boolean contains(T element) { return values.contains(element); }
    public int size() { return values.size(); }
    public boolean isEmpty() { return values.isEmpty(); }
    public Stream<T> stream() { return values.stream(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldSet<?> fieldSet = (FieldSet<?>) o;
        return Objects.equals(values, fieldSet.values);
    }

    @Override
    public int hashCode() { return Objects.hash(values); }

    @Override
    public String toString() { return "FieldSet" + values; }
}
