package com.paypal.bfs.test.bookingserv.persistence;

import com.paypal.bfs.test.bookingserv.entity.BookingDetails;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Sushil Saha
 *
 */

public interface BookingRepository extends PagingAndSortingRepository<BookingDetails, Integer> {

}