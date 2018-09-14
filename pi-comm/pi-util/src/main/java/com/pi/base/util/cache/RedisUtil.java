package com.pi.base.util.cache;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.pi.base.enumerate.charset.CommCharset;
import com.pi.base.enumerate.redis.RedisCacheEnum;
import com.pi.base.util.config.ConfigUtil;
import com.pi.base.util.serialize.SerializationUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

/**
 * @author chenmfa
 * @description redis 的通用处理工具类
 */
public class RedisUtil {
	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	//jedis 链接地址
	private static String jedisHost = ConfigUtil.getConfig("server.jedisHost","139.129.211.78");
	//jedis 连接端口
	private static int jedisPort = ConfigUtil.getInt("server.jedisPort",6810);
	//jedis连接密码
	private static String jedisPwd = ConfigUtil.getConfig("server.jedisPwd","Z2_XbGc^F8ALfx4BkV@*");
	 
	private static JedisPool jedisPool = JedisPoolHolder.pool;//非切片连接池
	
	public static final String CHAR_SET = "UTF-8";
	
	//等待超时并且未获取到数据
//	private static final String NO_AVAILABLE_SOURCE = "WAIT_NO_SOURCE";
	private RedisUtil(){
	}
	
	/**
	 * @description 初始化连接池
	 * @return
	 */
	private static JedisPool initializePool(){		

		JedisPoolConfig config = generateConfig();
		jedisPool = new JedisPool(config, jedisHost,jedisPort,10000);
		logger.info("Initial Jedis pool from "+ jedisHost +" with port "+ jedisPort);

		return jedisPool;
	}
	/**
	 * @从连接池获取一个redis连接
	 * @return Jedis
	 */
	public static Jedis getInstance(){
	  if(null == jedisPool){
	    initializePool();
	  }
    Jedis jedis = jedisPool.getResource();
    
    if(null != jedisPwd && !"".equals(jedisPwd.trim())){
      jedis.auth(jedisPwd);
    }
    return jedis;
	}
	
	/**
	 * @description 生成redis连接池配置
	 * @return
	 */
	private static JedisPoolConfig generateConfig(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(10);
		config.setMaxTotal(300);
		config.setMaxWaitMillis(10000);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(true);
		//Idle时进行连接扫描
		config.setTestWhileIdle(true);
		//表示idle object evitor两次扫描之间要sleep的毫秒数
		config.setTimeBetweenEvictionRunsMillis(30000);
		//表示idle object evitor每次扫描的最多的对象数
		config.setNumTestsPerEvictionRun(10);
		//表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
		config.setMinEvictableIdleTimeMillis(60000);		
		return config;
	}
	
	/**
	 * @description 删除符合某个pattern的键内容
	 * @param pattern 正则表达式
	 */
  public static void clearJedisCahce(String pattern){
    //单片操作
    Jedis jedisBorrow = null ;
    try{
      jedisBorrow = getInstance();
      Set<String> keys = jedisBorrow.keys(pattern);
      for(String key :keys){
        jedisBorrow.del(key);
      }
    }finally{
      if(null != jedisBorrow){
        jedisBorrow.close();
      }
    }
  }
	
	/**
	 * @description 判断当前查询的键是否存在
	 * @param key 键值
	 * @return exists 是否存在
	 */
	public static boolean isExists(String key){
	  Jedis jedisBorrow = null ;
    try{
      jedisBorrow = getInstance();
      Boolean exists = jedisBorrow.exists(key);
      return exists;
    }finally{
      if(null != jedisBorrow){
        jedisBorrow.close();
      }
    }
	}
	/**
	 * @description 将元素添加到redis队列尾部
	 * @param key
	 * @param serializable
	 * @throws IOException
	 * @return 队列当中的剩余个数
	 */
	public static Long rpush(String key, Serializable serializable) throws IOException{
	  Jedis jedisBorrow = null ;
	  try{
	    jedisBorrow = getInstance();
	    byte[] buf = SerializationUtil.serializeObject(serializable);
	    Long l = jedisBorrow.rpush(key.getBytes(CHAR_SET), buf);
	    return l;
	  }finally{
	    if(null != jedisBorrow){
	      jedisBorrow.close();
	    }
	  }
	}
	
