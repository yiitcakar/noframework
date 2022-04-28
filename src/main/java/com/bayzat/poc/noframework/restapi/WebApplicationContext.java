package com.bayzat.poc.noframework.restapi;

import com.bayzat.poc.noframework.injection.SimpleApplicationContext;
import com.bayzat.poc.noframework.injection.common.Bean;
import com.bayzat.poc.noframework.reflections.PackageManager;
import com.bayzat.poc.noframework.restapi.common.ControllerBean;
import com.bayzat.poc.noframework.restapi.server.RestApiHandler;
import com.bayzat.poc.noframework.restapi.server.RestApiServer;
import com.bayzat.poc.noframework.restapi.server.RestApiServerConfig;

import java.util.Map;
import java.util.stream.Collectors;

public class WebApplicationContext extends SimpleApplicationContext {

    private Map<String, Class<?>> controllerClassMap;

    public WebApplicationContext(String sourcePackage) {
        super(sourcePackage);
        RestApiHandler bean = getBean(RestApiHandler.class);
        bean.initControllerMap(controllerClassMap);
        bean.setApplicationContext(this);
    }

    @Override
    protected Map<String, Class<?>> createBeanClassMap(String sourcePackage) {
        Map<String, Class<?>> beanClassMap = super.createBeanClassMap(sourcePackage);
        Map<String, Class<?>> serverClassMap = super.createBeanClassMap("com.bayzat.poc.noframework.restapi.server");

        controllerClassMap =  PackageManager.instance()
                .getAllClassesWithAnnotation(ControllerBean.class, sourcePackage)
                .stream()
                .collect(Collectors.toMap(
                        beanClass -> beanClass.getName(),
                        beanClass->beanClass));

        beanClassMap.putAll(controllerClassMap);
        beanClassMap.putAll(serverClassMap);

        return beanClassMap;
    }

    @Override
    protected void createBeans(Map<String, Class<?>> beanClassMap) {
        super.createBeans(beanClassMap);
    }

    public void start(){
        getBean(RestApiServer.class).start();
    }
}
