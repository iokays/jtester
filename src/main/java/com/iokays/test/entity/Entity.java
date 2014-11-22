package com.iokays.test.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.google.common.collect.Lists;
import com.iokays.test.data.TestData;

public class Entity {
    public static <T> T create(Class<T> clazz) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        if (clazz == String.class) {
            return (T) TestData._string();
        } else if (clazz == Integer.class || clazz== int.class) {
            return (T) TestData._integer();
        } else if (clazz == Long.class || clazz== long.class) {
            return (T) TestData._long();
        } else if (clazz.isEnum()) {
            return TestData._enum(clazz);
        }
        else {
            T entity = clazz.newInstance();
            Method[] methods = clazz.getMethods();
            List<Method> setters = Lists.newArrayList();
            for (Method method : methods) {
                if (isSetter(method) && method.getParameterTypes().length == 1) {
                    setters.add(method);
                }
            }
            Integer[] methodMark = TestData._random(setters.size());
            for (Integer mark : methodMark) {
                Method method = setters.get(mark);
                
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (1 == parameterTypes.length) {
                    if (parameterTypes[0] == String.class) {
                        method.invoke(entity, create(String.class));
                    }

                    if (parameterTypes[0] == Integer.class || parameterTypes[0] == int.class) { // 对象为Integer
                        method.invoke(entity, create(Integer.class));
                    }

                    if (parameterTypes[0] == Long.class || parameterTypes[0] == long.class) { // 对象为Long
                        method.invoke(entity, create(Long.class));
                    }

                    if (parameterTypes[0].isEnum()) { // 对象为Enum
                        method.invoke(entity, create(parameterTypes[0]));
                    }

                    if (parameterTypes[0] == List.class) {// 对象为List
                        ParameterizedType type = (ParameterizedType) method.getGenericParameterTypes()[0];
                        Class entityClass= (Class) (type.getActualTypeArguments()[0]);
                        try {
                            method.invoke(entity, TestData._list(entityClass));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return entity;
        } 
    }
    
    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get"))
            return false;
        if (method.getParameterTypes().length != 0)
            return false;
        if (void.class.equals(method.getReturnType()))
            return false;
        return true;
    }

    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set"))
            return false;
        if (method.getParameterTypes().length != 1)
            return false;
        return true;
    }
 
}
