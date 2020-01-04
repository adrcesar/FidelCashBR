package br.com.acf.fidelcash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@SpringBootApplication
@EnableSpringDataWebSupport
public class FidelcashApplication {

	public static void main(String[] args) {
		SpringApplication.run(FidelcashApplication.class, args);
	}

}
