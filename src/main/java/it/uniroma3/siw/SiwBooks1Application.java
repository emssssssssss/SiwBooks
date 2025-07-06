package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import it.uniroma3.authentication.AppProperties;

@SpringBootApplication
@ComponentScan(basePackages = "it.uniroma3")
@EnableJpaRepositories(basePackages = "it.uniroma3.repository")
@EntityScan(basePackages = "it.uniroma3.model")
@EnableConfigurationProperties(AppProperties.class)
public class SiwBooks1Application {

	public static void main(String[] args) {
		SpringApplication.run(SiwBooks1Application.class, args);
	}

}
