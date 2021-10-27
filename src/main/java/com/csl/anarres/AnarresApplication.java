package com.csl.anarres;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.csl.anarres.mapper")
public class AnarresApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnarresApplication.class, args);
	}

}
