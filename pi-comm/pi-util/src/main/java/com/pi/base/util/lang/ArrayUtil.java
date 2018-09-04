package com.pi.base.util.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 
* @author chenmfa
* @version 创建时间：2017年12月14日 下午5:07:00 
* @description
*/
public class ArrayUtil{
  /**
   * @description 合并多个数组
   * @param tArrays 多个数组
   * @return int 长度
   */
  public static <T>T[] concat(@SuppressWarnings("unchecked") T[]... tArrays){
    T[] copies = null;
    if(null != tArrays && tArrays.length>0){
      int sum = (null != tArrays[0])?tArrays[0].length:0;
      copies = Arrays.copyOf(tArrays[0], count(tArrays));
      if(tArrays.length>1){        
        for(int a=1, length= tArrays.length; a<length; a++){
          System.arraycopy(tArrays[a], 0, copies, sum, (null != tArrays[a])?tArrays[a].length:0);
          sum += tArrays[a].length;
        }
      }
    }
    return copies;
  }
  
  /**
   * @description 计算数组队列的元素总长度
   * @param tArrays 多个数组
   * @return int 长度
   */
  public static int count(Object[]... tArrays){
    int i = 0;
    if(null != tArrays && tArrays.length>0){
      for(int a=0, length= tArrays.length; a<length; a++ ){
        i += (null != tArrays[a])?tArrays[a].length:0;
      }
    }
    return i;
  }

  /**
   * @description 用于在list中查找特定的list
   * @param containerList 在哪个list中查找特定的list
   * @param targetList    需要查找的list
   * @param <T>
   * @return list在container中的索引值，如果不存在则返回-1
   */
  public static <T> int indexOfList(List<T> containerList, List<T> targetList) {
    // 先转移数据，避免影响原先的数据,注意如果直接赋值，只是给了引用
    List<T> container = new ArrayList<T>();
    container.addAll(containerList);
    List<T> target = new ArrayList<T>();
    target.addAll(targetList);

    if (target.size() > 0) {
      if (target.size() > container.size()) {//如果被搜索的数组大于可搜索的数组,直接返回-1
        return -1;
      }
      //针对｛1，2，3，2，3，4｝中查找｛2，3，4｝第一次出现23的时候不符合但不能终止查找，所以要继续
      while (true) {
        T firstE = target.get(0);
        // 从第一个匹配到的位置开始查找
        int i = container.indexOf(firstE);//查找第一个元素的位置, 如果加上搜索的数组都比总的大，则返回没找到
        if (i == -1 || target.size() + i > container.size()) {
          return -1;
        }

        int o1 = i, o2 = 0;
        while (o1 < container.size() && o2 < target.size()) {
          if (!(container.get(o1).equals(target.get(o2)))) {
            break;
          }
          o1++;
          o2++;
        }
        if (o2 == target.size()) {
          return i;
        }
        //如果这一次查找失败，需要将前面查找过的数据移除
        for (int j = 0; j < (i + 1); j++) {
          container.remove(0);
        }
      }
    }
    return -1;
  }
  
  
  /**
   * @description 用于在数组中查找特定的数组
   * @param container 在哪个数组中查找特定的数组
   * @param target    需要查找的数组
   * @return target在container中的索引值，如果不存在则返回-1
   */
  public static int indexOfArray(Object[] container, Object[] target) {
    int start = 0;
    if (target.length > 0) {
    if (target.length > container.length) {
      return -1;
    }
    // 针对｛1，2，3，2，3，4｝中查找｛2，3，4｝第一次出现23的时候不符合但不能终止查找，所以要继续
    while (true) {
      // 从第一个匹配到的位置开始查找
      for (; start < container.length; start++) {
        if (container[start].equals(target[0]))
        break;
      }

      if (start == container.length
        || target.length + start > container.length) {
        return -1;
      }
      int o1 = start, o2 = 0;
      while (o1 < container.length && o2 < target.length) {
        if (!(container[o1].equals(target[o2]))) {
          break;
        }
        o1++;
        o2++;
      }
      if (o2 == target.length) {
        return start;
      }
      start++;
    }
    }
    return -1;
  }
}
