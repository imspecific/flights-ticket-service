package com.flights.ticket.exception;

public class TicketNotFoundException extends RuntimeException {

	public TicketNotFoundException(String s) {
		super(s);
	}
}
