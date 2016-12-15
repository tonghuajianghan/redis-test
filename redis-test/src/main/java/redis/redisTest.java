package redis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class redisTest {

	@Test
	public void test() {
		Jedis jedis = new Jedis("localhost");
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
	}

	@Ignore
	@Test
	public void RedisList() {
		// 连接本地的 Redis 服务
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server sucessfully");
		// 存储数据到列表中
		jedis.lpush("tutorial-list", "Redis");
		jedis.lpush("tutorial-list", "Mongodb");
		jedis.lpush("tutorial-list", "Mysql");
		// 获取存储的数据并输出
		List<String> list = jedis.lrange("tutorial-list", 0, 5);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Stored string in redis:: " + list.get(i));
		}
	}

	@Test
	public void RedisKeys() {
		// 连接本地的 Redis 服务
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server sucessfully");

		// 获取数据并输出
		Set<String> list = jedis.keys("*");
		Iterator i = list.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
	}
}
