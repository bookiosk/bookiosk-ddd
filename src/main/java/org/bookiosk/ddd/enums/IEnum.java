package org.bookiosk.ddd.enums;

/**
 * @author bookiosk
 */
public interface IEnum<T> {

    T getCode();

    String getDesc();

    static <T,E extends IEnum<T>> E of(Class<E> clazz, T code) {
        assert clazz.isEnum();
        if (code == null) {
            return null;
        }
        for (E constant : clazz.getEnumConstants()) {
            if (constant.getCode().equals(code)) {
                return constant;
            }
        }
        throw new RuntimeException("invalid code: " + code);
    }
}
