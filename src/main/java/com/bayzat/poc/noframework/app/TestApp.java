package com.bayzat.poc.noframework.app;

import com.bayzat.poc.noframework.app.service.Service1;
import com.bayzat.poc.noframework.injection.SimpleApplicationContext;
import com.bayzat.poc.noframework.injection.common.ApplicationContext;

public class TestApp {
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new SimpleApplicationContext("com.bayzat.poc.noframework.app");
        applicationContext.getBeanNames().forEach(System.out::println);
        applicationContext.getBean(Service1.class).service1Test();
    }
}
