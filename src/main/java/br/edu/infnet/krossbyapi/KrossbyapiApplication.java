package br.edu.infnet.krossbyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KrossbyapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KrossbyapiApplication.class, args);
	}

}
