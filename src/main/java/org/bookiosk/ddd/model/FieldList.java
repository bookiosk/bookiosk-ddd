package org.bookiosk.ddd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Immutable list wrapper for entity collection properties.
 * Every add/remove returns a new FieldList — original is never mutated.
 *
 * @param <T> element type
 */
public final class FieldList<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<T> values;

    private FieldList(List<T> values) {
        this.values = Collections.unmodifiableList(new ArrayList<>(values));
    }

    public static <T> FieldList<T> of(List<T> values) {
        return values == null || values.isEmpty() ? empty() : new FieldList<>(values);
    }

    public static <T> FieldList<T> empty() { return new FieldList<>(Collections.emptyList()); }

    public List<T> get() { return values; }

    public FieldList<T> add(T element) {
        List<T> newValues = new ArrayList<>(this.values);
        newValues.add(element);
        return new FieldList<>(newValues);
    }

    public FieldList<T> add(int index, T element) {
        List<T> newValues = new ArrayList<>(this.values);
        newValues.add(index, element);
        return new FieldList<>(newValues);
    }

    public FieldList<T> remove(T element) {
        List<T> newValues = new ArrayList<>(this.values);
        newValues.remove(element);
        return new FieldList<>(newValues);
    }

    public FieldList<T> remove(int index) {
        List<T> newValues = new ArrayList<>(this.values);
        newValues.remove(index);
        return new FieldList<>(newValues);
    }

    public FieldList<T> filter(Predicate<? super T> predicate) {
        return new FieldList<>(this.values.stream().filter(predicate).collect(Collectors.toList()));
    }

    public FieldList<T> replace(int index, T element) {
        List<T> newValues = new ArrayList<>(this.values);
        newValues.set(index, element);
        return new FieldList<>(newValues);
    }

    public T get(int index) { return values.get(index); }
    public int size() { return values.size(); }
    public boolean isEmpty() { return values.isEmpty(); }
    public Stream<T> stream() { return values.stream(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldList<?> fieldList = (FieldList<?>) o;
        return Objects.equals(values, fieldList.values);
    }

    @Override
    public int hashCode() { return Objects.hash(values); }

    @Override
    public String toString() { return "FieldList" + values; }
}