	/**
	 * @description 将元素添加到redis队列尾部
	 * @param key
	 * @param serializable
	 * @throws IOException
	 * @return 队列当中的剩余个数
	 */
	public static Long rpushMessage(String key, String value) throws IOException{
	  Jedis jedisBorrow = null ;
	  try{
	    jedisBorrow = getInstance();
	    Long l = jedisBorrow.rpush(key.getBytes(CHAR_SET), value.getBytes(CHAR_SET));
	    return l;
	  }finally{
	    if(null != jedisBorrow){
	      jedisBorrow.close();
	    }
	  }
	}
	/**
   * @description 将元素添加到redis队列尾部
   * @param key
   * @param serializable
   * @throws IOException
   * @return 队列当中的剩余个数
   */
  public static Long rpushMessage(String key, byte[] value) throws IOException{
    Jedis jedisBorrow = null ;
    try{
      jedisBorrow = getInstance();
      Long l = jedisBorrow.rpush(key.getBytes(CHAR_SET), value);
      return l;
    }finally{
      if(null != jedisBorrow){
        jedisBorrow.close();
      }
    }
  }
	/**
	 * @description 获取列表里面的第一个元素
	 * @param key
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static Object blpop(String key) throws ClassNotFoundException, IOException{
	  Jedis jedisBorrow = null;
	  try{
	    jedisBorrow = getInstance();
	    List<byte[]> blpopList = jedisBorrow.blpop(3000,key.getBytes(CHAR_SET));
	    if(null != blpopList && blpopList.size() == 2){
	      byte[] value = blpopList.get(1);
	      Object obj = SerializationUtil.deSerializeObject(value);
	      return obj;
	    }else{
	      if(null != blpopList){
	        logger.error("数据为空或数据大小不正确={}",blpopList);
	      }
	      return null;
	    } 
	  }finally{
	    if(null != jedisBorrow){	      
	      jedisBorrow.close();
	    }
	  }
	}
	
	public static String blpopMessage(String key) throws ClassNotFoundException, IOException{
	  byte[] value = blpopByteMessage(key);
	  return new String(value, CHAR_SET);
	}
	/**
	 * @description 获取列表里面的第一个元素
	 * @param key
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static byte[] blpopByteMessage(String key) throws ClassNotFoundException, IOException{
	  Jedis jedisBorrow = null;
	  try{
	    jedisBorrow = getInstance();
	    List<byte[]> blpopList = jedisBorrow.blpop(3000,key.getBytes(CHAR_SET));
	    if(null != blpopList && blpopList.size() == 2){
	      byte[] value = blpopList.get(1);
	      return value;
	    }else{
	      if(null != blpopList){
	        logger.error("数据为空或数据大小不正确={}",blpopList);
	      }
	      return null;
	    } 
	  }finally{
	    if(null != jedisBorrow){	      
	      jedisBorrow.close();
	    }
	  }
	}
	
	/**
   * @description 列表添加数据
   * @param key
   * @param serializable
   * @return
   * @throws UnsupportedEncodingException
   * @throws IOException
   */
  public static Long sadd(String key, Serializable serializable) 
      throws UnsupportedEncodingException, IOException{
    Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      return jedisBorrow.sadd(key.getBytes(CHAR_SET), 
          SerializationUtil.serializeObject(serializable));
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
  }
	/**
	 * @description 有序集合添加元素
	 * @param key
	 * @param serializable
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static Long zadd(String key, Serializable serializable) 
	    throws UnsupportedEncodingException, IOException{
	  Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      return jedisBorrow.zadd(key.getBytes(CHAR_SET), 
          Double.valueOf(System.currentTimeMillis()), 
          SerializationUtil.serializeObject(serializable));
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
	}
	/**
	 * @description 有序集合的元素大小
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Long zcard(String key) throws UnsupportedEncodingException{
   Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      return jedisBorrow.zcard(key.getBytes(CHAR_SET));
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
	}
	/**
	 * @description 根据值的排序来删除
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Long zremrangeByRank(String key, long start, long end) throws UnsupportedEncodingException{
   Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      Set<byte[]> zrange = zrange(key, start, end);
      long delete = 0;
      if(null != zrange){
        for(byte[] byArr:zrange){
          byte[] min_max =  new byte[byArr.length+1];
          min_max[0] = 0x5B;//符号[表示>=  
          System.arraycopy(byArr, 0, min_max, 1, byArr.length);
          jedisBorrow.zremrangeByLex(key.getBytes(CHAR_SET), min_max, min_max);
          delete++;
        }
      }
      return delete;
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
	}
	
	/**
   * @description 根据值来删除
   * @param key
   * @param start
   * @param end
   * @return
	 * @throws IOException 
   */
  public static Long zremrangeByLex(String key, Serializable start, Serializable end) 
      throws IOException{
   Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      byte[] startMin = SerializationUtil.serializeObject(start);
      byte[] endMax = SerializationUtil.serializeObject(end);
      byte[] min =  new byte[startMin.length+1];
      byte[] max =  new byte[endMax.length+1];
      min[0] = max[0] = 0x5B;//符号[表示>=  
      System.arraycopy(startMin, 0, min, 1, startMin.length);
      System.arraycopy(endMax, 0, max, 1, endMax.length);
      return jedisBorrow.zremrangeByLex(key.getBytes(CHAR_SET), min, max);
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
  }
 
