package dev.guilhermebpnr.baeldung.tutorials.springbootbootstratp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("dev.guilhermebpnr.baeldung.tutorials.springbootbootstratp.repository")
@EntityScan("dev.guilhermebpnr.baeldung.tutorials.springbootbootstratp.model")
public class SpringBootBootstrapApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBootstrapApplication.class, args);
	}

}
