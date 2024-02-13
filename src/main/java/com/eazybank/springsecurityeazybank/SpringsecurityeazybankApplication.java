package com.eazybank.springsecurityeazybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.eazybank.springsecurityeazybank.controller")//Optional - used only when we want any particular to be scanned
public class SpringsecurityeazybankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityeazybankApplication.class, args);
	}
}
