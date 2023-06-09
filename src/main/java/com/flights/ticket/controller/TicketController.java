package com.flights.ticket.controller;

import com.flights.ticket.model.BookedTicket;
import com.flights.ticket.model.Flight;
import com.flights.ticket.model.Ticket;
import com.flights.ticket.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Component
@RestController
@RequestMapping("poc-ticket-service")
public class TicketController {


    @Autowired
    private TicketService ticketService;

    // poc-ticket-service/book-ticket/201/user1/password
    @PostMapping(value = "/book-ticket/{flightId}/{userName}/{password}")
    public BookedTicket bookTicket(@RequestBody Ticket ticket, @PathVariable Integer flightId, @PathVariable String userName, @PathVariable String password) {
        return ticketService.bookTicket(ticket, flightId, userName, password);
    }

    // poc-ticket-service/find-ticket-by-id/101/user1/password
    @GetMapping(value = "/find-ticket-by-id/{ticketId}/{userName}/{password}")
    public Ticket findTicketById(@PathVariable Integer ticketId, @PathVariable String userName, @PathVariable String password) {
        log.info("poc-ticket-service find tickey by id controller executed");
        return ticketService.findTicketById(ticketId, userName, password);
    }

    // poc-ticket-service/cancel-ticket-by-id/44/user1/password
    @DeleteMapping(value = "/cancel-ticket-by-id/{ticketId}/{userName}/{password}")
    public String cancelTicketById(@PathVariable Integer ticketId, @PathVariable String userName, @PathVariable String password) {
        if (ticketService.cancelTicketById(ticketId, userName, password))
            return "Ticket is canceled.";
        else return "Declined.";

    }

    // poc-ticket-service/cancel-booked-ticket-by-id/5/user1/password
    @DeleteMapping(value = "/cancel-booked-ticket-by-id/{pnr}/{userName}/{password}")
    public String cancelBookedTicketById(@PathVariable Integer pnr, @PathVariable String userName, @PathVariable String password) {
        ticketService.deleteBookedTicketById(pnr, userName, password);
        return "Ticket canceled.";
    }

    // poc-ticket-service/flights-by-source-destination/Hyderabad/Prayagraj/user1/password
    @GetMapping(value = "/flights-by-source-destination/{source}/{destination}/{userName}/{password}")
    public List<Flight> getFlightsBySourceAndDestination(@PathVariable String source, @PathVariable String destination, @PathVariable String userName, @PathVariable String password) {
        log.info("poc-ticket-service find source and destination controller executed");
        return ticketService.getFlightsBySourceAndDestination(source, destination, userName, password);
    }

    // poc-ticket-service/find-flight-by-id/201/user1/password
    @GetMapping(value = "/find-flight-by-id/{flightId}/{userName}/{password}")
    public Flight flightById(@PathVariable Integer flightId, @PathVariable String userName, @PathVariable String password) {
        log.info("poc-ticket-service find flight by id controller executed");
        return ticketService.flightById(flightId, userName, password);
    }

}
