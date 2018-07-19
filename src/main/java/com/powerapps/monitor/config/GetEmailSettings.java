package com.powerapps.monitor.config;

import com.powerapps.monitor.util.Utils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;

/**
 * This class reads in email properties from jSon using gson and returns a map.
 *
 * @author hashim
 */
@Component
public class GetEmailSettings {
    private final JsonReader jsonReader;
    private final Utils util;

    public GetEmailSettings(JsonReader jsonReader,
                            Utils util){
        this.jsonReader=jsonReader;
        this.util=util;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, String> getSettings(String path){
        return jsonReader.readJson(this.util.listToBuffer(util.readFile(new File(path))).toString(),HashMap.class);
    }
}
