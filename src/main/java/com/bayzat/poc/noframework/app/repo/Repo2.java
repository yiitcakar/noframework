package com.bayzat.poc.noframework.app.repo;

import com.bayzat.poc.noframework.injection.common.Bean;
import com.bayzat.poc.noframework.injection.common.Init;

@Bean
public class Repo2 {

    @Init
    public void init(){
        System.out.println("init Repo2");
    }

    public void repo2Test(){
        System.out.println("repo2Test");
    }
}
