package com.csl.anarres;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.csl.anarres.mapper")
public class AnarresApplication {
	//todo 想着能不能填表格自动生成数据库中的表
	public static void main(String[] args) {
		SpringApplication.run(AnarresApplication.class, args);
	}

}
