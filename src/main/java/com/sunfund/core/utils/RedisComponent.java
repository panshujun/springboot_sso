package com.sunfund.core.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description 操作Redis
 * @author sl
 * @date 2015年3月9日
 */
@Component
public class RedisComponent {

	private static final Logger logger = Logger.getLogger(RedisComponent.class);

	@Autowired
	public StringRedisTemplate redisTemplate;
	

	/**
	 * 不需要设置过期时间的参数
	 * 
	 * @param key
	 * @param vlaue
	 * @throws Exception
	 */
	public void set(String key, String vlaue) throws Exception {
		try {
			redisTemplate.opsForValue().set(key, vlaue);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}

	/**
	 * 需要设置过期时间的参数
	 * 
	 * @param key
	 * @param vlaue
	 * @param expireTime
	 *            过期时间（默认单位为秒）
	 * @throws Exception
	 */
	public void set(String key, String vlaue, long expireTime) throws Exception {
		try {
			this.set(key, vlaue, expireTime, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}

	/**
	 * 需要设置过期时间的参数和时间单位
	 * 
	 * @param key
	 * @param vlaue
	 * @param expireTime
	 *            过期时间
	 * @param unit
	 *            时间单位
	 * @throws Exception
	 */
	public void set(String key, String vlaue, long expireTime, TimeUnit unit)
			throws Exception {
		try {
			redisTemplate.opsForValue().set(key, vlaue, expireTime, unit);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}

	/**
	 * 根据key获取数据
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String get(String key) throws Exception {
		try {
			return redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}
	/**
	 * 模糊查找key列表
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) throws Exception{
		try {
			return redisTemplate.keys(pattern);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}
	/**
	 * 根据key删除数据
	 * 
	 * @param key
	 * @throws Exception
	 */
	public void delete(String key) throws Exception {
		try {
			redisTemplate.delete(key);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}
	/**
	 * 根据key集合删除数据
	 * @param keys
	 */
	public void delete(Collection<String> keys)throws Exception{
		try {
			redisTemplate.delete(keys);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}
	/**
	 * 通过key获取过期时间
	 * 
	 * @param key
	 * @return
	 */
	public long getExpireTime(String key) throws Exception{
		try {
			return redisTemplate.opsForValue().getOperations().getExpire(key);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}

	/**
	 * 根据key自增value（默认+1）
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public long increment(String key) throws Exception {
		try {
			return this.increment(key, 1L);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}

	/**
	 * 根据key自增value
	 * 
	 * @param key
	 * @param inc
	 * @return
	 * @throws Exception
	 */
	public long increment(String key, Long inc) throws Exception {
		try {
			return redisTemplate.opsForValue().increment(key, inc);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}

	/**
	 * 需要设置过期时间的参数和时间单位(Set集合存储)
	 * 
	 * @param key
	 * @param vlaue
	 * @param expireTime
	 *            过期时间
	 * @param unit
	 *            时间单位
	 * @throws Exception
	 */
	public void setCollection(String key, String vlaue, long expireTime,
			TimeUnit unit) throws Exception {
		try {
			redisTemplate.opsForSet().add(key, vlaue);
			redisTemplate.expire(key, expireTime, unit);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param expireTime
	 * @param unit
	 */
	public void setExpire(String key, long expireTime, TimeUnit unit) throws Exception{
		try {
			redisTemplate.expire(key, expireTime, unit);
		} catch (Exception e) {
			logger.error("访问缓存服务异常！", e);
			throw e;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String lpop(String key){
		
		return this.redisTemplate.opsForList().leftPop(key);
		
	}
	

}
