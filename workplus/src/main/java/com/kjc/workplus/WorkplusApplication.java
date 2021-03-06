package com.kjc.workplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // (= @Configuration + @EnableAutoConfiguration + @ComponentScan)
public class WorkplusApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkplusApplication.class, args);
	}

}
