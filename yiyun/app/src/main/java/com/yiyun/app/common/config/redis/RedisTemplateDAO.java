package com.yiyun.app.common.config.redis;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class RedisTemplateDAO {
    private static final Logger logger = LogManager.getLogger(RedisTemplateDAO.class.getName());

	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;

	@Autowired
	protected StringRedisTemplate stringRedisTemplate;

	/**
	 * 设置redisTemplate
	 *
	 * @param redisTemplate the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 获取 RedisSerializer
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	//======================== 新增缓存 begin ========================//

	/**
	 * 新增字符串缓存 , 可以设置数据库和超时时间
	 *
	 * @param
	 * @return
	 */
	public boolean put(final String key, final String value , final Integer dbIndex , final Long timeOut) {
		try {
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

					if(dbIndex != null){
						connection.select(dbIndex);//切换数据库
					}

					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] keyByte = serializer.serialize(key);
					connection.set(keyByte, serializer.serialize(value));

					if(timeOut != null && timeOut > 0){
						connection.expire(keyByte, timeOut);
					}
					return true;
				}
			});
			return result;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return false;
		}
	}
	/**
	 * 新增字符串缓存 , 可以设置超时时间
	 *
	 * @param
	 * @return
	 */
	public boolean put(final String key, final String value , final Long timeOut) {
		try {
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] keyByte = serializer.serialize(key);
					connection.set(keyByte, serializer.serialize(value));

					if(timeOut != null && timeOut > 0){
						connection.expire(keyByte, timeOut);
					}
					return true;
				}
			});
			return result;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return false;
		}
	}
	/**
	 * put重载 , 在当前库新增字符串 , 不设置超时时间
	 *
	 * @param
	 * @return
	 */
	public boolean put(final String key, final String value) {
		return put(key , value , null , null);
	}

	/**
	 * put重载 , 在当指定库新增字符串 , 不设置超时时间
	 *
	 * @param
	 * @return
	 */
	public boolean put(final String key, final String value , final Integer dbIdx) {
		return put(key , value , dbIdx , null);
	}

	/**
	 * 新增对象
	 *
	 * @param
	 * @return
	 */
	public boolean putObj(final String key, final Object value) {
		try {
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
					connection.set(serializer.serialize(key), serializer.serialize(value));
					return true;
				}
			});
			return result;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return false;
		}
	}

	/**
	 * 批量新增 使用pipeline方式 <br>
	 * ------------------------------<br>
	 *
	 * @param list
	 * @return
	 */
	/*
	 * public boolean add(final List<V> list) { boolean result =
	 * redisTemplate.execute(new RedisCallback<Boolean>() { public Boolean
	 * doInRedis(RedisConnection connection) throws DataAccessException {
	 * RedisSerializer<String> serializer = getRedisSerializer(); for (V v :
	 * list) { byte[] key = serializer.serialize(""); byte[] name =
	 * serializer.serialize(""); connection.setNX(key, name); } return true; }
	 * }, false, true); return result; }
	 */

	//======================== 新增缓存 end ========================//


	//======================== 获取缓存 begin ========================//

	/**
	 * 通过key获取 , 指定数据库索引
	 *
	 * @param keyId
	 * @return
	 */
	public String get(final String keyId , final Integer dbIdx) {
		try {
			String result = redisTemplate.execute(new RedisCallback<String>() {
				public String doInRedis(RedisConnection connection) throws DataAccessException {

					if(dbIdx != null){
						connection.select(dbIdx);
					}

					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(keyId);
					byte[] value = connection.get(key);
					if (value == null) {
						return null;
					}
					String strValue = serializer.deserialize(value);
					return strValue;
				}
			});
			return result;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return null;
		}
	}

	/**
	 * get重载 , 通过key获取 , 在当前库
	 *
	 * @param keyId
	 * @return
	 */
	public String get(final String keyId) {
		return get(keyId, null);
	}

	/**
	 * 获取对象
	 *
	 * @param
	 * @return
	 */
	public Object getObject(final String keyId) {
		try {
			Object result = redisTemplate.execute(new RedisCallback<String>() {
				@SuppressWarnings({ "unused", "rawtypes" })
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(keyId);
					byte[] value = connection.get(key);
					if (value == null) {
						return null;
					}
					RedisSerializer objSerializer = getRedisSerializer();
					return serializer.deserialize(value);
				}
			});
			return result;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return null;
		}
	}

	/**
	 * @Description 通过正则来获取key和value (消耗内存 , 慎用)
	 * @return
	 * @author wangzy
	 * @createDate 2016年5月19日 下午3:45:17
	 * @modifier
	 * @modifyDate
	 * @version 1.0
	 */
	public Set<String> getKeys(String pattern) {
		Set<String> keySet = stringRedisTemplate.keys(pattern);
		return keySet;
	}

	//======================== 获取缓存 end ========================//


	//======================== 删除缓存 begin ========================//

	/**
	 * 删除 , 指定库
	 *
	 * @param key
	 */
	public void delete(final String key , final Integer dbIdx) {
		try {
			redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) {
					if(dbIdx != null){
						connection.select(dbIdx);
					}
					connection.del(redisTemplate.getStringSerializer().serialize(key));
					return null;
				}
			});
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
		}
	}

	/**
	 * delete重载 , 删除 , 当前库
	 *
	 * @param key
	 */
	public void delete(final String key) {
		delete(key, null);
	}

	/**
	 * 删除多个 <br>
	 * ------------------------------<br>
	 *
	 * @param keys
	 */
	public void delete(List<String> keys) {
		try {
			redisTemplate.delete(keys);
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
		}
	}

	//======================== 删除缓存 end ========================//


	//======================== 修改缓存 begin ========================//

	/**
	 * 修改 , 指定库和重新设置超时时间
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean update(final String key, final String value , final Integer dbIdx , final Long timeout) {
		try {
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					if(dbIdx != null){
						connection.select(dbIdx);
					}

					connection.del(redisTemplate.getStringSerializer().serialize(key));
					RedisSerializer<String> serializer = getRedisSerializer();
					 connection.setNX(serializer.serialize(key), serializer.serialize(value));


					 if(timeout != null && timeout > 0){
						connection.expire(serializer.serialize(key), timeout);
					 }
					 return true;
				}
			});
			return result;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return false;
		}
	}

	/**
	 * update重载 , 修改 , 指定库 , 不设置超时时间
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean update(final String key, final String value , final Integer dbIdx) {
		return update(key, value, dbIdx, null);
	}

	/**
	 * update重载 , 修改 , 当前库, 不设置超时时间
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean update(final String key, final String value) {
		return update(key, value, null, null);
	}

	/**
	 * 修改缓存时不再重新设置超时时间(以剩余时间继续计算) , 可以指定库
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean updateIgnoreTime(final String key, final String value , final Integer dbIdx) {
		try {
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					if(dbIdx != null){
						connection.select(dbIdx);
					}

					final byte[] KEY = redisTemplate.getStringSerializer().serialize(key);

					long timeout = connection.ttl(KEY);//取出剩余的超时时间

					connection.del(KEY);
					RedisSerializer<String> serializer = getRedisSerializer();
					 connection.setNX(serializer.serialize(key), serializer.serialize(value));

					 if(timeout > 0){//继续设置剩下的时间
						connection.expire(serializer.serialize(key), timeout);
					 }
					 return true;
				}
			});
			return result;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return false;
		}
	}

	/**
	 * updateIgnoreTime重载 , 修改缓存时不再重新设置超时时间(以剩余时间继续计算) , 当前库
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean updateIgnoreTime(final String key, final String value) {
		return updateIgnoreTime(key, value, null);
	}

	//======================== 修改缓存 end ========================//

	//======================== 超时时间 begin ========================//

	/**
	 * 设置过期时间 , 可以指定库
	 *
	 * @param key
	 * @param timeout(单位为秒)
	 * @return
	 */
	public boolean expire(final String key, final Integer dbIdx , final Long timeout) {
		try {
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

					if(dbIdx != null){
						connection.select(dbIdx);
					}

					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] byteKey = serializer.serialize(key);
					return connection.expire(byteKey, timeout);
				}
			});
			return result;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return false;
		}
	}

	/**
	 * expire重载 , 设置过期时间 , 当前库
	 *
	 * @param key
	 * @param timeout(单位为秒)
	 * @return
	 */
	public boolean expire(final String key , final Long timeout) {
		return expire(key,null , timeout);
	}

	/**
	 * 获取过期时间 , 可以指定库
	 *
	 * @param key
	 * @param timeout(单位为秒)
	 * @return
	 */
	public Long getOutTime(final String key, final Integer dbIdx , final Long timeout) {
		try {
			Object result = redisTemplate.execute(new RedisCallback<String>() {
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					if(dbIdx != null){
						connection.select(dbIdx);
					}

					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] byteKey = serializer.serialize(key);
					Long timeout = connection.ttl(byteKey);//取出剩余的超时时间
					return String.valueOf(timeout);
				}
			});
			return result == null ? null : (Long)result;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return null;
		}
	}

	/**
	 * getOutTime重载 , 获取过期时间 , 当前库
	 *
	 * @param key
	 * @param timeout(单位为秒)
	 * @return
	 */
	public Long getOutTime(final String key , final Long timeout) {
		return getOutTime(key, null, timeout);
	}

	//======================== 超时时间 end ========================//

	//======================== 其他非常规操作 ========================//
	/**
	 * 获取队列大小
	 *
	 * @param key
	 * @return
	 */
	public long getListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 从队列右边入队列
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public void rPush(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
		}
	}

	/**
	 * 从队列左边出队列,批量
	 *
	 * @param key
	 * @param
	 * @return
	 */
	public List<Object> rPushBatch(final String key, final List<Object> valueList) {
		try {
			List<Object> results = redisTemplate.executePipelined(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					for (int i = 0; i < valueList.size(); i++) {
						StringRedisSerializer serializer = new StringRedisSerializer();
						JdkSerializationRedisSerializer valueSerializer = new JdkSerializationRedisSerializer();
						connection.rPush(serializer.serialize(key), valueSerializer.serialize(valueList.get(i)));
					}
					return null;
				}
			});
			return results;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return null;
		}
	}

	/**
	 * 从队列左边出队列,批量
	 *
	 * @param key
	 * @param batchSize
	 * @return
	 */
	public List<Object> lPop(final String key, final int batchSize) {
		try {
			List<Object> results = redisTemplate.executePipelined(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					// StringRedisConnection stringRedisConn =
					// (StringRedisConnection)connection;
					for (int i = 0; i < batchSize; i++) {
						// stringRedisConn.lPop(key);
						StringRedisSerializer serializer = new StringRedisSerializer();
						connection.lPop(serializer.serialize(key));
					}
					return null;
				}
			});
			return results;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return null;
		}
	}

	/**
	 * 从队列左边出队列,批量
	 *
	 * @param key
	 * @param batchSize
	 * @return
	 */
	public List<Object> blockLeftPop(final String key, final int batchSize) {
		try {
			List<Object> results = redisTemplate.executePipelined(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					for (int i = 0; i < batchSize; i++) {
						StringRedisSerializer serializer = new StringRedisSerializer();
						connection.bLPop(0, serializer.serialize(key));
					}
					return null;
				}
			});
			return results;
		} catch (RedisConnectionFailureException e) {
			logger.error("Redis连接异常", e);
			return null;
		}
	}
}