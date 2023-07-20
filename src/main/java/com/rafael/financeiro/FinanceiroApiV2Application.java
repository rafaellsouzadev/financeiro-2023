package com.rafael.financeiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.rafael.financeiro.config.properties.AppFinanceiroProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppFinanceiroProperties.class)
public class FinanceiroApiV2Application {

	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApiV2Application.class, args);
	}

}
