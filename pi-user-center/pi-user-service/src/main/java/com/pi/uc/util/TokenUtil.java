package com.pi.uc.util;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

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

public class TokenUtil {
	private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);
	/**
   * @description 设置token信息V2.0.0
   * @description 去除了ip来源并加入了tokenVersion字段
   * {@link ServerConstants#TOKEN_VERSION}
   * @return
   */
  public static String getUserToken(UserEntity user){
  	validateUser(user);

//	设置redis的用户信息		
  	String token = StringUtil.generateNonce(32);
		RedisUtil.directset(RedisCacheEnum.USER_LOGIN_SESSION,
				JSON.toJSONString(user), getTokenValidity(user.getSourceId()), new Object[]{token});
    RedisUtil.directset(RedisCacheEnum.USER_LOGIN_TOKEN,
        JSON.toJSONString(user), getTokenValidity(user.getSourceId()), new Object[]{user.getId()});
    return token;
  }
  
  public static UserEntity getUserSession(String account,String partnerCode){
  	try {
			return RedisUtil.get(RedisCacheEnum.USER_LOGIN_TOKEN, UserEntity.class);
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
  
  private static String getSessionKey(String token){		
		return MessageFormat.format(RedisCacheEnum.USER_LOGIN_TOKEN.getKey(), token);
  }
  private static String getTokenKey(String userId){    
    return MessageFormat.format(RedisCacheEnum.USER_LOGIN_SESSION.getKey(), userId);
  }
  
  /**
   * @description token的额外校验(下个版本完善)
   * @param token
   * @return
   */
	public static boolean validate(String token) {
		return true;
	}
	
  /**
   * @description 获取服务器的token时效
   * @param token
   * @return
   */
	public static int getTokenValidity(long partnerCode){
		return -1;
	}
	
	public static UserSession refreshUserSession(String account, Integer partnerCode){
		if(null == account && null == partnerCode){
			logger.error("操作账号或来源为空, 账号：{}， 来源：{}", account, partnerCode);
			return null;
		}
		UserSession session = getUserSession(account, String.valueOf(partnerCode));
		if(null == session){
			throw new ServiceException(ErrorUser.TOKEN_EXPIRED.getTag(),
					ErrorUser.TOKEN_EXPIRED.getErrorCode());
		}
		session.setLastActive(System.currentTimeMillis()/1000);
		RedisUtil.directset(
				getSessionKey(String.valueOf(partnerCode), account), 
				JSON.toJSONString(session), getTokenValidity(session.getPartnerCode()));
		return session;
	}
}
