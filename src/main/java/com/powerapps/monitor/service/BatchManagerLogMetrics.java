package com.powerapps.monitor.service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.kollect.etl.util.FileUtils;
import com.powerapps.monitor.Utils;
import com.powerapps.monitor.model.Batch;

public class BatchManagerLogMetrics {

	public static final String STARTING_REGEX = "(\\d+-\\d+-\\d+\\s+\\d+:\\d+\\d+:\\d+,\\d+)\\s+(INFO)\\s+(Starting)\\s+-\\s+(Batch)\\s+-\\s+(\\w+)";
	public static final String FINISHED_REGEX = "(\\d+-\\d+-\\d+\\s+\\d+:\\d+\\d+:\\d+,\\d+)\\s+(INFO)\\s+(Finished)\\s+-\\s+(Batch)\\s+-\\s+";

	
	 public List<String> getFile(File cacheFile) throws IOException {
		    return new FileUtils().readFile(cacheFile);
		  }
	 
	public static void main(String[] args) throws IOException {
		File f = new File("C:\\Users\\Chelsea\\workspace\\powerappslogmonitor\\batches_notifications\\BatchManager\\eCollect\\Batch-01.Jun.2018-07_48_02.log");
		BatchManagerLogMetrics bm = new BatchManagerLogMetrics();
		List<String> lines = bm.getFile(f);
		
		System.out.println(bm.extract2(lines));
	}
	
	public List<Batch> extract2(List<String> list) {
		List<String> regexList = new ArrayList<>(Arrays.asList(STARTING_REGEX, FINISHED_REGEX));
		boolean startFound = false;
		boolean finishFound=false;
		String group5=null;
		Batch batch =  null;
		List<Batch> batchList = new ArrayList<>();
			for(int j =0; j<list.size(); j++) {
				
				if(!startFound) {
					Matcher startingMatcher = Utils.matcher(list.get(j), regexList.get(0));
					if(startingMatcher.find()) {
						
						String group1 = startingMatcher.group(1);
						String group2 = startingMatcher.group(2);
						String group3 = startingMatcher.group(3);
						String group4 = startingMatcher.group(4);
						group5 = startingMatcher.group(5);						
//						System.out.println(group1+"|"+group2+"|"+group3+"|"+group4+"|"+group5);
						startFound=true;
						finishFound=false;
						batch = new Batch();
						batch.setBatchName(group5);
						String[] timeTokens = group1.split("\\,");		
						batch.setStartTime(Timestamp.valueOf(timeTokens[0]+"."+timeTokens[1]));
						
						continue;
					}
				}
				
				if(!finishFound) {
					Matcher finishedMatcher = Utils.matcher(list.get(j), regexList.get(1)+"("+group5+")");
					if(finishedMatcher.find()) {
						String group1 = finishedMatcher.group(1);
						String group2 = finishedMatcher.group(2);
						String group3 = finishedMatcher.group(3);
						String group4 = finishedMatcher.group(4);
						
//						extractedLines.add(line);
//						System.out.println(group1+"|"+group2+"|"+group3+"|"+group4+"|"+group5);
						String[] timeTokens = group1.split("\\,");
						Timestamp endTime = Timestamp.valueOf(timeTokens[0]+"."+timeTokens[1]);
						batch.setEndTime(endTime);
						finishFound=true;
						startFound=false;				
						double runningTime = (endTime.getTime() - batch.getStartTime().getTime() / 1000) / 60f;
						batch.setRunningTime(runningTime);
						batchList.add(batch);
						continue;
					}
				}
				
			}
		
		return batchList;
	}
	
}
