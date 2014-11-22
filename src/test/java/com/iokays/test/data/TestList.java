package com.iokays.test.data;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.google.common.collect.Lists;
import com.iokays.test.entity.Entity;

public class TestList {
    public void setTest(List<String> vals) {
        System.out.println(vals.size());
    }
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        new TestList().setTest(Lists.newArrayList(Entity.create(String.class)));;
    }
}
