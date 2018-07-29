package Redis;

import redis.clients.jedis.Jedis;

import java.util.ResourceBundle;

/**
 * Created by lixiangpeng on 2018/7/21.
 */
public class RedisDB {
//    private static JedisPool jedisPool;
    private static String addr;
    private static int port;
    private static String passwd;

    //加载配置文件
    private static ResourceBundle rb = ResourceBundle.getBundle("Resid-config");

    //初始化连接
    static {
        addr = rb.getString("jedis.addr");
        port = Integer.parseInt(rb.getString("jedis.port"));
        passwd = rb.getString("jedis.passwd");
    }

    //获取Jedis实例
    public synchronized static Jedis getJedis() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis(addr, port);

        //权限认证,设置密码时开启
        //jedis.auth(passwd);

        return jedis;
    }

    //释放Jedis资源
    public static void close(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}