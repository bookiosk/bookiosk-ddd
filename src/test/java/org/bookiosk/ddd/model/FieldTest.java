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
}
