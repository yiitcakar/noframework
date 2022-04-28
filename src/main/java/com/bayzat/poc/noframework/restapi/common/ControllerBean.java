package com.bayzat.poc.noframework.restapi.common;

import com.bayzat.poc.noframework.injection.common.Bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Bean
public @interface ControllerBean {
    String path() default "/";
}
