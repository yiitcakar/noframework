package com.bayzat.poc.noframework.app.controller;

import com.bayzat.poc.noframework.app.service.Service1;
import com.bayzat.poc.noframework.injection.common.Init;
import com.bayzat.poc.noframework.injection.common.Inject;
import com.bayzat.poc.noframework.restapi.common.ControllerBean;
import com.bayzat.poc.noframework.restapi.common.Get;

@ControllerBean(path = "/controller1")
public class Controller1 {

    @Inject
    private Service1 service1;

    @Init
    public void init(){
        System.out.println("init Controller1");
    }

    @Get
    public String controller1Test(){
        System.out.println("controller1Test");
        service1.service1Test();
        return "it works";
    }

}
