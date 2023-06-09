package com.flights.ticket.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="poc-user-service",url = "localhost:8000")
public interface UserFeignClient{

	@GetMapping(value="/poc-user-service/validateuser/{userName}/{password}")
	Boolean validUser(@PathVariable String userName, @PathVariable String password);
}
