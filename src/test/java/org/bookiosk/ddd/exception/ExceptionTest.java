package org.bookiosk.ddd.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExceptionTest {

    @Test
    public void bizException_shouldHaveCodeAndMsg() {
        BizException ex = new BizException("BIZ_001", "business error");
        assertEquals("BIZ_001", ex.getCode());
        assertEquals("business error", ex.getMsg());
    }

    @Test
    public void bizException_defaultConstructor_shouldUseDefaultCode() {
        BizException ex = new BizException("business error");
        assertEquals("BIZ_ERROR", ex.getCode());
        assertEquals("business error", ex.getMsg());
    }

    @Test
    public void aggregateException_shouldExtendBizException() {
        AggregateException ex = new AggregateException("validation failed");
        assertTrue(ex instanceof BizException);
        assertTrue(ex instanceof RuntimeException);
        assertEquals("AGGREGATE_ERROR", ex.getCode());
    }

    @Test
    public void aggregateException_customCode_shouldUseCustomCode() {
        AggregateException ex = new AggregateException("ORDER_INVALID", "Invalid order");
        assertEquals("ORDER_INVALID", ex.getCode());
    }

    @Test
    public void repositoryException_shouldExtendBizException() {
        RepositoryException ex = new RepositoryException("save failed");
        assertTrue(ex instanceof BizException);
        assertEquals("REPOSITORY_ERROR", ex.getCode());
    }

    @Test
    public void repositoryException_customCode_shouldUseCustomCode() {
        RepositoryException ex = new RepositoryException("DB_FAIL", "Database failure");
        assertEquals("DB_FAIL", ex.getCode());
    }
}
