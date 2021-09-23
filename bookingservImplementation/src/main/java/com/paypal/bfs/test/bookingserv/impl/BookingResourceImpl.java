package com.paypal.bfs.test.bookingserv.impl;

import com.paypal.bfs.test.bookingserv.api.BookingResource;
import model.Booking;
import com.paypal.bfs.test.bookingserv.service.IBookingDetailsService;
import model.ApiPaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingResourceImpl implements BookingResource {

    @Autowired
    private IBookingDetailsService bookingDetailsService;

    @Override
    public ResponseEntity<Booking> create(Booking booking) {
       return ResponseEntity.ok(bookingDetailsService.createNewBooking(booking));
    }

    @Override
    public ResponseEntity<ApiPaginatedResponse> getBookings(int pageIndex, int pageSize) {
        return ResponseEntity.ok(bookingDetailsService.getAllBookings(pageIndex, pageSize));
    }
}
