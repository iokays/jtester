package com.iokays.test.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.google.common.collect.Lists;
import com.iokays.test.data.TestData;

public class Entity {
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

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
            
            String className = clazz.getSimpleName().toLowerCase();
            String propertyName = method.getName().substring(3).toLowerCase();
            
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (1 == parameterTypes.length) {
                if (parameterTypes[0] == String.class) {
                    method.invoke(entity, TestData._string(className, propertyName));
                }

                if (parameterTypes[0] == Integer.class || parameterTypes[0] == int.class) { // 对象为Integer
                    method.invoke(entity, TestData._integer(className, propertyName));
                }

                if (parameterTypes[0] == Long.class || parameterTypes[0] == long.class) { // 对象为Long
                    method.invoke(entity, TestData._long(className, propertyName));
                }

                if (parameterTypes[0].isEnum()) { // 对象为Enum
                    method.invoke(entity, create(parameterTypes[0]));
                }

                if (parameterTypes[0] == List.class) {// 对象为List
                    ParameterizedType type = (ParameterizedType) method.getGenericParameterTypes()[0];
                    @SuppressWarnings("rawtypes")
                    Class entityClass= (Class) (type.getActualTypeArguments()[0]);
                    try {
                        method.invoke(entity, TestData._list(entityClass, className, propertyName));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return entity;
    
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
