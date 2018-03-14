package com.tigerzhang.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * Created by tigerzhang on 2018/3/8.
 */
public class MyClassloader extends URLClassLoader {

    public MyClassloader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.equals("com.tigerzhang.classloader.A") || name.equals("com.tigerzhang.classloader.B")
                || name.equals("com.tigerzhang.classloader.C")) {
            System.out.println("loaded class:" + name);
            String path = name.replaceAll("\\.", "/");
            InputStream stream = getResourceAsStream(path + ".class");
            Class<?> clazz = findLoadedClass(name);
            if (clazz != null) {
                return clazz;
            }

            byte[] buffer = new byte[4096];
            try {
                int len = stream.read(buffer);
                clazz = defineClass(name, buffer, 0, len);
                resolveClass(clazz);
                return clazz;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.loadClass(name);
    }

    public static void main(String[] args) throws Exception {

        MyClassloader classloader1 = new MyClassloader(new URL[0], MyClassloader.class.getClassLoader());
        MyClassloader classloader2 = new MyClassloader(new URL[0], MyClassloader.class.getClassLoader());
        Class<A> aClass = (Class<A>) classloader1.loadClass("com.tigerzhang.classloader.A");
        Class<B> xClass = (Class<B>) classloader1.loadClass("com.tigerzhang.classloader.B");
        Class<A> bClass = (Class<A>) classloader2.loadClass("com.tigerzhang.classloader.A");

        System.out.println(aClass.newInstance());
        System.out.println(bClass.newInstance());
        System.out.println(aClass.newInstance() instanceof C);
        (aClass.newInstance()).cccc();
    }
}
