package com.powerapps.monitor.util;

import com.kollect.etl.util.PropertiesUtils;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * This class is used to read and write Property files needed by the scheduler
 *
 * @author hashim
 */
@Component
public class PropertyFileUtils {

    public void hashMapToProp(String path, HashMap<String, String> keyValuePair)
            throws IOException{
        Properties prop = new PropertiesUtils().loadPropertiesFile(path);
        for (String key : keyValuePair.keySet())
            prop.setProperty(key, keyValuePair.get(key));
        prop.store(new FileOutputStream(path), null);
    }

    public HashMap<String, String> propToHashMap(String path) throws IOException{
        HashMap<String, String> hMap = new HashMap<>();
        Properties prop = new PropertiesUtils().loadPropertiesFile(path);
        for (Object property : prop.keySet()){
            hMap.put(property.toString(), String.valueOf(prop.get(property)));
        }
        return hMap;
    }
}
