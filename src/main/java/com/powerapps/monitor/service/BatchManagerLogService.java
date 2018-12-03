package com.powerapps.monitor.service;

import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.ListUtils;
import com.powerapps.monitor.model.Batch;
import com.powerapps.monitor.model.BmProperties;
import com.powerapps.monitor.model.LogSummary;
import com.powerapps.monitor.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BatchManagerLogService {

    private static final Logger LOG = LoggerFactory.getLogger(BatchManagerLogService.class);

    @Value("${app.bmJson}")
    private String bmJsonPath;

    public BmProperties bmConfig;

    @Autowired
    public BatchManagerLogService(BmProperties bmConfig) {
        this.bmConfig = bmConfig;
    }
    
    private String getStartRegex(){
        return bmConfig.getBatchStartRegex();
    }

    private String getErrorRegex(){
        return bmConfig.getBatchErrorRegex();
    }

    private String getDoneRegex(){
        return bmConfig.getBatchDoneRegex();
    }

    private String getRootPath(){
        return bmConfig.getBmRootPath();
    }

    public String getBmCache(){
        return bmConfig.getBmCache();
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
    private List<String> getCachedList(File cacheFile) throws IOException {
        return new FileUtils().readFile(cacheFile);
    }

    // compares all files starting with prefix "Batch-" against cache list
    // removes the cache list from available list aka logList
    private List<String> fetchNewlyAddedLogFiles(List<String> cacheList, List<String> logList) {
        return new ListUtils().subtract(cacheList, logList);
    }
    
    
    public List<LogSummary> getBmLogSummaries() throws IOException {
      List<String> availableLogList = getLogFiles(new File(getRootPath()));
      List<String> cacheList = getCachedList(new File(getBmCache()));
      List<String> unCachedList = fetchNewlyAddedLogFiles(cacheList, availableLogList);
      int count = unCachedList.size();
      List<LogSummary> summaries = new ArrayList<>();
      LOG.info("Number of files uncached: {}", count);
      if (count != 0) {
          for (String log : unCachedList) {
            LogSummary summary = summarizeLog(log);
            summaries.add(summary);
          }
      } else {
          LOG.info("Count is {}, nothing to cache", count);
      }
      return summaries;
  }
    
    

    private LogSummary summarizeLog(String log) {
        //System.out.println(log);
        List<String> regexList = new ArrayList<>(Arrays.asList(getStartRegex(),
                getErrorRegex(), getDoneRegex()));
        //LOG.info("Processing {}", log);
        List<String> lines = Utils.readLogFile(new File(getRootPath(), log));
        boolean isStartEntry = false;
        boolean isDoneEntry = false;
        boolean isErrorEntry = false;
        boolean errorTerminated = false;
        int batchStatus = 0;
        double runningTime = 0.0;
        Batch batch = new Batch();
        String startTime;
        String endTime = null;
        Timestamp batchStartTime = null;
        Timestamp batchEndTime = null;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            //System.out.println(line);
            if (!isStartEntry) {
                boolean isStarted = Utils.matcher(line, regexList.get(0)).find();
                if (isStarted) {
                    String columns[] = line.split("\\s+");
                    startTime = columns[1] + " " + columns[2].split(",")[0] + "." + columns[2].split(",")[1];
                    batchStartTime = Timestamp.valueOf(startTime);
                    batch.setStartTime(batchStartTime);
                    isStartEntry = true;
                    continue;
                }
            }
            if (!isErrorEntry) {
                boolean isDone = Utils.matcher(line, regexList.get(1)).find();
                if (isDone) {
                    String columns[] = line.split("\\s+");
                    endTime = columns[1] + " " + columns[2].split(",")[0] + "." + columns[2].split(",")[1];
                    batchEndTime = Timestamp.valueOf(endTime);
                    batch.setEndTime(batchEndTime);
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
                    endTime = columns[1] + " " + columns[2].split(",")[0] + "." + columns[2].split(",")[1];
                    batchEndTime = Timestamp.valueOf(endTime);
                    batch.setEndTime(batchEndTime);
                    isDoneEntry = true;
                    batchStatus = 2; // successful
                    continue;
                }
            }
            
            
            if ((i == (lines.size() - 1)) && (endTime == null)) {
                batchStatus = 3; // inprogress
                batchEndTime = new Timestamp(System.currentTimeMillis());
                batch.setEndTime(batchEndTime);
            }  
            
            
            
        }
        if(batchStatus == 3) {
          if(batch.getStartTime() == null) {
            batchStatus = 1; // failed
            
            Timestamp now = new Timestamp(System.currentTimeMillis());
            batchEndTime = now;
            batchStartTime = now;
            batch.setStartTime(batchStartTime);
            batch.setEndTime(batchStartTime);
          }
        }
        batch.setRunningTime(((batch.getEndTime().getTime() - batch.getStartTime().getTime())/ 1000) / 60f);
        return new LogSummary(log, isStartEntry, isDoneEntry, errorTerminated, batch.getStartTime(), batch.getEndTime(), batchStatus,
                batch.getRunningTime());
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
