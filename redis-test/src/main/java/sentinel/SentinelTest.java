package sentinel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class SentinelTest {

	@Test
	public void testSentinel() {
		Set<String> sentinels = new HashSet<String>();
		// 添加监控节点
		sentinels.add("192.168.184.1:26379");
		// 创建监控节点池,其中masterName要与sentinel中配置的masterName一致
		JedisSentinelPool pool = new JedisSentinelPool("master", sentinels);

		Jedis jedis = pool.getResource();
		jedis.set("jedis", "jedis");
		pool.returnResource(jedis);
	}

	@Test
	public void test2() {
		Set<String> sentinels = new HashSet<String>();
		// 添加监控节点
		sentinels.add("192.168.184.1:26379");
		// 创建监控节点池,其中masterName要与sentinel中配置的masterName一致
		JedisSentinelPool pool = new JedisSentinelPool("master", sentinels);

		System.out.println("master-----" + pool.getCurrentHostMaster());
		System.out.println("active-----" + pool.getNumActive());
		System.out.println("id---------" + pool.getNumIdle());

		pool.destroy();
	}
	
	@Test
	public void testlist(){
		Set<String> sentinels = new HashSet<String>();
		// 添加监控节点
		sentinels.add("192.168.184.1:26379");
		// 创建监控节点池,其中masterName要与sentinel中配置的masterName一致
		JedisSentinelPool pool = new JedisSentinelPool("master", sentinels);
		Jedis jedis = pool.getResource();
		//存储数据到列表中
		jedis.lpush("jh-lisr", "jh1");//可以多次的插入重复的数据,因为是list
		jedis.lpush("jh-lisr", "jh12");
		jedis.lpush("jh-lisr", "jh123");
		
		 // 获取存储的数据并输出
	     List<String> list = jedis.lrange("jh-lisr", 0 ,5);
	     for(int i=0; i<list.size(); i++) {
	       System.out.println("jh-lisr string in redis:: "+list.get(i));
	     }
	}
}
