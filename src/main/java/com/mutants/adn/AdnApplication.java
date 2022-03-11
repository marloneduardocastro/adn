package com.mutants.adn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@Slf4j
@SpringBootApplication
public class AdnApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AdnApplication.class);
		Environment env = app.run(args).getEnvironment();
		String hostAddress = getHostAddress();

		log.info("-----------------------------------------------");
		log.info("Application '{}' is running!", env.getProperty("spring.application.name"));
		log.info("host: {}:{}", hostAddress, env.getProperty("server.port"));
		log.info("-----------------------------------------------");
	}

	private static String getHostAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			log.warn("The host name could not be determined, using 'localhost' as fallback");
			return "localhost";
		}
	}
}
