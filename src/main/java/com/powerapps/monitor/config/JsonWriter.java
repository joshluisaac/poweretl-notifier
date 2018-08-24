package com.powerapps.monitor.config;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

/**
 * The purpose of this class is to convert Java plain objects into JSON formatted string
 *
 * @author joshua
 */

@Component
public class JsonWriter {
    private Gson gson;

    public JsonWriter() {
        this.gson = new Gson();
    }

    public <T> String generateJson(T prop) {
        return gson.toJson(prop);
    }
}
