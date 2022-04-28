package com.bayzat.poc.noframework.app.service;

import com.bayzat.poc.noframework.app.repo.Repo1;
import com.bayzat.poc.noframework.injection.common.Bean;
import com.bayzat.poc.noframework.injection.common.Init;
import com.bayzat.poc.noframework.injection.common.Inject;

@Bean
public class Service1 {

    @Inject
    private Repo1 repo1;

    @Inject
    private Service2 service2;

    @Init
    public void init(){
        System.out.println("init Service1");
    }

    public void service1Test(){
        System.out.println("service1Test");
        repo1.repo1Test();
        service2.service2Test();
    }


}
