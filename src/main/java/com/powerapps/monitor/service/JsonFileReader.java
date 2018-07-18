package com.powerapps.monitor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

@Component
public class JsonFileReader {
    private static final Logger LOG = LoggerFactory.getLogger(JsonFileReader.class);

    public HashMap readJsonToMap(String path) throws IOException {
        HashMap expandedJson = new HashMap();
        try {
            expandedJson = new ObjectMapper().readValue(new File(path), HashMap.class);
        } catch (FileNotFoundException e) {
            LOG.info("First time visiting the page, content will be displayed after saving json data." +e);
        }
        return expandedJson;
    }
}