  /**
   * @description 删除单个分数的数据
   * @param key
   * @param start
   * @param end
   * @return
   * @throws IOException 
   */
  public static Long zremrangeByScore(String key, double data) 
      throws IOException{
   return zremrangeByScore(key, data, data);
  }
  
  /**
   * @description 根据分数删除数据
   * @param key
   * @param start
   * @param end
   * @return
   * @throws IOException 
   */
  public static Long zremrangeByScore(String key, double start, double end) 
      throws IOException{
   Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      return jedisBorrow.zremrangeByScore(key.getBytes(CHAR_SET), start, end);
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
  }
  
	/**
	 * @description 根据排名获取缓存数据
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Set<byte[]> zrange(String key, long start, long end)
	    throws UnsupportedEncodingException{
	  Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      return jedisBorrow.zrange(key.getBytes(CHAR_SET), start, end);
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
	}
	
  /**
   * @description 根据数据对象获取缓存数据
   * @param key
   * @param start
   * @param end
   * @return
   * @throws IOException 
   */
  public static Set<byte[]> zrangeByLex(String key, 
      Serializable start) throws IOException{
    return zrangeByLex(key, start, start, 0, 1);
  }
	 /**
   * @description 根据数据对象获取缓存数据
   * @param key
   * @param start
   * @param end
   * @return
   * @throws IOException 
   */
  public static Set<byte[]> zrangeByLex(String key, 
      Serializable start, Serializable end) throws IOException{
    return zrangeByLex(key, start, end, 0, Integer.MAX_VALUE);
  }
	
	/**
   * @description 根据数据对象获取缓存数据
   * @param key
   * @param start
   * @param end
   * @return
	 * @throws IOException 
   */
  public static Set<byte[]> zrangeByLex(String key, 
      Serializable start, Serializable end,
      int offset, int count)
      throws IOException{
    Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      byte[] startMin = SerializationUtil.serializeObject(start);
      byte[] endMax = SerializationUtil.serializeObject(end);
      byte[] min =  new byte[startMin.length+1];
      byte[] max =  new byte[endMax.length+1];
      min[0] = max[0] = 0x5B;//符号[表示>=  
      System.arraycopy(startMin, 0, min, 1, startMin.length);
      System.arraycopy(endMax, 0, max, 1, endMax.length);
      return jedisBorrow.zrangeByLex(key.getBytes(CHAR_SET), min, max, offset, count);
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
  }
  /**
   * @description 根据分数查询对应区间的数据
   * @param key
   * @return
   * @throws IOException 
   */
  public static Set<byte[]> zrangeByScore(String key) 
      throws UnsupportedEncodingException{
    return zrangeByScore(key, 0.00, Double.MAX_VALUE, 0, Integer.MAX_VALUE);
  }
  
