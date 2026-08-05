package org.bookiosk.ddd.enums;

/**
 * Standardized enum contract for all business enumerations.
 * Every business enum MUST implement this interface to ensure
 * consistent code/desc access and type-safe lookup.
 *
 * <p>Usage:
 * <pre>{@code
 * public enum OrderStatusEnum implements IEnum<Integer> {
 *     PENDING(0, "待处理"),
 *     CONFIRMED(1, "已确认");
 *
 *     private final Integer code;
 *     private final String desc;
 *     // ... constructor, getCode(), getDesc()
 * }
 *
 * OrderStatusEnum status = IEnum.of(OrderStatusEnum.class, 1);
 * }</pre>
 *
 * @param <T> the code type (Integer, String, etc.)
 * @author bookiosk
 */
public interface IEnum<T> {

    T getCode();

    String getDesc();

    static <T,E extends IEnum<T>> E of(Class<E> clazz, T code) {
        if (!clazz.isEnum()) {
            throw new IllegalArgumentException("not an enum type: " + clazz.getName());
        }
        if (code == null) {
            return null;
        }
        for (E constant : clazz.getEnumConstants()) {
            if (constant.getCode().equals(code)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("invalid code: " + code);
    }
}
