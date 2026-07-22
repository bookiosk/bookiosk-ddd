package org.bookiosk.ddd.enums;

/**
 * @author bookiosk
 */
public final class IEnumConverter {
    public static <T> T getCode(IEnum<T> iEnum) {
        return iEnum == null ? null : iEnum.getCode();
    }
}
