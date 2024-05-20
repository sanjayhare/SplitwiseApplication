package com.splitwise;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


@SpringBootApplication
public class SplitiwiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplitiwiseApplication.class, args);
	}

}
