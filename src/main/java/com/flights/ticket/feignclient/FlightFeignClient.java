package com.flights.ticket.feignclient;

import java.util.List;

import com.flights.ticket.model.Flight;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="poc-flight-service",url="localhost:8200")
public interface FlightFeignClient {

	
	@GetMapping(value="/poc-flight-service/find-flight-by-id/{flightId}/{userName}/{password}")
	public Flight flightById(@PathVariable Integer flightId, @PathVariable String userName, @PathVariable String password);
	
	@GetMapping(value="/poc-flight-service/flights-by-source-destination/{source}/{destination}/{userName}/{password}")
	public List<Flight> getFlightsBySourceAndDestination(@PathVariable String source, @PathVariable String destination, @PathVariable String userName, @PathVariable String password);
	
	@PutMapping(value="/poc-flight-service/update-flight-status/{flightId}/{availSeats}")
	public void updateFlightStatusById(@PathVariable Integer flightId, @PathVariable Integer availSeats);
	
}
