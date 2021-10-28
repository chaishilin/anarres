package com.csl.anarres.utils;

import redis.clients.jedis.Jedis;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/28 15:13
 * @Description:
 */
public class RedisUtil {
    private static Jedis jedis;
    private RedisUtil(){

    }

    /**
     * 单例模式获得redis连接
     * @return
     */
    public static Jedis getInstance(){
        if(jedis == null){
            jedis = new Jedis("http://localhost:6379");
        }
        return jedis;
    }
}
