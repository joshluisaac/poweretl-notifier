package com.powerapps.monitor.service;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.FilenameFilter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ZipFileHelper {
  
  private static final Logger LOG = LoggerFactory.getLogger(ZipFileHelper.class);
  
  public File[] listChildFiles(final File dir) {
    File[] files = dir.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".stacktrace") || name.endsWith(".bad");
      }
    });
    return files;
  }
  
  public String zipFile(String context, File[] files, String Dest) throws Exception {
    final String zipFileName =  context + "_rejection_stacktrace_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).concat(".zip");
    
    
    
    FileOutputStream fos = new FileOutputStream(new File(Dest,zipFileName));
    ZipOutputStream zos = new ZipOutputStream(fos);
    for(File f : files) {
      FileInputStream fis = new FileInputStream(f);
      ZipEntry zipEntry = new ZipEntry(f.getName());
      zos.putNextEntry(zipEntry);
      byte[] bytes = new byte[1024];
      int length;
      while((length = fis.read(bytes)) >= 0) {
        zos.write(bytes, 0, length);
      }
      fis.close();
      LOG.info("Added {} to zip file", f.getName());
      
    }
    zos.close();
    fos.close();
    LOG.info("Created zip file {} ", zipFileName);
    String finalFile = Dest +"/"+ zipFileName;
    return finalFile;
  }
  
  
  public static void main(String[] args) throws Exception {
    //File dir = new File("/home/kllctvalley/yyc_loading/DataConnector/logs/");
    //File[] files = listChildFiles(dir);
    //zipFile(files,"/home/kllctvalley/yyc_loading/DataConnector/logs/");
  }

}