  /**
   * @description 根据分数查询对应区间的数据
   * @param key
   * @param min
   * @return
   * @throws IOException 
   */
  public static Set<byte[]> zrangeByScore(String key,Double min)
      throws UnsupportedEncodingException{
    return zrangeByScore(key, min, min, 0, Integer.MAX_VALUE);
  }
  /**
   * @description 根据分数查询对应区间的数据
   * @param key
   * @param min
   * @param max
   * @return
   * @throws IOException 
   */
  public static Set<byte[]> zrangeByScore(String key,Double min,Double max, int offset, int count)
      throws UnsupportedEncodingException{
    Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      return jedisBorrow.zrangeByScore(key.getBytes(CommCharset.UTF_8.toString()), min, max, offset, count);
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
  }
  
  
  /**
   * @description 根据排名获取缓存数据
   * @param key
   * @param start
   * @param end
   * @return
   * @throws IOException 
   */
  public static Set<Tuple> zrangeWithScores(String key)
      throws IOException{
    return zrangeWithScores(key,0,Long.MAX_VALUE);
  }
  /**
   * @description 根据排名获取缓存数据
   * @param key
   * @param start
   * @param end
   * @return
   * @throws IOException 
   */
  public static Set<Tuple> zrangeWithScores(String key, long start, long end)
      throws IOException{
    Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      byte[] startMin = SerializationUtil.serializeObject(start);
      byte[] endMax = SerializationUtil.serializeObject(end);
      byte[] min =  new byte[startMin.length+1];
      byte[] max =  new byte[endMax.length+1];
      min[0] = max[0] = 0x5B;//符号[表示>=  
      System.arraycopy(startMin, 0, min, 1, startMin.length);
      System.arraycopy(endMax, 0, max, 1, endMax.length);
      return jedisBorrow.zrangeWithScores(key.getBytes(CHAR_SET), start, end);
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
  }
	
	
	/**
	 * @description 获取已缓存的内容
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
  public static <T>T get(String key, Class<T> clz) throws UnsupportedEncodingException{
    String value = get(key);
    if(null != value && !value.trim().isEmpty()){
      return JSON.parseObject(value, clz);
    }
		return null;
	}
	
	 /**
   * @description 获取已缓存的内容
   * @param key
   * @return
	 * @throws UnsupportedEncodingException 
   */
  public static String get(String key) throws UnsupportedEncodingException{
    Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      if(!jedisBorrow.exists(key.getBytes(CHAR_SET))){
        return null;
      }
      String value = jedisBorrow.get(key);
      return value;
    }catch(Exception e){
      logger.error("获取的数据无法转换成目标类型",e);
      return null;
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
  }
  
  /**
   * @description 获取已缓存的内容
   * @param key
   * @return
   * @throws UnsupportedEncodingException 
   */
  public static String get(RedisCacheEnum cacheKey, Object... args) throws UnsupportedEncodingException{
    String key = getKeyByEnum(cacheKey, args);
    return get(key);
  }
  /**
   * @description 获取已缓存的内容
   * @param key
   * @return
   * @throws UnsupportedEncodingException 
   */
  public static <T>T get(RedisCacheEnum cacheKey, Class<T> clz, Object... args) throws UnsupportedEncodingException{
    String key = getKeyByEnum(cacheKey, args);
    return get(key, clz);
  }
	
