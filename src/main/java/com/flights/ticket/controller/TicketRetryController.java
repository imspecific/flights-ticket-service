package com.flights.ticket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class TicketRetryController {

	private static Logger logger = LoggerFactory.getLogger(TicketRetryController.class);
	
	@GetMapping(value="/ticket-retryinfo")
	@Retry(name="myservice", fallbackMethod = "failureResponse")
	public String retryInfo() {
		logger.info("Ticket retry controller call received.");
		return "Resilience retry info called.";
	}
	
	public String failureResponse(Exception ex) {
		return "Ticket Retry: Ticket Service is down.";
	}
}
