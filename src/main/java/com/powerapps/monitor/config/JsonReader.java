package com.powerapps.monitor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

@Component
public class JsonReader {

    @Autowired
    private final Gson gson;

    public JsonReader(Gson gson){
        this.gson = gson;
    }

    public <T> T readJson(String jsonText, Class<T> t){
       return  gson.fromJson(jsonText, t);
    }




}
