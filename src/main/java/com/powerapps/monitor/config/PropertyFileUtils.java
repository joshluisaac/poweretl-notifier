package com.powerapps.monitor.config;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is used to read and write Property files needed by the scheduler
 *
 * @author hashim
 */
@Component
public class PropertyFileUtils {

    public Properties readPropFile(String path) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(path));
        return prop;
    }

    public void writePropFile(String path, String key, String value)
            throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(path));
        prop.setProperty(key, value);
        prop.store(new FileOutputStream(path), null);
    }

}