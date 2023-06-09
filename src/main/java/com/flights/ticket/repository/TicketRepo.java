package com.flights.ticket.repository;

import javax.transaction.Transactional;

import com.flights.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer> {
	
	@Transactional
	@Modifying
	@Query(value="delete from poc_booked_ticket where pnr=?1 and user_name=?2 and password=?3", nativeQuery = true)
	void deleteBookedTicketById(@PathVariable Integer pnr, @PathVariable String userName, @PathVariable String password);
}
