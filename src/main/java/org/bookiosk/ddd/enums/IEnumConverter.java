package org.bookiosk.ddd.enums;

/**
 * Null-safe utility for extracting enum codes.
 * Returns {@code null} when the enum reference is null,
 * avoiding NPE in mapping/conversion scenarios.
 *
 * @author bookiosk
 */
public final class IEnumConverter {
    public static <T> T getCode(IEnum<T> iEnum) {
        return iEnum == null ? null : iEnum.getCode();
    }
}
