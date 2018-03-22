package com.tigerzhang.kafka;

import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by tigerzhang on 2018/3/15.
 */
@EnableLoadTimeWeaving
public class Spring {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("app.xml");
        ctx.getBean(ClientTest.class);
    }
}
