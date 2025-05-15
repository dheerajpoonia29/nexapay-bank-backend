package com.nexapay.nexapay_bank_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.nexapay.model")
@EnableJpaRepositories(basePackages = "com.nexapay.nexapay_bank_backend.dao")
public class NexaPayBankBackendApplication {
	private static final Logger logger = LoggerFactory.getLogger(NexaPayBankBackendApplication.class);

	public static void main(String[] args) {
		logger.info("starting NexaPayBankBackendApplication");
		SpringApplication.run(NexaPayBankBackendApplication.class, args);
	}

}
