package com.pi.nbcenter.util.comm;

import java.util.List;

public class ObjectUtil {
  /**
   * @description 获取单个对象
   * @param tList
   * @return
   */
  public static <T>T getOne(List<T> tList){
    return (null == tList || tList.size() == 0)? null : tList.get(0);
  }
}
