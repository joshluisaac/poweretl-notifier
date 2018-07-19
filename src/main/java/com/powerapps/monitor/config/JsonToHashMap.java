package com.powerapps.monitor.config;

import com.powerapps.monitor.util.Utils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;

/**
 * This class reads in json file using path using gson and returns a map.
 *
 * @author hashim
 */
@Component
public class JsonToHashMap {
    private final JsonReader jsonReader;
    private final Utils util;

    public JsonToHashMap(JsonReader jsonReader,
                         Utils util){
        this.jsonReader=jsonReader;
        this.util=util;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, String> toHmap(String path){
        return jsonReader.readJson(this.util.listToBuffer(util.readFile(new File(path))).toString(),HashMap.class);
    }
}
