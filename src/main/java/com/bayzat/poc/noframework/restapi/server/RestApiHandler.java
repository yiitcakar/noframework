package com.bayzat.poc.noframework.restapi.server;

import com.bayzat.poc.noframework.injection.common.Bean;
import com.bayzat.poc.noframework.injection.common.Prop;
import com.bayzat.poc.noframework.restapi.WebApplicationContext;
import com.bayzat.poc.noframework.restapi.common.ControllerBean;
import com.bayzat.poc.noframework.restapi.common.Get;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;

@Bean
public class RestApiHandler implements HttpHandler {

    private static final String ERROR_MESSAGE_TEMPLATE = "{\"message\" : \"#message#\"}";

    @Prop(key = "path")
    private String path;

    private Map<String,Class<?>> controllerClassMap;

    private WebApplicationContext webApplicationContext;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET")){
            Class<?> controllerClass = this.controllerClassMap.get(exchange.getRequestURI().getPath());
            Object bean = webApplicationContext.getBean(controllerClass);
            Method[] methods = controllerClass.getDeclaredMethods();
            for(Method method : methods){
                if(method.getAnnotation(Get.class) != null){
                    try {
                        String response = method.invoke(bean).toString();
                        sendMessage(exchange,response,200);
                    } catch (Exception e) {
                        sendMessage(exchange,"Error",500);
                    }
                }
            }
            sendMessage(exchange,"Not Found",404);
        }else{
            sendMessage(exchange,"Operation not supported operation " + exchange.getRequestMethod(),501);
        }
    }

    public void initControllerMap(Map<String,Class<?>> controllerBeanClassMap){
        this.controllerClassMap = controllerBeanClassMap.values().stream()
                .collect(Collectors.toMap(
                        controllerClass-> this.path + controllerClass.getAnnotation(ControllerBean.class).path(),
                        controllerClass-> controllerClass));
    }

    public void setApplicationContext(WebApplicationContext webApplicationContext){
        this.webApplicationContext = webApplicationContext;
    }

    private void sendMessage(HttpExchange exchange,String message,int code) throws IOException{
        String response = ERROR_MESSAGE_TEMPLATE.replaceAll("#message#",message);
        exchange.sendResponseHeaders(code, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
