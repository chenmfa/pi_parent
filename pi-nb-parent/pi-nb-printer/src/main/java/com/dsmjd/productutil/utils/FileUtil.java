package com.dsmjd.productutil.utils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
  
  private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
  
  /**
   * @description 将字符串格式的内容写到文件
   * @param content 字符串
   * @param path 文件路径
   */
  public static void saveStringContentToQrcode(String content, String path){
    if(null != content){
      try {
        byte[] buf = content.getBytes("UTF-8");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
        bos.write(buf);
        bos.flush();
        bos.close();
      } catch (IOException  e) {
        logger.error("保存内容到文件失败",e);
      }
    }
  }
  /**
   * @description 将流内容写到文件
   * @param InputStream 流对象
   * @param path 文件路径
   */
  public static void saveStreamToQrcode(InputStream in, String path){
    if(null != in){
      try {
        int i = 0;
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
        while((i = in.read()) != -1){          
          bos.write(i);
        }
        bos.flush();
        bos.close();
      } catch (IOException  e) {
        logger.error("保存内容到文件失败",e);
      }
    }
  }
}
