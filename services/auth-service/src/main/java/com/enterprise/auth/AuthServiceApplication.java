package com.enterprise.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class AuthServiceApplication {


	public static void main(String[] args) {

        System.setProperty("user.timezone", "Asia/Kolkata");
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));

        SpringApplication.run(AuthServiceApplication.class, args);
	}

}
