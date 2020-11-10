package pri.jarod.java.core.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import pri.jarod.java.core.exception.DapException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * asset 断言，直接报出异常
 *
 * @author Jarod.Kong
 * @date 2020/8/11 10:25
 */
public class BmAssetUtils {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new DapException(message);
        }
    }

    public static void isTrue(boolean expression, Supplier<RuntimeException> supplier) {
        if (!expression) {
            throw supplier.get();
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "结果必须为true");
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new DapException(message);
        }
    }

    public static void isFalse(boolean expression, Supplier<RuntimeException> supplier) {
        if (expression) {
            throw supplier.get();
        }
    }

    public static void isFalse(boolean expression) {
        isFalse(expression, "结果必须为false");
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new DapException(message);
        }
    }

    public static void isNulls(Object[] object, String message) {
        if (object != null && Arrays.stream(object).anyMatch(Objects::nonNull)) {
            throw new DapException(message);
        }
    }

    public static void isNull(Object object, Supplier<RuntimeException> supplier) {
        if (object != null) {
            throw supplier.get();
        }
    }

    public static void isNulls(Object[] object, Supplier<RuntimeException> supplier) {
        if (object != null && Arrays.stream(object).anyMatch(Objects::nonNull)) {
            throw supplier.get();
        }
    }

    public static void isNull(Object object) {
        isNull(object, "对象必须为null");
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new DapException(message);
        }
    }

    public static void notNulls(Object[] object, String message) {
        if (object == null || Arrays.stream(object).anyMatch(Objects::isNull)) {
            throw new DapException(message);
        }
    }

    public static void notNull(Object object, Supplier<RuntimeException> supplier) {
        if (object == null) {
            throw supplier.get();
        }
    }


    public static void notNull(Object object) {
        notNull(object, "对象必须不为null");
    }

    public static void hasText(String text, String message) {
        if (StringUtils.length(text) == 0) {
            throw new DapException(message);
        }
    }

    public static void hasText(String text) {
        hasText(text, "字符串不可空");
    }

    public static void doesNotContain(String textToSearch, String substring, String message) {
        if ((StringUtils.length(textToSearch) > 0) && (StringUtils.length(substring) > 0) && (textToSearch.contains(substring))) {
            throw new DapException(message);
        }
    }

    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring, "带搜索字符串中不含有子字符串[" + substring + "]");
    }

    public static void notEmpty(Object[] array, String message) {
        if ((array == null) || (array.length == 0)) {
            throw new DapException(message);
        }
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, "对象数组必须有元素");
    }

    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new DapException(message);
                }
            }
        }
    }

    public static void noNullElements(Object[] array) {
        noNullElements(array, "对象数组必须没有null元素");
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new DapException(message);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "集合对象必须含有元素");
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if ((map == null) || (map.isEmpty())) {
            throw new DapException(message);
        }
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "map对象必须含有元素");
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "检查类型不可为null");
        if (!type.isInstance(obj)) {
            throw new DapException(message);
        }
    }

    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "对象必须是指定类的实例");
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "检查类型不可为null");
        if ((subType == null) || (!superType.isAssignableFrom(subType))) {
            throw new DapException(message);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "SuperType必须是SubType的父类");
    }
}
