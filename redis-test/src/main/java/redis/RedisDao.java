package redis;

import java.util.List;


public interface RedisDao<E> {

	public abstract E get(String key);

	public abstract void removePattern(String pattern);

	public abstract void remove(String... keys);

	public abstract void remove(String key);

	public abstract boolean hasKey(String key);

	public abstract boolean set(String key, Object value, Long expireTime);

	public abstract boolean set(String key, Object value);

	public abstract List<E> getList(String key);

}
