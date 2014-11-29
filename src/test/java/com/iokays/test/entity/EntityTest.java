package com.iokays.test.entity;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.iokays.test.data.TestList;

public class EntityTest {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Test
    public void test() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Entity.create(EntityTest.class);
    }
}
