package com.pi.uc.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.pi.base.dto.result.respcode.error.ErrorServer;
import com.pi.base.dto.result.respcode.user.UcResp;
import com.pi.base.enumerate.redis.RedisCacheEnum;
import com.pi.base.exception.ServiceException;
import com.pi.base.util.cache.RedisUtil;
import com.pi.base.util.lang.StringUtil;
import com.pi.uc.dao.entity.UserEntity;

//需要设置UserSession和自定义会话
public class TokenUtil {
	private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);
	/**
   * @description 设置token信息V2.0.0
   * @description 去除了ip来源并加入了tokenVersion字段
   * {@link ServerConstants#TOKEN_VERSION}
   * @return
	 * @throws Exception 
   */
  public static String getUserToken(UserEntity user) throws Exception{
  	validateUser(user);
    //查询用户是否有会话
  	String storedToken = RedisUtil.get(RedisCacheEnum.USER_LOGIN_TOKEN, new Object[]{user.getId()});
		
  	//暂时只允许一处登陆
  	if(StringUtils.isNotBlank(storedToken)){
  	  RedisUtil.del(RedisCacheEnum.USER_LOGIN_SESSION, storedToken);
  	}
 // 设置新的redis用户信息
  	storedToken = StringUtil.generateNonce(32);;
		RedisUtil.directset(RedisCacheEnum.USER_LOGIN_SESSION,
				JSON.toJSONString(user), getTokenValidity(user.getSourceId()), new Object[]{storedToken});
    RedisUtil.directset(RedisCacheEnum.USER_LOGIN_TOKEN,
        storedToken, getTokenValidity(user.getSourceId()), new Object[]{user.getId()});
    return storedToken;
  }
  /**
   * @description 根据token获取登陆时的会话信息
   * @param token
   * @return 
   */
  public static UserEntity getUserSession(String token){
  	try {
			return RedisUtil.get(RedisCacheEnum.USER_LOGIN_SESSION, UserEntity.class, new Object[]{token});
		} catch (UnsupportedEncodingException e) {
			throw new ServiceException(ErrorServer.REQUEST_UNAVAILABLE.getTag(),
					ErrorServer.REQUEST_UNAVAILABLE.getErrorCode());
		}
  }
  /**
   * @description 根据用户id获取当前的登陆会话标志(这样可以限制做大登陆设备总数与挤账号)
   * @param userId
   * @return 
   */
  public static String getUserToken(long userId){
    try {
      return RedisUtil.get(RedisCacheEnum.USER_LOGIN_TOKEN, new Object[]{userId});
    } catch (UnsupportedEncodingException e) {
      throw new ServiceException(ErrorServer.REQUEST_UNAVAILABLE.getTag(),
          ErrorServer.REQUEST_UNAVAILABLE.getErrorCode());
    }
  }
  
  private static void validateUser(UserEntity user){
  	if(null == user.getId() || user.getId()< 0){
  		throw new ServiceException(
  		    UcResp.RESP_USER_ID_EMPTY.getTag(),
  		    UcResp.RESP_USER_ID_EMPTY.getErrorCode());
  	}
  	if(null == user.getSourceId() || user.getSourceId() < 0){
      throw new ServiceException(
          UcResp.RESP_SOURCE_ID_EMPTY.getTag(),
          UcResp.RESP_SOURCE_ID_EMPTY.getErrorCode());
  	}
  }
  
  /**
   * @description 获取服务器的token时效
   * @param token
   * @return
   */
	public static int getTokenValidity(long partnerCode){
		return -1;
	}
	
	public static UserEntity refreshUserSession(Long userId, String token){
		if(null == userId && null == token){
			logger.error("操作账号或会话为空, 账号：{}， 来源：{}", userId, token);
			return null;
		}
		UserEntity session = getUserSession(token);
		if(null == session){
			throw new ServiceException(ErrorServer.REQUEST_HAS_EXPIRED.getTag(),
			    ErrorServer.REQUEST_HAS_EXPIRED.getErrorCode());
		}
		String userToken = getUserToken(userId);
		if(StringUtils.isBlank(userToken) || userToken.indexOf(token) < 0){
      throw new ServiceException(ErrorServer.REQUEST_HAS_EXPIRED.getTag(),
          ErrorServer.REQUEST_HAS_EXPIRED.getErrorCode());
		}

    RedisUtil.directset(RedisCacheEnum.USER_LOGIN_SESSION,
        JSON.toJSONString(session), getTokenValidity(session.getSourceId()), new Object[]{token});
    RedisUtil.directset(RedisCacheEnum.USER_LOGIN_TOKEN,
        userToken, getTokenValidity(session.getSourceId()), new Object[]{userId});   
		return session;
	}
}
