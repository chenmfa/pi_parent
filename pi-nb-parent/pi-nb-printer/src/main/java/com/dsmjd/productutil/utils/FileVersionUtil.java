package com.dsmjd.productutil.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileVersionUtil {
  
  private static final Logger logger = LoggerFactory.getLogger(FileVersionUtil.class);
  public static final Map<String,Integer> versionMap = new HashMap<String,Integer>();
   
  public static void main(String[] args) throws IOException {   
    initializeVersionMap();
    increaseLockSerialNo("T700_0");
    System.out.println(getCurrentReleaseNo()+String.format("%07d", versionMap.get("T700_0")));
  }
  
  public static void increaseLockSerialNo(String lockType) throws IOException{
    if(!versionMap.containsKey("Year")){
      Calendar cal = Calendar.getInstance();
      int year = cal.get(Calendar.YEAR);
      versionMap.put("Year", year);
    }
    Integer count = versionMap.get(lockType);
    versionMap.put(lockType, (null == count)?1:count.intValue()+1);
    File file = new File("versioninfo.dat");
    if( !file.exists()){
      file.createNewFile();
    }
    BufferedWriter writer =new BufferedWriter(new FileWriter("versioninfo.dat"));
    Set<Entry<String, Integer>> versionset = versionMap.entrySet();
    Iterator<Entry<String, Integer>> it = versionset.iterator();
    try{
      while(it.hasNext()){
        Entry<String, Integer> next = it.next();
        writer.write(next.getKey()+"|"+next.getValue());
        writer.newLine();
      }     
      writer.flush();  
    }finally{
      writer.close();      
    }
  }
  
  public static void initializeVersionMap() throws IOException{
    File file = new File("versioninfo.dat");
    if( !file.exists()){
      file.createNewFile();
    }else{
      /*Process p = Runtime.getRuntime().exec(String.format("cmd /C dir %s /tc", file.getAbsolutePath()));
      System.out.println(file.getAbsolutePath());
      InputStream is = p.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String result;
      String getTime = null;
      while ((result = br.readLine()) != null) {
        String[] str = result.split(" ");
        for (int i = str.length - 1; i >= 0; i--) {
          if (str[i].equals(file.getName())) {
            getTime = str[0];
          }
        }
      }
      String[] fileInfo = getTime.replaceAll("/", "").split(" ");
      int createYear = 0;
      try{        
        createYear = Integer.parseInt(fileInfo[0]);
      }catch(Exception e){
        
      }
      System.out.println("文件创建时间："+getTime);*/
    
      BufferedReader reader = new BufferedReader((new FileReader(file)));
      String line="";
      try{  
        while(( line = reader.readLine()) !=null){
          if(line.indexOf("|")>=0){
            String[] versionArr = line.split("[|]");
            int count;
            try {
              count = Integer.parseInt(versionArr[1]);
            } catch (NumberFormatException e) {
              count =0;
            }
            versionMap.put(versionArr[0], count);
          }else{
            logger.error("生产二维码记录数出错"+line);
          }
        }
        Integer createYear = versionMap.get("Year");
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        if(null == createYear){
          versionMap.put("Year",year);
        }else if(year>createYear){
         file.createNewFile();
         versionMap.clear();
         versionMap.put("Year",year);
        }
      }finally{
        reader.close();
      }
    }
  }
  public static String getCurrentReleaseNo(){
    StringBuilder sb = new StringBuilder();
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    //年
    char y =(char)(year-2016+65);
    //System.out.println((char)(year-2016+65));
    //月
    int month = cal.get(Calendar.MONTH);
    month = (month >=8)?month+1:month;
    char m = (char)(65+month);
    //System.out.println((char)(65+month));
    //
    int day = cal.get(Calendar.DAY_OF_MONTH);
    String d = String.format("%02d", day);
    //System.out.println(String.format("%02d", day));
    System.out.println(y+m+d);
    sb.append(y);
    sb.append(m);
    sb.append(d);
    return sb.toString(); 
  }
}
