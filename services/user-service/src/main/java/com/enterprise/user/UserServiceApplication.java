package com.enterprise.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {

        System.setProperty("user.timezone", "Asia/Kolkata");
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(UserServiceApplication.class, args);
	}

}
