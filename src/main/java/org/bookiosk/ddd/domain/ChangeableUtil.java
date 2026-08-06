package org.bookiosk.ddd.domain;

import org.bookiosk.ddd.model.Field;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Change detection over an object graph of {@link Field}-wrapped properties.
 *
 * <p>Scans all {@code Field} properties reachable from the target — including
 * nested {@link Changeable} entities, collections, maps, and arrays — and reports
 * the ones whose {@link Field#isChanged()} is {@code true}.
 *
 * <p>Structural collection changes (add/remove) are NOT tracked here: per the
 * framework contract the Repository diffs collection properties against the
 * persisted state and persists the diff.
 */
public final class ChangeableUtil {

    private ChangeableUtil() {
    }

    /** Whether any reachable {@code Field} property is changed. */
    public static boolean isChanged(Object target) {
        return !collectChanged(target).isEmpty();
    }

    /** The changed {@code Field} properties reachable from {@code target}. */
    public static List<Field<?>> collectChanged(Object target) {
        List<Field<?>> changed = new ArrayList<>();
        scan(target, changed, new IdentityHashMap<>());
        return changed;
    }

    private static void scan(Object target, List<Field<?>> changed, IdentityHashMap<Object, Boolean> visited) {
        if (target == null) {
            return;
        }
        if (visited.put(target, Boolean.TRUE) != null) {
            return;
        }
        if (target instanceof Field) {
            Field<?> field = (Field<?>) target;
            if (field.isChanged()) {
                changed.add(field);
            }
            return;
        }
        if (target instanceof Collection) {
            for (Object element : (Collection<?>) target) {
                scan(element, changed, visited);
            }
            return;
        }
        if (target instanceof Map) {
            for (Object value : ((Map<?, ?>) target).values()) {
                scan(value, changed, visited);
            }
            return;
        }
        Class<?> type = target.getClass();
        if (type.isArray()) {
            for (int i = 0, len = Array.getLength(target); i < len; i++) {
                scan(Array.get(target, i), changed, visited);
            }
            return;
        }
        if (isJdkType(type)) {
            return;
        }
        while (type != null && type != Object.class) {
            for (java.lang.reflect.Field field : type.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                try {
                    scan(field.get(target), changed, visited);
                } catch (IllegalAccessException e) {
                    // inaccessible field — skip
                }
            }
            type = type.getSuperclass();
        }
    }

    private static boolean isJdkType(Class<?> type) {
        String name = type.getName();
        return name.startsWith("java.") || name.startsWith("javax.")
                || name.startsWith("jdk.") || name.startsWith("sun.");
    }
}
