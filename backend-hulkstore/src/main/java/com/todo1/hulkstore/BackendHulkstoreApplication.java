package com.todo1.hulkstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BackendHulkstoreApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(BackendHulkstoreApplication.class);

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BackendHulkstoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		for (int i = 0; i < 2; i++) {
			String bCryptPasswordEncoder = passwordEncoder.encode("12345");
			LOG.info("Password: " + bCryptPasswordEncoder);
			LOG.info("Password: 12345");
		}
	}

}
