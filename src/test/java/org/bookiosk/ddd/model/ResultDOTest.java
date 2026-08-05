package org.bookiosk.ddd.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultDOTest {

    @Test
    public void buildSuccessResult_shouldReturnSuccess() {
        ResultDO<String> result = ResultDO.buildSuccessResult("data");
        assertTrue(result.isSuccess());
        assertEquals("data", result.getData());
    }

    @Test
    public void buildSuccessResult_void_shouldReturnSuccessWithNullData() {
        ResultDO<Void> result = ResultDO.buildSuccessResult();
        assertTrue(result.isSuccess());
        assertNull(result.getData());
    }

    @Test
    public void buildFailResult_shouldReturnFailure() {
        ResultDO<String> result = ResultDO.buildFailResult("ERR_001", "error message");
        assertFalse(result.isSuccess());
        assertTrue(result.isFail());
        assertEquals("ERR_001", result.getCode());
        assertEquals("error message", result.getMsg());
    }

    @Test
    public void buildFailResult_fromOtherResult_shouldReturnFailure() {
        ResultDO<String> result = ResultDO.buildFailResult("Operation failed");
        assertFalse(result.isSuccess());
        assertEquals("Operation failed", result.getMsg());
    }

    @Test
    public void buildFailResult_withData_shouldCarryData() {
        ResultDO<String> result = ResultDO.buildFailResult("DUPLICATE", "duplicate order", "existing-order-no");
        assertFalse(result.isSuccess());
        assertEquals("DUPLICATE", result.getCode());
        assertEquals("duplicate order", result.getMsg());
        assertEquals("existing-order-no", result.getData());
    }

    @Test
    public void buildFailResult_withNullData_shouldReturnNullData() {
        ResultDO<String> result = ResultDO.buildFailResult("ERR", "msg", null);
        assertFalse(result.isSuccess());
        assertNull(result.getData());
    }
}
