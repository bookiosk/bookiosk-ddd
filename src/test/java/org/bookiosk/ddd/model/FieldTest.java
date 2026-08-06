package org.bookiosk.ddd.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {

    @Test
    public void of_shouldCreateFieldWithValue() {
        Field<String> field = Field.of("hello");
        assertTrue(field.isPresent());
        assertEquals("hello", field.get());
    }

    @Test
    public void of_null_shouldCreateEmptyField() {
        Field<String> field = Field.of(null);
        assertFalse(field.isPresent());
        assertNull(field.get());
    }

    @Test
    public void empty_shouldCreateEmptyField() {
        Field<String> field = Field.empty();
        assertFalse(field.isPresent());
    }

    @Test
    public void map_shouldTransformValue() {
        Field<String> field = Field.of("hello");
        Field<Integer> result = field.map(String::length);
        assertEquals(Integer.valueOf(5), result.get());
    }

    @Test
    public void map_empty_shouldReturnEmpty() {
        Field<String> field = Field.empty();
        Field<Integer> result = field.map(String::length);
        assertFalse(result.isPresent());
    }

    @Test
    public void orElse_shouldReturnValueWhenPresent() {
        Field<String> field = Field.of("hello");
        assertEquals("hello", field.orElse("default"));
    }

    @Test
    public void orElse_shouldReturnDefaultWhenEmpty() {
        Field<String> field = Field.empty();
        assertEquals("default", field.orElse("default"));
    }

    @Test
    public void equals_shouldBeTrueForSameValues() {
        Field<String> f1 = Field.of("hello");
        Field<String> f2 = Field.of("hello");
        assertEquals(f1, f2);
    }

    @Test
    public void equals_shouldBeFalseForDifferentValues() {
        Field<String> f1 = Field.of("hello");
        Field<String> f2 = Field.of("world");
        assertNotEquals(f1, f2);
    }

    @Test
    public void toString_shouldContainValue() {
        Field<String> field = Field.of("hello");
        assertTrue(field.toString().contains("hello"));
    }

    @Test
    public void set_differentValue_shouldMarkChanged() {
        Field<String> field = Field.of("hello");
        field.set("world");
        assertTrue(field.isChanged());
        assertEquals("world", field.get());
    }

    @Test
    public void set_sameValue_shouldNotMarkChanged() {
        Field<String> field = Field.of("hello");
        field.set("hello");
        assertFalse(field.isChanged());
    }

    @Test
    public void set_nullFromValue_shouldMarkChanged() {
        Field<String> field = Field.of("hello");
        field.set(null);
        assertTrue(field.isChanged());
        assertFalse(field.isPresent());
    }

    @Test
    public void set_nullWhenAlreadyNull_shouldNotMarkChanged() {
        Field<String> field = Field.empty();
        field.set(null);
        assertFalse(field.isChanged());
    }

    @Test
    public void freshField_shouldNotBeChanged() {
        Field<String> field = Field.of("hello");
        assertFalse(field.isChanged());
    }

    @Test
    public void equalsValue_shouldBeNullSafe() {
        Field<String> field = Field.of("hello");
        assertTrue(field.equalsValue("hello"));
        assertFalse(field.equalsValue(null));
        Field<String> empty = Field.empty();
        assertTrue(empty.equalsValue(null));
    }
}
