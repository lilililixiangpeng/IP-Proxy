package Redis;

import Model.IPMessage;
import Model.SerializeUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

import static java.lang.System.out;

/**
 * Created by lixiangpeng on 2018/7/21.
 */
public class MyRedis {
    Jedis jedis = RedisDB.getJedis();

    //将ip信息保存在Redis列表中
    public void setIPToList(List<IPMessage> ipMessages) {
        for (IPMessage ipMessage : ipMessages) {
            //首先将ipMessage进行序列化
            String ip = SerializeUtil.serialize(ipMessage);

            jedis.rpush("IPPool", ip);
        }
    }

    //将Redis中保存的对象进行反序列化
    public IPMessage getIPByList() {
        //随机抽取一个代理实例
        int rand = (int)(Math.random()*jedis.llen("IPPool"));

        Object o = SerializeUtil.unserialize(jedis.lindex("IPPool", rand));
        if (o instanceof IPMessage) {
            return (IPMessage)o;
        } else {
            out.println("不是IPMessage的实例");
            return null;
        }
    }

    public void deleteKey(String key) {
        jedis.del(key);
    }

    public void close() {
        RedisDB.close(jedis);
    }
}
