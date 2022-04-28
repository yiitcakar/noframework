package com.bayzat.poc.noframework.app;

import com.bayzat.poc.noframework.restapi.WebApplicationContext;
import com.bayzat.poc.noframework.restapi.server.RestApiHandler;
import com.bayzat.poc.noframework.restapi.server.RestApiServer;

public class TestWebApp {
    public static void main(String[] args) throws Exception {
        WebApplicationContext webApplicationContext = new WebApplicationContext("com.bayzat.poc.noframework.app");
        webApplicationContext.start();
    }
}
