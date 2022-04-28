package com.bayzat.poc.noframework.restapi.server;

import com.bayzat.poc.noframework.injection.common.Bean;
import com.bayzat.poc.noframework.injection.common.Init;
import com.bayzat.poc.noframework.injection.common.Inject;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

@Bean
public class RestApiServer
{
    @Inject
    private RestApiHandler handler;

    @Inject
    private RestApiServerConfig config;

    private HttpServer server;

    @Init
    public void init() throws Exception{
        server = HttpServer.create(new InetSocketAddress(config.getPort()), 0);
        server.createContext(config.getPath(), handler);
        server.setExecutor(null);
    }

    public void start(){
        server.start();
        System.out.printf("Server Started localhost:" + this.config.getPort() + this.config.getPath());
    }



}
