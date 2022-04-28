package com.bayzat.poc.noframework.app.service;

import com.bayzat.poc.noframework.app.repo.Repo2;
import com.bayzat.poc.noframework.injection.common.Bean;
import com.bayzat.poc.noframework.injection.common.Init;
import com.bayzat.poc.noframework.injection.common.Inject;

@Bean
public class Service2 {

    @Inject
    private Repo2 repo2;

    @Init
    public void init(){
        System.out.println("init Service2");
    }

    public void service2Test(){
        System.out.println("service2Test");
        repo2.repo2Test();
    }
}
