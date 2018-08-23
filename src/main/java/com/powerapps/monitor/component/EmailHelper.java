package com.powerapps.monitor.component;

import com.kollect.etl.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailHelper {

  @Value("${app.cacheFilePath}")
  private String cacheFilePath;

  private final Logger logger = LoggerFactory.getLogger(EmailHelper.class);

  private ComponentProvider comProvider;

  @Autowired
  public EmailHelper(ComponentProvider comProvider, @Value("${app.cacheFilePath}") String cacheFilePath) {
    this.comProvider = comProvider;
    this.cacheFilePath = cacheFilePath;
  }

  // will aggregate log files which starts from a list of prefixes
  private List<String> aggregateLogFiles(File dir, List<String> prefixes) {
    List<String> list = new ArrayList<>();
    for (String prefix : prefixes)
      list.addAll(comProvider.getFileStartsWith(dir, prefix));
    return list;
  }

  public List<String> fetchNewManifestLog(File dir, List<String> prefixes) throws IOException {
    List<String> availableLogList = aggregateLogFiles(dir, prefixes);
    List<String> cacheList = comProvider.getLogManifestCache(new File(cacheFilePath));
    List<String> diff = comProvider.getListDifference(cacheList, availableLogList);
    return diff;
  }

  public void persistToCache(List<String> unCached) {
    if (unCached.size() > 0) {
      for (String log : unCached) {
        new FileUtils().writeTextFile(cacheFilePath, log + "\n");
        logger.info("Persisted {} to cache", log);
      }
    }
  }
  
  
  public void persistToCache(String log) {
    new FileUtils().writeTextFile(cacheFilePath, log + "\n");
    logger.info("Persisted {} to cache", log);
  }

}
