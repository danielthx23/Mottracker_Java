package br.com.fiap.Mottracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MottrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MottrackerApplication.class, args);
	}

}
