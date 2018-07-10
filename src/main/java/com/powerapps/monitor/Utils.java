package com.powerapps.monitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Utils {
  
  private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
  
  
  //reads a text file into a list data structure
  public static List<String> readLogFile(File f) {
    List<String> list = new ArrayList<>();
    int lineNumber = 0;
    try(BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
      String line;
      while((line = r.readLine()) != null) {
        lineNumber++;
        list.add(lineNumber  + " " + line);
      }
    } catch(Exception e) {
      LOG.error("{}",e.getMessage());
    }
    return list;
  }
  
  
  
  public static Matcher matcher(final String candidate, final String pattern) {
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(candidate);
    return m;
  }


  
  
  

}
