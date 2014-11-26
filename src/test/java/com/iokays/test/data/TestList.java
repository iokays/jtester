package com.iokays.test.data;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.iokays.test.entity.Entity;

public class TestList {
    public void setTest(List<String> vals) {
        for (String string : vals) {
            System.out.println(string);
        }
    }
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Entity.create(TestList.class);
    }
}
