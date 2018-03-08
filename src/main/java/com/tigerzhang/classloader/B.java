package com.tigerzhang.classloader;

/**
 * Created by tigerzhang on 2018/3/8.
 */
public class B {
    public B() {
        A a = new A();
        a.cccc();
        System.out.println(B.class.getClassLoader());
        System.out.println("hello");
    }
}
