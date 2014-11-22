package com.iokays.test.data;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.iokays.test.entity.Entity;

public class TestData<E> {
    
    
    public static String _string() {
        return strings[random.nextInt(strings.length)];
    }

    public static Integer _integer() {
        return integers[random.nextInt(integers.length)];
    }

    public static Long _long() {
        return longs[random.nextInt(longs.length)];
    }
    
    public static <T> List<T> _list(Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Set<T> set = Sets.newHashSet();
        final int length = random.nextInt(strings.length);
        for (int i = 0; i < length; ++i) {
            set.add(Entity.create(clazz));
        }
        return Lists.newArrayList(set.iterator());
    }

    public static Integer[] _random(int size) {
        if (0 == size) {
            return new Integer[0];
        } else {
            int methodCount = random.nextInt(size) + 1;

            Set<Integer> set = Sets.newHashSet();
            for (int i = 0; i < methodCount; ++i) {
                int key = random.nextInt(size);
                while (set.contains(key)) {
                    key = random.nextInt(size);
                }
                set.add(key);
            }
            return set.toArray(new Integer[methodCount]);
        }
    }

    public static <T> T _enum(Class<T> clazz) {
        T[] _enums = clazz.getEnumConstants();
        return _enums[random.nextInt(_enums.length)];
    }

    private static final Random random = new Random(new Date().getTime());
    
    private static final String[] strings = new String[]{ "0", "-1", "1", "啦", "a", "A", "小", "林", "Ab",
            " TEST", "TEST ", "  TEST  ", "懂懂", "风筝误", "(*^__^*) 嘻嘻……" };

    private static final Integer[] integers = new Integer[]{ Integer.MAX_VALUE, Integer.MIN_VALUE, -10001, -10000,
            -9999, -1001, -1000, -999, -101, -100, -99 - 11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5,
            6, 7, 8, 9, 10, 11 };

    private static final Long[] longs = new Long[]{ Long.MAX_VALUE, Long.MIN_VALUE, -10001L, -10000L, -9999L, -1001L,
            -1000L, -999L, -101L, -100L, -99L - 11L, -10L, -9L, -8L, -7L, -6L, -5L, -4L, -3L, -2L, -1L, 0L, 1L, 2L, 3L,
            4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L };
}
