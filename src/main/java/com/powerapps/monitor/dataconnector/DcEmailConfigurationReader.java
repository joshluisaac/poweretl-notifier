package com.powerapps.monitor.dataconnector;

import com.google.gson.reflect.TypeToken;
import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.JsonUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DcEmailConfigurationReader {

  public String fakeConfig(){
    List<DcEmailConfiguration> configs = new ArrayList<>();
    Map<String, DcEmailConfiguration> map = new HashMap<>();

    DcEmailConfiguration config1 = new DcEmailConfiguration("1","2", "true","* * 90","","","","","","mbsb", "4", "true");
    DcEmailConfiguration config2 = new DcEmailConfiguration("4","6", "false","* 45 *","Server.log","/home/joshua","9","br","","br","4", "true");

    configs.add(config1);
    configs.add(config2);

    map.put("MBSB", config1);
    map.put("BR", config2);

    System.out.println(new JsonUtils().toJson(map));
    return new JsonUtils().toJson(configs);
  }

  public  List<String> read(File file) throws IOException {
    return new FileUtils().readFile(file);
  }


  public String jsonSerializer(List<DcEmailConfiguration> configs){
    return new JsonUtils().toJson(configs);
  }


  public Map<String, DcEmailConfiguration> jsonDeSerializer(File file) throws FileNotFoundException {
    return new JsonUtils().fromJson(new FileReader(file), new TypeToken<Map<String,DcEmailConfiguration>>(){}.getType());
  }

  public static void main(String[] args) {
    new DcEmailConfigurationReader().fakeConfig();
  }

}
