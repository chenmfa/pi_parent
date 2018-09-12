package com.pi.mybatis.page.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PageResult<E> extends ArrayList<E> {
  private static final long serialVersionUID = 7215625667311856620L;
  private int pageNumber; //当前页数
  private int pageSize; //每页大小
  private int pageCount;  //总页数
  private int totalCount; //总条数
  public PageResult(int pageNumber, int pageSize, int pageCount, int totalCount,  Collection<E> elements) {
    super(null == elements?new ArrayList<E>():elements);
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.pageCount = pageCount;
    this.totalCount = totalCount;
  }
  public PageResult(int pageNumber, int pageSize, int totalCount,  List<E> elements) {
    super(null == elements?new ArrayList<E>():elements);
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    if(totalCount >0 && pageCount >0){      
      this.pageCount = (totalCount-1)/pageSize + 1;
    }
    this.totalCount = totalCount;
  }
  public int getPageNumber() {
    return pageNumber;
  }
  public int getPageSize() {
    return pageSize;
  }
  public int getPageCount() {
    return pageCount;
  }
  public int getTotalCount() {
    return totalCount;
  }
  public boolean isLastPage(){
    return pageCount == pageNumber;
  }
  
  public boolean isFirstPage(){
    return pageNumber == 1;
  }
  
  public boolean hasMorePage(){
    return pageCount > pageNumber;
  }
  public int getStartIndex(){
    if(pageNumber < 1){
      return 0;
    }
    return (pageNumber -1) * pageSize;
  }
}
