package redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class redisTest {

	@Test
	public void test() {
		Jedis jedis = new Jedis(RedisConstants.localhost);//默认端口
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
		System.out.println("------------------------------");
	}

//	@Ignore
	@Test
	public void RedisList() {
		// 连接本地的 Redis 服务
		Jedis jedis = new Jedis(RedisConstants.localhost);//默认端口
		//Jedis jedis = new Jedis(host, port);
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
		Jedis jedis = new Jedis(RedisConstants.localhost);
		System.out.println("Connection to server sucessfully");

		// 获取数据并输出
		Set<String> list = jedis.keys("*");
		Iterator i = list.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
		System.out.println("------------------------------");
	}
	
	//=========================================String =========================================================
	@Test
	public void redisAppend(){
		//原有value基础上拼接,而不是覆盖
		Jedis jedis = new Jedis(RedisConstants.localhost);
		jedis.append("wh", "wh");
		System.out.println(jedis.get("wh"));
		System.out.println("------------------------------");
	}
	
	@Test
	public void redisStringMset(){
		//原有value基础上拼接,而不是覆盖
		Jedis jedis = new Jedis(RedisConstants.localhost);
		jedis.mset("key1","value1","key2","value2","age","23");
		jedis.incr("age");//进行加1操作
		System.out.println("key1:"+jedis.get("key1")+"|key2:"+jedis.get("key2")+"|age:"+jedis.get("age"));
		System.out.println("------------------------------");
	}
}
