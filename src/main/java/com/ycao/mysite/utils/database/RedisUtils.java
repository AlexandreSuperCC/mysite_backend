package com.ycao.mysite.utils.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * 获取连接池对象
 */
@Component
public class RedisUtils {
    private static JedisPool jedisPool;
    private static Jedis jedis = null;

    /**
     * set jedisPool for this method
     * very important step!!!!!
     * @param
     * @return
     */
    public static void setJedisPool(JedisPool jedisPool) {
        RedisUtils.jedisPool = jedisPool;
    }

    /**
     * 向Redis中存值，永久有效
     */
    public static String set(String key, String value) {
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            return "0";
        } finally {
            jedis.close();
        }
    }

    /**
     * 根据传入Key获取指定Value
     */
    public static String get(String key) {
        String value;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            return "0";
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 校验Key值是否存在
     */
    public static Boolean exists(String key) {
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            return false;
        } finally {
            jedis.close();
        }
    }

    /**
     * 删除指定Key-Value
     */
    public static Long del(String key) {
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            return 0L;
        } finally {
            jedis.close();
        }
    }


    public static void set(String key, String value, Integer time) {
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key,time,value);
        } catch (Exception e) {
            return ;
        } finally {
            jedis.close();
        }

    }
}


