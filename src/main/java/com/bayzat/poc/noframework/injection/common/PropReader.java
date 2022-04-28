package com.bayzat.poc.noframework.injection.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PropReader
{
    private static PropReader instance = new PropReader();
    private Map<String,String> propMap;

    public static PropReader instance(){
        return instance;
    }

    private PropReader()
    {
        this.propMap = new HashMap<>();
        InputStream is = PropReader.class.getClassLoader().getResourceAsStream("application.prop");
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        try{
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                this.propMap.put(parts[0],parts[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Integer getAsInt(String key){
        return Integer.parseInt(propMap.get(key));
    }

    public String getAsString(String key){
        return propMap.get(key);
    }

}
