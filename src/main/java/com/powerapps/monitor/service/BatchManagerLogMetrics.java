package com.powerapps.monitor.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import com.kollect.etl.util.FileUtils;
import com.powerapps.monitor.Utils;
import com.powerapps.monitor.model.Batch;

/**
 * The Class that extracts some relevant features from BatchManager log file
 * 
 * @author Chelsea Kosasih 
 */

//consider moving this to a component package and remaning this to feature extractor as in FeatureExtractor
public class BatchManagerLogMetrics {

  public static final String STARTING_REGEX = "(\\d+-\\d+-\\d+\\s+\\d+:\\d+\\d+:\\d+,\\d+)\\s+(INFO)\\s+(Starting)\\s+-\\s+(Batch)\\s+-\\s+(\\w+)";
  public static final String FINISHED_REGEX = "(\\d+-\\d+-\\d+\\s+\\d+:\\d+\\d+:\\d+,\\d+)\\s+(INFO)\\s+(Finished)\\s+-\\s+(Batch)\\s+-\\s+";

  public List<String> getFile(File cacheFile) throws IOException {
    return new FileUtils().readFile(cacheFile);
  }

  
  public List<Batch> extractFeatures(List<String> list) {
    List<String> regexList = new ArrayList<>(Arrays.asList(STARTING_REGEX, FINISHED_REGEX));
    boolean startFound = false;
    boolean finishFound = false;
    String processName = null;
    Batch batch = null;
    List<Batch> batchList = new ArrayList<>();
    for (int j = 0; j < list.size(); j++) {
      if (!startFound) {
        Matcher startingMatcher = Utils.matcher(list.get(j), regexList.get(0));
        if (startingMatcher.find()) {
          String timeString = startingMatcher.group(1);
          processName = startingMatcher.group(5);
          batch = new Batch();
          batch.setBatchName(processName);
          String[] timeTokens = timeString.split("\\,");
          batch.setStartTime(Timestamp.valueOf(timeTokens[0] + "." + timeTokens[1]));
          startFound = true;
          finishFound = false;
          continue;
        }
      }
      if (!finishFound) {
        Matcher finishedMatcher = Utils.matcher(list.get(j), regexList.get(1) + "(" + processName + ")");
        if (finishedMatcher.find()) {
          String timeString = finishedMatcher.group(1);
          String[] timeTokens = timeString.split("\\,");
          Timestamp endTime = Timestamp.valueOf(timeTokens[0] + "." + timeTokens[1]);
          batch.setEndTime(endTime);
          double runningTime = (endTime.getTime() - batch.getStartTime().getTime() / 1000) / 60f;
          batch.setRunningTime(runningTime);
          batchList.add(batch);
          finishFound = true;
          startFound = false;
          continue;
        }
      }

    }
    return batchList;
  }

}
