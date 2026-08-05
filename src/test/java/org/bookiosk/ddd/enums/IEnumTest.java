package org.bookiosk.ddd.enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class IEnumTest {

    enum TestStatus implements IEnum<Integer> {
        PENDING(1, "pending"),
        CONFIRMED(2, "confirmed");

        private final Integer code;
        private final String desc;

        TestStatus(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public Integer getCode() { return code; }

        @Override
        public String getDesc() { return desc; }
    }

    @Test
    public void of_shouldFindEnumByCode() {
        TestStatus status = IEnum.of(TestStatus.class, 1);
        assertEquals(TestStatus.PENDING, status);
    }

    @Test
    public void of_nullCode_shouldReturnNull() {
        TestStatus status = IEnum.of(TestStatus.class, null);
        assertNull(status);
    }

    @Test(expected = RuntimeException.class)
    public void of_invalidCode_shouldThrowException() {
        IEnum.of(TestStatus.class, 999);
    }

    @Test
    public void converter_shouldExtractCode() {
        Integer code = IEnumConverter.getCode(TestStatus.CONFIRMED);
        assertEquals(Integer.valueOf(2), code);
    }

    @Test
    public void converter_null_shouldReturnNull() {
        Integer code = IEnumConverter.getCode(null);
        assertNull(code);
    }

    @Test
    public void getCode_shouldReturnCode() {
        assertEquals(Integer.valueOf(1), TestStatus.PENDING.getCode());
    }

    @Test
    public void getDesc_shouldReturnDescription() {
        assertEquals("pending", TestStatus.PENDING.getDesc());
    }
}
