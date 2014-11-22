package com.iokays.test.entity;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.iokays.test.data.TestList;

public class EntityTest {

    @Test
    public void test() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Entity.create(TestList.class);
    }
}
