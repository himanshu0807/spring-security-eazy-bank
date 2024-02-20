package com.eazybank.springsecurityeazybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
// @ComponentScan("com.eazybank.springsecurityeazybank.controller")//Optional - used only when we want any particular to be scanned
//just in case of we have our jps repo and entity outside the base package
//@EnableJpaRepositories("com.eazybank.springsecurityeazybank.repository")
//@EntityScan("com.eazybank.springsecurityeazybank.model")
//Need the annotation to enable the spring security if we are using the spring, in spring-boot it is enabled automatically based on the classpath dependencies we have
// Also to enable the debugging of spring security which can help in debugging the filter flow, not for prod
//@EnableWebSecurity(debug = true) - Issue with version 3.2.1
public class SpringsecurityeazybankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityeazybankApplication.class, args);
	}
}
