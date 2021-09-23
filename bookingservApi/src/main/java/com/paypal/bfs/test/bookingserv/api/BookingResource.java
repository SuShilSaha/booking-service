package com.paypal.bfs.test.bookingserv.api;

import model.Booking;
import model.ApiPaginatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;

public interface BookingResource {
    /**
     * Create {@link Booking} resource
     *
     * @param booking the booking object
     * @return the created booking
     */
    @RequestMapping(value = "/v1/bfs/booking", method = RequestMethod.POST)
    ResponseEntity<Booking> create(@RequestBody Booking booking);

    /**
     * Fetch {@link Booking} resource list
     *
     * @param pageIndex
     * @param pageSize
     *
     * @return the booking details
     */
    @RequestMapping(value = "/v1/bfs/booking", method = RequestMethod.GET)
    ResponseEntity<ApiPaginatedResponse> getBookings(@RequestParam(value = "pageIndex", required = false, defaultValue = "0") @Min(0) int pageIndex,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") @Min(1) int pageSize);
}
