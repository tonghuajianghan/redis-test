package redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * Created by 17020751 on 2018/1/23.
 * <p>
 * 被 WATCH 的键会被监视，并会发觉这些键是否被改动过了。
 * 如果有至少一个被监视的键在 EXEC 执行之前被修改了，
 * 那么整个事务都会被取消，
 * EXEC 返回空多条批量回复（null multi-bulk reply）来表示事务已经失败。
 *
 * WATCH 使得 EXEC 命令需要有条件地执行：
 * 事务只能在所有被监视键都没有被修改的前提下执行， 如果这个前提不能满足的话，事务就不会被执行。
 *
 * 监控时间 watch----exec
 */
public class WatchTest {


    /*WATCH mykey

    val = GET mykey
    val = val + 1

    MULTI
    SET mykey $val
    EXEC*/

    @Test
    public void test() throws InterruptedException {
        Jedis jedis = new Jedis(RedisConstants.localhost);
        jedis.set("yw","1");

        jedis.watch("yw");

        String yww = jedis.get("yw") + "11";

        Transaction tx = jedis.multi();
        Thread.sleep(3000l);
        tx.set("yw",yww);
        //jedis.set("yw",yww);
        List<Object> l = tx.exec();
        System.out.println(l.size());
        for(Object o : l){
            System.out.println(o.toString());
        }
        System.out.println(jedis.get("yw"));
    }

    @Test
    public void test2() {
        Jedis jedis = new Jedis(RedisConstants.localhost);
        jedis.set("yw","1");
    }

    @Test
    public void test3() throws InterruptedException {
        Jedis jedis = new Jedis(RedisConstants.localhost);
        jedis.set("yw","1");
        jedis.expire("yw",1);
        Thread.sleep(2000l);
        System.out.println(jedis.get("yw"));
    }
}
