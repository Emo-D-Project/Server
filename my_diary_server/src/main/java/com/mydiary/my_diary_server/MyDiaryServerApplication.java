package com.mydiary.my_diary_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class MyDiaryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyDiaryServerApplication.class, args);
	}

}
