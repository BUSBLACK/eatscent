package com.example.eatscent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.eatscent.Dao")
@SpringBootApplication
public class EatscentApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatscentApplication.class, args);
	}

}
