package com.flights.ticket.service;

import com.flights.ticket.exception.MyException;
import com.flights.ticket.exception.SeatNotAvailableException;
import com.flights.ticket.exception.UserNotValidException;
import com.flights.ticket.feignclient.FlightFeignClient;
import com.flights.ticket.feignclient.UserFeignClient;
import com.flights.ticket.model.BookedTicket;
import com.flights.ticket.model.Flight;
import com.flights.ticket.model.Ticket;
import com.flights.ticket.repository.BookedTicketRepo;
import com.flights.ticket.repository.TicketRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class TicketService {

    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private BookedTicketRepo bookedTicketRepo;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private FlightFeignClient flightFeignClient;

    private static final String USER_SERVICE = "poc-user-service";

    private static final String FLIGHT_SERVICE = "poc-flight-service";

    @CircuitBreaker(name = "USER_SERVICE", fallbackMethod = "userServiceFallBack")
    public BookedTicket bookTicket(Ticket ticket, Integer flightId, String userName, String password) {
        if (userFeignClient.validUser(userName, password)) {
            List<Flight> flights = flightFeignClient.getFlightsBySourceAndDestination(ticket.getSource(), ticket.getDestination(), userName, password);
            BookedTicket bt = new BookedTicket();
            flights.forEach(flight -> {
                if (flight.getFlightId().equals(flightId)) {
                    if (flight.getAvailSeats() >= ticket.getNoOfPax()) {
                        Flight f = new Flight();
                        f.setFlightId(flight.getFlightId());
                        f.setSource(flight.getSource());
                        f.setDestination(flight.getDestination());
                        f.setTClass(flight.getTClass());
                        f.setDepartureDate(flight.getDepartureDate());
                        f.setDepartureTime(flight.getDepartureTime());
                        f.setAvailSeats(flight.getAvailSeats() - ticket.getNoOfPax());
                        f.setFare(flight.getFare());
                        f.setTotalFare(ticket.getNoOfPax() * flight.getFare());
                        Random random = new Random();
                        bt.setPnr(random.nextInt(99999));
                        bt.setFlightId(flight.getFlightId());
                        bt.setSource(f.getSource());
                        bt.setDestination(f.getDestination());
                        bt.setDepartureDate(f.getDepartureDate());
                        bt.setDepartureTime(f.getDepartureTime());
                        bt.setUserName(ticket.getUserName());
                        bt.setPassword(ticket.getPassword());
                        bt.setTotalFare(ticket.getNoOfPax() * flight.getFare());
                        int difff = flight.getAvailSeats() - ticket.getNoOfPax();
                        flight.setAvailSeats((flight.getAvailSeats() - ticket.getNoOfPax()));
                        Integer diff = flight.getAvailSeats() - ticket.getNoOfPax();
                        flightFeignClient.updateFlightStatusById(flightId, difff);
                        bookedTicketRepo.save(bt);

                        log.info("Ticket booked.");
                    } else {
                        throw new SeatNotAvailableException("Seats are not available");
                    }
                }
            });
            return bt;
        } else {
            throw new UserNotValidException("User not valid.");
        }
    }


    public Ticket findTicketById(Integer ticketId, String userName, String password) {
        if (userFeignClient.validUser(userName, password)) {
            Optional<Ticket> isTicketValid = ticketRepo.findById(ticketId);
            if (isTicketValid.isPresent())
                return ticketRepo.findById(ticketId).get();
            else throw new MyException("Ticket id is invalid");
        } else
            throw new MyException("Declined");
    }


    public boolean cancelTicketById(Integer ticketId, String userName, String password) {
        if (userFeignClient.validUser(userName, password)) {
            Optional<Ticket> isTicketValid = ticketRepo.findById(ticketId);
            if (isTicketValid.isPresent()) {
                ticketRepo.deleteById(ticketId);
                return true;
            } else
                return false;
        } else {
            throw new MyException("Declined");
        }
    }

    @CircuitBreaker(name = "FLIGHT_SERVICE", fallbackMethod = "flightServiceFallBack")
    public List<Flight> getFlightsBySourceAndDestination(@PathVariable String source, @PathVariable String destination, @PathVariable String userName, @PathVariable String password) {
        if (userFeignClient.validUser(userName, password)) {
            return flightFeignClient.getFlightsBySourceAndDestination(source, destination, userName, password);
        } else
            throw new MyException("Flights not found");
    }


    public Flight flightById(Integer flightId, String userName, String password) {
        if (userFeignClient.validUser(userName, password)) {
            Flight f = flightFeignClient.flightById(flightId, userName, password);
            if (f == null)
                throw new MyException("Flight not found.");
            else
                return f;
        } else throw new MyException("Some error occurred.");
    }


    public String deleteBookedTicketById(@PathVariable Integer pnr, @PathVariable String userName, @PathVariable String password) {
        if (userFeignClient.validUser(userName, password)) {
            ticketRepo.deleteBookedTicketById(pnr, userName, password);
            return "Ticket canceled.";
        } else {
            throw new UserNotValidException("User not valid.");
        }
    }

    public BookedTicket userServiceFallBack(Exception ex) {
        throw new MyException("User Service is currently down...");
    }

    public List<Flight> flightServiceFallBack(Exception ex) {
        throw new MyException("Flight service is currently down...");
    }
}
