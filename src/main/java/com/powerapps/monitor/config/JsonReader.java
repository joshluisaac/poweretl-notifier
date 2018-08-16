package com.powerapps.monitor.config;

import com.google.gson.Gson;

import java.io.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonReader {
    private final Gson gson;

    @Autowired
    public JsonReader(Gson gson){
        this.gson = gson;
    }

    public <T> T readJson(String jsonText, Class<T> t){
       return  gson.fromJson(jsonText, t);
    }
    
    
    public <T> T readJson(Reader reader, Class<T> t){
      return  gson.fromJson(reader, t);
   }




}
