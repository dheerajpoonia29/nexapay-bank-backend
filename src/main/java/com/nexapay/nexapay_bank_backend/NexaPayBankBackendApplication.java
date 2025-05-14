package com.nexapay.nexapay_bank_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NexaPayBankBackendApplication {
	private static final Logger logger = LoggerFactory.getLogger(NexaPayBankBackendApplication.class);

	public static void main(String[] args) {
		logger.info("starting NexaPayBankBackendApplication");
		SpringApplication.run(NexaPayBankBackendApplication.class, args);
	}

}
