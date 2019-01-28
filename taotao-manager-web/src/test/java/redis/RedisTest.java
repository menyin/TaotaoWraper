package redis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

public class RedisTest {
    //单机
    @Test
    public void testSingleClient(){
        Jedis jedis = new Jedis("192.168.1.236", 7001);
        String aa = jedis.get("aa");
        System.out.println(aa);
        jedis.close();
    }
    //集群
    @Test
    public void testColonyClient(){
        HashSet<HostAndPort> hostAndPorts= new HashSet<>();
        hostAndPorts.add(new HostAndPort("192.168.1.236", 7001));
        hostAndPorts.add(new HostAndPort("192.168.1.236", 7002));
        hostAndPorts.add(new HostAndPort("192.168.1.236", 7003));
        hostAndPorts.add(new HostAndPort("192.168.1.236", 7004));
        hostAndPorts.add(new HostAndPort("192.168.1.236", 7005));
        hostAndPorts.add(new HostAndPort("192.168.1.236", 7006));
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
        String aa = jedisCluster.get("ff");

        System.out.println(aa);

    }

}
