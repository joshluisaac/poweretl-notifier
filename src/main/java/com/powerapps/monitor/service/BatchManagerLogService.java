package com.powerapps.monitor.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.ListUtils;
import com.powerapps.monitor.Utils;
import com.powerapps.monitor.model.LogSummary;

@Service
public class BatchManagerLogService {

  private static final Logger LOG = LoggerFactory.getLogger(BatchManagerLogService.class);
  public final String batchStartRegex;
  private final String batchDoneRegex;
  private final String batchErrorRegex;
  // private final String batchStartingRegex = "";
  // private final String batchFinishedRegex = "";
  private final String rootPath;
  
  private final String cacheFileName = "/media/joshua/martian/kvworkspace/powerappslogmonitor/config/bmcache.csv";

  @Autowired
  public BatchManagerLogService(@Value("${app.bmRootPath}") String rootPath,
      @Value("${app.batchStartRegex}") String batchStartRegex, @Value("${app.batchDoneRegex}") String batchDoneRegex,
      @Value("${app.batchErrorRegex}") String batchErrorRegex) {
    this.batchStartRegex = batchStartRegex;
    this.batchDoneRegex = batchDoneRegex;
    this.batchErrorRegex = batchErrorRegex;
    this.rootPath = rootPath;
  }

  // needs refactoring
  public List<String> getLogFiles(File dir) {
    List<String> bmLogFiles = new ArrayList<>();
    for (String file : new FileUtils().getFileList(dir)) {
      if (file.startsWith("Batch") || file.startsWith("Daily") || file.startsWith("defaultSms")
          || file.startsWith("extractExcelImportLetter-") || file.startsWith("legalHostExtraction-")
          || file.startsWith("mortgageSms-") || file.startsWith("refreshRepoAuction"))
        bmLogFiles.add(file);
    }
    return bmLogFiles;
  }

  // cache list
  public List<String> getCachedList(File cacheFile) throws IOException {
    return new FileUtils().readFile(cacheFile);
  }

  // compares all files starting with prefix "Batch-" against cache list
  // removes the cache list from available list aka logList
  public List<String> fetchNewlyAddedLogFiles(List<String> cacheList, List<String> logList) {
    return new ListUtils().subtract(cacheList, logList);
  }

  // obtains the batch status for a particular batch
  public int getBatchStatus(String log) {
    return summarizeLog(log).getBatchStatus();
  }

  public void emailAndPersistToCache() throws IOException {
    List<String> availableLogList = getLogFiles(new File(rootPath));
    List<String> cacheList = getCachedList(new File(cacheFileName));
    List<String> unCachedList = fetchNewlyAddedLogFiles(cacheList, availableLogList);
    int count = unCachedList.size();
    LOG.info("Number of files uncached: {}", count);
    if (count != 0) {
      for (String log : unCachedList) {
        int status = getBatchStatus(log);
        if (status != 3) {
          if(status == 1) {
            //send failed email
            //if email was sent then persist to cache
            new FileUtils().writeTextFile(cacheFileName, log + "\n");
            LOG.info("Cached {}", log);
          } else {
            //send successful email
            //if email was sent then persist to cache
            new FileUtils().writeTextFile(cacheFileName, log + "\n");
            LOG.info("Cached {}", log);
          }
        }
      }
    } else {
      LOG.info("Count is {}, nothing to cache", count);
    }

  }

  public LogSummary summarizeLog(String log) {
    List<String> regexList = new ArrayList<>(Arrays.asList(batchStartRegex, batchErrorRegex, batchDoneRegex));
    //LOG.info("Processing {}", log);
    List<String> lines = Utils.readLogFile(new File(rootPath, log));
    boolean isStartEntry = false;
    boolean isDoneEntry = false;
    boolean isErrorEntry = false;
    boolean errorTerminated = false;
    int batchStatus = 0;
    String startTime = null;
    String endTime = null;
    Timestamp batchStartTime = null;
    Timestamp batchEndTime = null;
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      if (!isStartEntry) {
        boolean isStarted = Utils.matcher(line, regexList.get(0)).find();
        if (isStarted) {
          String columns[] = line.split("\\s+");
          startTime = columns[1] + " " + columns[2].split("\\,")[0] + "." + columns[2].split("\\,")[1];
          batchStartTime = Timestamp.valueOf(startTime);
          isStartEntry = true;
          continue;
        }
      }
      if (!isErrorEntry) {
        boolean isDone = Utils.matcher(line, regexList.get(1)).find();
        if (isDone) {
          String columns[] = line.split("\\s+");
          endTime = columns[1] + " " + columns[2].split("\\,")[0] + "." + columns[2].split("\\,")[1];
          batchEndTime = Timestamp.valueOf(endTime);
          isDoneEntry = true;
          isErrorEntry = true;
          errorTerminated = true;
          batchStatus = 1; // failed
          break;
        }
      }
      if (!isDoneEntry) {
        boolean isDone = Utils.matcher(line, regexList.get(2)).find();
        if (isDone) {
          String columns[] = line.split("\\s+");
          endTime = columns[1] + " " + columns[2].split("\\,")[0] + "." + columns[2].split("\\,")[1];
          batchEndTime = Timestamp.valueOf(endTime);
          isDoneEntry = true;
          batchStatus = 2; // successful
          continue;
        }
      }

      if ((i == (lines.size() - 1)) && (endTime == null)) {
        batchStatus = 3; // inprogress
        batchEndTime = new Timestamp(System.currentTimeMillis());
      }
    }
    double runningTime = ((batchEndTime.getTime() - batchStartTime.getTime()) / 1000) / 60f;
    return new LogSummary(log, isStartEntry, isDoneEntry, errorTerminated, batchStartTime, batchEndTime, batchStatus,
        runningTime);
  }

  // needs refactoring
  public List<LogSummary> getAllLogSummary(List<String> logs) {
    List<LogSummary> summaryMetrics = new ArrayList<>();
    for (String log : logs) {
      summaryMetrics.add(summarizeLog(log));
    }
    return summaryMetrics;
  }

}
