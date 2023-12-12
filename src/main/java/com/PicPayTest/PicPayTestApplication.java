package com.PicPayTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PicPayTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicPayTestApplication.class, args);
		System.out.println("Server is running on port 8080");
	}

}