	/**
	 * @description 保存字符串内容到某个键下(并设置过期时间)
	 * @param key
	 * @param value 
	 * @return
	 */
	public static boolean set(String key, String value){
    Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      if(jedisBorrow.exists(key)){
        logger.info("当前查询结果的信息已存在，暂不覆盖："+key);
        return false;
      }
      jedisBorrow.set(key, value);
//      jedisBorrow.expire(key, 1800);
      return true;
    }finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
	}
	
	 /**
   * @description 覆盖字符串内容到某个键下(并设置过期时间)
   * @param key
   * @param value 
   * @return
   */
  public static boolean directset(String key, String value, int... expried){
    return directset(key, value, -1, new Object[]{});
  }
  /**
   * @description 覆盖字符串内容到某个键下(并设置过期时间)
   * @param key
   * @param value 
   * @return
   */
  public static boolean directset(String key, String value, int expired, Object... args){
    Jedis jedisBorrow = null;
    try{
      jedisBorrow = getInstance();
      jedisBorrow.set(key, value);
      if(expired > 0){
        jedisBorrow.expire(key, expired);
      }
      return true;
    } finally{
      if(null != jedisBorrow){        
        jedisBorrow.close();
      }
    }
  }
  /**
   * @description 覆盖字符串内容到某个键下(并设置过期时间)
   * @param key
   * @param value 
   * @return
   */
  public static boolean directset(RedisCacheEnum cacheKey, String value, Object...args){
      String key = getKeyByEnum(cacheKey, args);
      return directset(key, value, -1, args);  
  }
  /**
   * @description 覆盖字符串内容到某个键下(并设置过期时间)
   * @param cacheKey
   * @param value 
   * @param args
   * @return
   */
  public static boolean directset(RedisCacheEnum cacheKey, String value, int expired){
      return directset(cacheKey.getKey(), value, expired, new Object[]{});  
  }
  /**
   * @description 覆盖字符串内容到某个键下(并设置过期时间)
   * @param key
   * @param value 
   * @return
   */
  public static boolean directset(RedisCacheEnum cacheKey, String value, int expired, Object...args){
      String key = getKeyByEnum(cacheKey, args);
      if(expired <= 0 && cacheKey.getExpired() > 0 ){
        expired = cacheKey.getExpired();
      }
      return directset(key, value, expired, args);  
  }
  /**
  * @description 覆盖字符串内容到某个键下(并设置过期时间)
  * @param key
  * @param value 
  * @return
   * @throws IOException 
   * @throws UnsupportedEncodingException 
  */
 public static boolean set(String key, Serializable object, int... expried) throws Exception{
  Jedis jedisBorrow = null;
  try{
    jedisBorrow = getInstance();
    jedisBorrow.set(key.getBytes(CHAR_SET), SerializationUtil.serializeObject(object));
    jedisBorrow.expire(key.getBytes(CHAR_SET), (null == expried || expried.length == 0)?300:expried[0]);
    return true;
  } finally{
    if(null != jedisBorrow){        
      jedisBorrow.close();
    }
  }
 }
 
 /**
  * @description 覆盖字符串内容到某个键下(并设置过期时间)
  * @param key
  * @param value 
  * @return
   * @throws IOException 
   * @throws UnsupportedEncodingException 
  */
 public static List<Object> setNx(String key, int expried) throws Exception{
  Jedis jedisBorrow = null;
  try{
    jedisBorrow = getInstance();
    jedisBorrow.watch(key.getBytes());
    Transaction multi = jedisBorrow.multi();
    multi.setnx(key.getBytes(), key.getBytes());
    multi.expire(key.getBytes(), expried);
    List<Object> exec = multi.exec();
    jedisBorrow.unwatch();
    return exec;
  } finally{
    if(null != jedisBorrow){        
      jedisBorrow.close();
    }
  }
 }
 
 /**
  * @description 删除缓存中某个键的值
  * @param key
  * @param value 
  * @return
  */
 public static long del(String... keys){
  Jedis jedisBorrow = null;
  try{
    jedisBorrow = getInstance();
    return jedisBorrow.del(keys);
  } finally{
    if(null != jedisBorrow){     
      jedisBorrow.close();
    }
  }
 }
 /**
  * @description 删除缓存中某个键的值
  * @param key
  * @param value 
  * @return
  */
 public static long del(RedisCacheEnum cacheKey, Object...args){
   String key = getKeyByEnum(cacheKey, args);
   return del(key);
 }
 
 private static String getKeyByEnum(RedisCacheEnum cacheKey, Object... args){
   if(null == args || args.length == 0)
     return cacheKey.getKey();
   return MessageFormat.format(cacheKey.getKey(), args);
 }
  
  private static class JedisPoolHolder{
    private static JedisPool pool = initializePool();
  }
  
//  public static boolean isNormalEmpty(String msg){
//    return NO_AVAILABLE_SOURCE.equals(msg);
//  }
}
