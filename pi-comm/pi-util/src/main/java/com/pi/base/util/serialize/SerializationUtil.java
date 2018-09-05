package com.pi.base.util.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/** 
* @author chenmfa
* @version 创建时间：2017年7月20日 上午11:12:32 
* @description
*/
public class SerializationUtil {
  
  /**
   * @description 将对象序列化成字节数组
   * @param obj 需要序列化的对象
   * @return
   * @throws IOException
   */
  public static byte[] serializeObject(Serializable serializable) throws IOException{
    ObjectOutputStream oos = null;
    ByteArrayOutputStream baos = null;
    try{
      baos = new ByteArrayOutputStream();
      oos = new ObjectOutputStream(baos);
      oos.writeObject(serializable);
      return baos.toByteArray();
    }finally{
      if(oos != null ){
        oos.close();
      }
      if(baos != null ){
        oos.close();
      }
    }
  }
  /**
   * @description 将字节数组反序列化成对象
   * @param obj 需要序列化的对象
   * @return
   * @throws IOException
   */
  @SuppressWarnings("unchecked")
  public static <T>T deSerializeObject(byte[] by) throws IOException, ClassNotFoundException{
    ByteArrayInputStream bais = null;
    ObjectInputStream ois = null;
    try{
      bais = new ByteArrayInputStream(by);
      ois = new ObjectInputStream(bais);
      Object obj = ois.readObject();
      return (T)obj;
    }finally{
      if(null != ois){
        ois.close();
      }
      if(null != bais ){
        bais.close();
      }
    }
  }
}
