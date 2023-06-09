package com.flights.ticket;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.flights.ticket.feignclient")
public class PocTicketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocTicketServiceApplication.class, args);
		log.info("POC-Ticket Service is running..");
	}

}
