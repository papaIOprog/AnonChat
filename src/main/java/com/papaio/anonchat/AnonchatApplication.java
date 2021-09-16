package com.papaio.anonchat;

import com.papaio.anonchat.properties.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class AnonchatApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnonchatApplication.class, args);
	}

}
