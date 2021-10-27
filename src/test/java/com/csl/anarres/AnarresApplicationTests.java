package com.csl.anarres;

import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
//@MapperScan("com.csl.anarres.dao")
class AnarresApplicationTests {
	@Autowired
	private UserService userService;
	@Test
	void contextLoads() {
		UserEntity user = new UserEntity();
		user.setName("1");
		user.setPassword("2");
		user.setCreateTime(new Date());
		user.setLastModifiedTime(new Date());
		userService.find();
		userService.login(user);
	}

}
