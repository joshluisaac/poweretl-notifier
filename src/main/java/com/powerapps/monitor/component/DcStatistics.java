package com.powerapps.monitor.component;


import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.dataconnector.Regex;
import com.kollect.etl.util.dataconnector.TotalLoaded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class DcStatistics {
  
  Regex regex;
  JsonUtils jsonUtils;
  
  @Autowired
  public DcStatistics(Regex regex, JsonUtils jsonUtils) {
    this.regex = regex;
    this.jsonUtils = jsonUtils;
  }
  
  
  public List<TotalLoaded> getStats(String serverLog, String daysAgo) {
    List<TotalLoaded> stats = null;
    try {
      stats = regex.getMatchedTokens(serverLog, Integer.parseInt(daysAgo));
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }
    return stats;
  }
  
  public String jsonEncode(List<TotalLoaded> stats) {
    return jsonUtils.toJson(stats);
  }
  
  public String execute(String serverLog, String daysAgo) {
    List<TotalLoaded> stats = getStats(serverLog, daysAgo);
    return jsonEncode(stats);
  }
  
  


}
