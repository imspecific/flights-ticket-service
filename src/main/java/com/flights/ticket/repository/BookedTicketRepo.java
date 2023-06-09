package com.flights.ticket.repository;

import com.flights.ticket.model.BookedTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedTicketRepo extends JpaRepository<BookedTicket, Integer> {

}
