package com.bayzat.poc.noframework.restapi.server;


import com.bayzat.poc.noframework.injection.common.Bean;
import com.bayzat.poc.noframework.injection.common.Prop;

@Bean
public class RestApiServerConfig {

    @Prop(key = "port")
    private Integer port;

    @Prop(key = "path")
    private String path;


    public Integer getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }
}
