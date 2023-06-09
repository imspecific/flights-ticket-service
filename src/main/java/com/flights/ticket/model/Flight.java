package com.flights.ticket.model;

import lombok.Data;

@Data
public class Flight {

	private Integer flightId;
	private String source;
	private String destination;
	private Integer availSeats;
	private String tClass;
	private String departureDate;
	private String departureTime;
	private double fare;
	private double totalFare;
}
