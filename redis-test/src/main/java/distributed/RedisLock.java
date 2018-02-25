package distributed;

import org.junit.Test;
import redis.RedisConstants;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Created by 17020751 on 2018/2/25.
 */
public class RedisLock {

    public static boolean acquireLock(Jedis jedis,String lockName,Long acquireTime,String identifier){
        long start = System.currentTimeMillis();
        long end = start + acquireTime;//尝试时间段
        while (start<end){
            String result = jedis.set("lock:"+lockName,identifier,"nx","ex",10);//尝试获取锁
            if(result.equals("OK")){
                return true;
            }
        }
        return false;
    }

    public static boolean releaseLock(Jedis jedis,String lockName,String identifier){
        lockName = "lock:" + lockName;
        while (true){//如果执行时间大于expire时间,则释放锁的时候为空值,会一直catch错误,无限循环
            try {
                jedis.watch(lockName);
                if(jedis.get(lockName).equals(identifier)){//检查进程是否仍然持有锁
                    Transaction t = jedis.multi();
                    t.del(lockName);
                    t.exec();
                    return true;
                }
                jedis.unwatch();
                break;
            }catch (JedisException e){
                continue;
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }

    private void doLock(int i) {
        Jedis jedis = new Jedis(RedisConstants.localhost);//默认端口
        String identifier = String.valueOf(i);//UUID.randomUUID().toString();

        boolean isLock = acquireLock(jedis,"amountLock",10000l,identifier);
        if (isLock == false){
            System.out.println("获取锁未成功:"+ Thread.currentThread().getName());
        }
        System.out.println(jedis.get("lock:amountLock"));
        try {
            Long l = jedis.decr("amount");
            System.out.println(l);
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            releaseLock(jedis,"amountLock",identifier);
        }
    }

    @Test
    public void testLock(){
        Jedis jedis = new Jedis(RedisConstants.localhost);//默认端口
        jedis.set("amount","500");
        for (int i = 1; i < 10; i++) {
            doLock(i);
        }
    }
}
