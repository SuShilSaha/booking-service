package com.paypal.bfs.test.bookingserv.service.impl;

import com.paypal.bfs.test.bookingserv.entity.AddressDetails;
import com.paypal.bfs.test.bookingserv.entity.BookingDetails;
import com.paypal.bfs.test.bookingserv.exception.ApplicationException;
import com.paypal.bfs.test.bookingserv.persistence.BookingRepository;
import com.paypal.bfs.test.bookingserv.service.IBookingDetailsService;
import com.paypal.bfs.test.bookingserv.utils.DateTimeUtil;
import com.paypal.bfs.test.bookingserv.utils.DateUtil;
import model.ApiPaginatedResponse;
import model.Booking;
import model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Sushil Saha
 *
 */

@Service
class BookingDetailsServiceImpl implements IBookingDetailsService {

    @Autowired
    private BookingRepository repository;

    @Override
    @Transactional
    public Booking createNewBooking(Booking booking) {

        validateRequest(booking);

        BookingDetails newBooking = new BookingDetails();
        newBooking.setFirstName(booking.getFirstName());
        newBooking.setLastName(booking.getLastName());
        newBooking.setDateOfBirth(DateUtil.stringToDate(booking.getDateOfBirth()));
        newBooking.setDeposit(booking.getDeposit());
        newBooking.setTotalPrice(booking.getTotalprice());
        newBooking.setCheckinDatetime(DateTimeUtil.getDate(booking.getCheckinDatetime()));
        newBooking.setCheckoutDatetime(DateTimeUtil.getDate(booking.getCheckoutDatetime()));

        Address address = booking.getAddress();
        AddressDetails addressDetails = new AddressDetails();
        addressDetails.setAddressLine1(address.getAddressLine1());
        addressDetails.setAddressLine2(address.getAddressLine2());
        addressDetails.setCity(address.getCity());
        addressDetails.setState(address.getState());
        addressDetails.setZipCode(address.getZipCode());
        newBooking.setAddressDetails(addressDetails);

        repository.save(newBooking);
        booking = entityDO(newBooking);

        return booking;
    }

    @Override
    @Transactional(readOnly=true)
    public ApiPaginatedResponse getAllBookings(int pageIndex, int pageSize) {

        Page<BookingDetails> page = repository.findAll(PageRequest.of(pageIndex, pageSize, Sort.by("id")));
        List<BookingDetails> bookingDetailsList = page.getContent();
        List<Booking> bookings = bookingDetailsList.stream().map(bookingDetails -> entityDO(bookingDetails)).collect(Collectors.toList());

        ApiPaginatedResponse paginatedResponse = new ApiPaginatedResponse();
        paginatedResponse.setPage(page.getNumber());
        paginatedResponse.setPageSize(page.getSize());
        paginatedResponse.setResults(Collections.singletonList(bookings));
        return paginatedResponse;
    }

    private void validateRequest(Booking bookingRequest) {

        if(Objects.isNull(bookingRequest.getFirstName())){
            throw new ApplicationException("First name cannot be empty");
        }

        if(Objects.isNull(bookingRequest.getLastName())){
            throw new ApplicationException("Last name cannot be empty");
        }

        if(Objects.isNull(bookingRequest.getDateOfBirth())){
            throw new ApplicationException("Date of birth cannot be empty");
        }

        if(Objects.isNull(bookingRequest.getCheckinDatetime())){
            throw new ApplicationException("Check in cannot be empty");
        }

        if(Objects.isNull(bookingRequest.getCheckoutDatetime())){
            throw new ApplicationException("Check out cannot be empty");
        }

        if(Objects.isNull(bookingRequest.getTotalprice())){
            throw new ApplicationException("Total price cannot be empty");
        }

        if(Objects.nonNull(bookingRequest.getDeposit()) && bookingRequest.getDeposit() > bookingRequest.getTotalprice()){
            throw new ApplicationException("Deposit cannot be more than total");
        }

        if(Objects.isNull(bookingRequest.getAddress())){
            throw new ApplicationException("Address is required");
        }

        if(Objects.isNull(bookingRequest.getAddress().getAddressLine1())){
            throw new ApplicationException("Address Line1 cannot be empty");
        }

        if(Objects.isNull(bookingRequest.getAddress().getCity())){
            throw new ApplicationException("City cannot be empty");
        }

        if(Objects.isNull(bookingRequest.getAddress().getState())){
            throw new ApplicationException("State cannot be empty");
        }

        if(Objects.isNull(bookingRequest.getAddress().getZipCode())){
            throw new ApplicationException("ZipCode cannot be empty");
        }
    }

    private Booking entityDO(BookingDetails bookingDetails) {
        if(null == bookingDetails) return null;

        Booking booking = new Booking();
        booking.setId(bookingDetails.getId());
        booking.setFirstName(bookingDetails.getFirstName());
        booking.setLastName(bookingDetails.getLastName());
        booking.setDeposit(bookingDetails.getDeposit());
        booking.setTotalprice(bookingDetails.getTotalPrice());
        booking.setCheckinDatetime(DateTimeUtil.getDate(bookingDetails.getCheckinDatetime()));
        booking.setCheckoutDatetime(DateTimeUtil.getDate(bookingDetails.getCheckoutDatetime()));

        AddressDetails addressDetails = bookingDetails.getAddressDetails();
        Address address = new Address();

        address.setAddressLine1(addressDetails.getAddressLine1());
        address.setAddressLine2(addressDetails.getAddressLine2());
        address.setCity(addressDetails.getCity());
        address.setState(addressDetails.getState());
        address.setZipCode(addressDetails.getZipCode());

        booking.setAddress(address);
        return booking;
    }

}