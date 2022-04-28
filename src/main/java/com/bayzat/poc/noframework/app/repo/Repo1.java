package com.bayzat.poc.noframework.app.repo;

import com.bayzat.poc.noframework.injection.common.Bean;
import com.bayzat.poc.noframework.injection.common.Init;

@Bean
public class Repo1 {

    @Init
    public void init(){
        System.out.println("init Repo1");
    }

    public void repo1Test(){
        System.out.println("repo1Test");
    }
}
