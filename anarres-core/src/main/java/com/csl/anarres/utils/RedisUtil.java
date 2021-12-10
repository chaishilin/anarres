package com.csl.anarres.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.commands.JedisCommands;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/28 15:13
 * @Description:
 */

public class RedisUtil implements InvocationHandler {
    private static final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    private static final JedisPool jedisPool = new JedisPool(jedisPoolConfig,"127.0.0.1");;

    private RedisUtil(){
        //只允许getProxyInstance调用
    }

    /**
     * 返回原生jedis对象，需要手动关闭
     * @return
     */
    public static Jedis getConsistentInstance(){
        return  jedisPool.getResource();
    }

    /**
     * 返回可自动关闭的jedis对象
     * @return
     */
    public static JedisCommands getInstance(){
        //创建jedisCommands的接口
        return (JedisCommands)Proxy.newProxyInstance(JedisCommands.class.getClassLoader(),new Class[]{JedisCommands.class}, new RedisUtil());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable{
        try (Jedis jedis = jedisPool.getResource()){
            //try-with-resource autoclose jedis resource
            //参考：https://stackoverflow.com/questions/33400338/java-proxy-for-autocloseable-jedis-resources
            return method.invoke(jedis,args);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
