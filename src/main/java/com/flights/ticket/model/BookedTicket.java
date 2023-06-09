package com.flights.ticket.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="poc_booked_ticket")
public class BookedTicket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pnr;

	private Integer flightId;
	private String source;
	private String destination;
	private String departureDate;
	private String departureTime;
	private double totalFare;
	private String userName;
	private String password;
}
