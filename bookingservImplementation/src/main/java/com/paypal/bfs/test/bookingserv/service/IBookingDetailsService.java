package com.paypal.bfs.test.bookingserv.service;

import model.Booking;
import model.ApiPaginatedResponse;

/**
 * @author Sushil Saha
 *
 */

public interface IBookingDetailsService {

    Booking createNewBooking(Booking booking);

    ApiPaginatedResponse getAllBookings(int page, int pageSize);
}