package redis;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * redis 数据入口类
 *
 * @author JiangHan
 * @param <E>
 */
@SuppressWarnings({ "unchecked", "restriction" })
@Component
public class RedisDaoImpl<E> implements RedisDao<E> {
	private Logger log = LoggerFactory.getLogger(RedisDaoImpl.class);

	@Resource
	private RedisTemplate<Serializable, Object> redisTemplate;

	/**
	 * 读取缓存
	 * 
	 * @Author JiangHan
	 * @param key redis键值
	 * @return
	 */
	@Override
	public E get(String key) {
		log.info("获取redis缓存数据,key值:{}", key);
		E result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = (E) operations.get(key);
		return result;
	}

	/**
	 * 读取缓存(返回集合)
	 *
	 * @Author JiangHan
	 * @param key
	 * @return
	 */
	@Override
	public List<E> getList(String key) {
		log.info("获取redis缓存list数据,key值:{}", key);
		List<E> result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = (List<E>) operations.get(key);
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @Author JiangHan
	 * @param key redis键值
	 * @param value
	 * @return
	 */
	@Override
	public boolean set(String key, Object value) {
		log.info("写入redis缓存数据,key值:{},value值:{}", key, value);
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			log.info("写入redis缓存出错,key值:{}", key);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @Author JiangHan
	 * @param key
	 * @param value
	 * @param expireTime 到期时间
	 * @return
	 */
	@Override
	public boolean set(String key, Object value, Long expireTime) {
		log.info("写入redis缓存数据,key值:{},value值:{}", key, value);
		log.info("到期时间,expireTime值:{}", expireTime);
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			log.info("写入redis缓存出错,key值:{}", key);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @Author JiangHan
	 * @param key
	 * @return
	 */
	@Override
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	@Override
	public void remove(String key) {
		log.info("删除redis缓存数据,key值:{}", key);
		if (hasKey(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	@Override
	public void remove(String... keys) {
		for (String key : keys) {
			log.info("删除redis缓存数据,keys值:{}", key);
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 * 
	 * @param pattern key值匹配字符串
	 */
	@Override
	public void removePattern(String pattern) {
		log.info("删除redis缓存数据,匹配pattern值:{}", pattern);
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

}
