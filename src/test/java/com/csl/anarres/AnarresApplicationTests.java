package com.csl.anarres;

import com.csl.anarres.service.UserService;
import com.csl.anarres.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
class AnarresApplicationTests {
	@Autowired
	private UserService userService;
	@Test
	void contextLoads() {
		Jedis jedis= RedisUtil.getInstance();
		Jedis jedis2= RedisUtil.getInstance();
		Jedis jedis3= RedisUtil.getInstance();
		System.out.println(jedis.equals(jedis2));
		System.out.println(jedis2.equals(jedis3));
		System.out.println(jedis.get("343434"));
	}

}
