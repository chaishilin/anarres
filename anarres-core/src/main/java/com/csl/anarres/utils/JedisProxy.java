package com.csl.anarres.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.commands.JedisCommands;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/1 9:21
 * @Description:
 */
//@Component
class JedisProxy implements InvocationHandler {

    private final JedisPool pool;

    public JedisProxy(JedisPool pool) {
        this.pool = pool;
    }

    public static JedisCommands newInstance(JedisPool pool) {
        return (JedisCommands) java.lang.reflect.Proxy.newProxyInstance(
                JedisCommands.class.getClassLoader(),
                new Class[] { JedisCommands.class },
                new JedisProxy(pool));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try (Jedis client = pool.getResource()) {
            return method.invoke(client, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw e;
        }
    }
}