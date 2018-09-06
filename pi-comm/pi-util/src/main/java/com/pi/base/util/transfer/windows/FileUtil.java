package com.pi.base.util.transfer.windows;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi.base.enumerate.file.FileType;

/**
 * @author zoumy
 * @date 2016年11月17日
 * @description 文件操作基础类
 * @description transferToRemote("121.43.225.110", "share", "Xj1KCzij*cpDu0BG", "upload/cert/alipay", new File("D:\\test.txt"));
 */
public class FileUtil{

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * @description 传输文件到远程共享服务器
	 * @return
	 */
	public static String transferToRemote
	  (String host,String username, String password, String parentfolder, File file) 
	    throws IOException{
	  String remotePhotoUrl = "smb://"+username+":"+password+"@"+host+"/"+parentfolder+"/"; //存放文件的共享目录  
    String filename = System.currentTimeMillis() + "." + FileType.PNG.name().toLowerCase();
    InputStream in =null;
    OutputStream out = null;
    try{
      SmbFile remoteFile = new SmbFile(remotePhotoUrl + "/" + filename);
      remoteFile.connect(); //尝试连接  
      File f = new File("//"+host+"/"+parentfolder+"/");
      if(!f.exists()){
        f.mkdirs();
      }
      in = new BufferedInputStream(new FileInputStream(file));  
      out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));  
    
      byte[] buffer = new byte[4096];  
      int len = 0; //读取长度  
      while ((len = in.read(buffer, 0, buffer.length)) != -1) {  
          out.write(buffer, 0, len);  
      }  
      out.flush(); //刷新缓冲的输出流
    }finally{
      safetyClose(in);
      safetyClose(out);
    }
    return filename;
	}
	
	/**
   * @description 传输文件到远程共享服务器
   * @param stream 输入流
   */
  public static String transferToRemote
    (String host,String username, String password, String parentfolder, InputStream stream,String filename) 
      throws IOException{
    String remotePhotoUrl = "smb://"+username+":"+password+"@"+host+"/"+parentfolder+"/"; //存放文件的共享目录  
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    filename = fmt.format(new Date())+filename;
    InputStream in =null;
    OutputStream out = null;
    try{
      SmbFile remoteFile = new SmbFile(remotePhotoUrl + "/" + filename);
      remoteFile.connect(); //尝试连接  
      File f = new File("//"+host+"/"+parentfolder+"/");
      if(!f.exists()){
        f.mkdirs();
      }
      in = new BufferedInputStream(stream);  
      out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));  
    
      byte[] buffer = new byte[4096];  
      int len = 0; //读取长度  
      while ((len = in.read(buffer, 0, buffer.length)) != -1) {  
          out.write(buffer, 0, len);  
      }  
      out.flush(); //刷新缓冲的输出流
    }finally{
      safetyClose(in);
      safetyClose(out);
    }
    return filename;
  }
	
	/**
	 * @description 删除远程服务器的共享文件
	 * @return true
	 */
  public static boolean removeRemoteFile(String host,String username, String password, String parentfolder, String filename) 
    throws IOException{
    String remoteFileName = "smb://"+username+":"+password+"@"+host+"/"+parentfolder+"/"+filename; //存放文件的共享目录
    SmbFile remoteFile = new SmbFile(remoteFileName);
    if(remoteFile.exists()){
      remoteFile.delete();
    }
    return true;
  }

	/**
	 * @description 写入文件，返回文件名 ,写入失败，返回null
	 * @return filename 返回写入成功
	 */
	public static String writeToFile(File file, String path) 
	    throws IOException {
		// 写入文件
		InputStream in = null;
		OutputStream out = null;
		File uploadFile = new File(path);
		try {
			in = new FileInputStream(file);
			out = new FileOutputStream(uploadFile);
			byte[] buffer = new byte[1024 * 1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			out.flush();
		}finally{	  
      safetyClose(in);
      safetyClose(out);
		}
		return uploadFile.getName();
	}

	/**
	 * @description 不详，估计是拷贝小文件的，慎用
	 * @return
	 */
	public static void copyFile(File uploadfile, String filePath, int fileSize)
			throws IOException {
		int BUFFER_SIZE = fileSize; 

		File newFile = new File(filePath);
		if (!newFile.getParentFile().exists()) {
			newFile.getParentFile().mkdirs();
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(uploadfile));
		
			out = new BufferedOutputStream(new FileOutputStream(newFile));
		
			byte[] buffer = new byte[BUFFER_SIZE];
			while (in.read(buffer) > 0) {
				out.write(buffer);
			}
			out.flush();
		}finally {
		  safetyClose(in);
		  safetyClose(out);
		}
	}
	
	/**
	 * @description 删除文件，但是不删除目录
	 */
  public static void deleteFile(String path)
      throws FileNotFoundException {
    if (path != null && path.length() > 0) {
      File file = new File(path);
      if (file.exists() && file.isFile()) {
        file.delete();
      }
    }
  }

	public static boolean isExists(String path)
	    throws FileNotFoundException {
		File file = new File(path);
		return file.exists();
	}

	public static boolean createDirectory(String path)
	    throws IOException {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			return file.mkdirs();
		} else {
			return true;
		}
	}

	/**
	 * @description 将文件转换成字符串输出
	 * @return 字符串
	 */
  public static String file2String(File file, String encoding) {
    InputStreamReader reader = null; 
    StringWriter writer = new StringWriter(); 
    try {
      if (encoding == null || "".equals(encoding.trim())) { 
          reader = new InputStreamReader(new FileInputStream(file), encoding); 
      } else { 
          reader = new InputStreamReader(new FileInputStream(file)); 
      } 
      char[] buffer = new char[1024]; 
      int n = 0; 
      while (-1 != (n = reader.read(buffer))) { 
        writer.write(buffer, 0, n); 
      }
    } catch (Exception e) { 
      logger.error("文件转换出错",e);
      return null; 
    } finally {
      safetyClose(reader);      
    }
    return writer.toString(); 
  }
  
  protected static void safetyClose(Closeable close){
    try {
      if(null != close){        
        close.close();
      }
    } catch (IOException e) {
      logger.error("流关闭出错",e);
    }
  }
  
  public static boolean mkDirs(File f,boolean isdir) throws IOException{
    if(!f.exists()){
      if(!isdir){        
        f.getParentFile().mkdirs();
        f.createNewFile();
      }else{
        f.mkdirs();
      }
    }
    return true;
  }

  public static boolean closeStream(InputStream input,OutputStream out,Reader reader,Writer writer)
      throws IOException{
    if(null !=out){
      out.flush();
      out.close();
    }
    if(null != input){
      input.close();
    }
    if(null !=reader){
      reader.close();
    }
    if(null != writer){
      writer.flush();
      writer.close();
    }
    return true;
  }
}
