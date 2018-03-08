package com.tigerzhang.classloader;

/**
 * Created by tigerzhang on 2018/3/8.
 */
public class A implements C {
    public A() {
    }

    @Override
    public void cccc() {
        System.out.println("A call func cccc()");
    }
}
