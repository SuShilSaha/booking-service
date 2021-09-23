package com.paypal.bfs.test.bookingserv;

import com.paypal.bfs.test.bookingserv.api.BookingResource;
import com.paypal.bfs.test.bookingserv.exception.ApplicationException;
import com.paypal.bfs.test.bookingserv.service.IBookingDetailsService;
import model.ApiPaginatedResponse;
import model.Booking;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for {@link BookingResource}
 *
 * @author Sushil Saha
 *
 */

@RunWith(SpringRunner.class)
@WebMvcTest(BookingResource.class)
public class BookingResourceTest {

    private @Autowired MockMvc mockMvc;

    private @MockBean IBookingDetailsService bookingDetailsService;

    private final String createBookingFirstNameValidationErrorJson = "{\"last_name\":\"testUser1LastName\",\"date_of_birth\":\"1980-12-07\"," +
            "\"checkin_datetime\":\"2022-01-21 12:00:00\",\"checkout_datetime\":\"2022-01-23 11:59:59\"," + "\"totalprice\":\"2000\"," +
            "\"deposit\":\"500\",\"address\":{\"addressLine1\":\"house no 1, street 1\",\"addressLine2\":\"near some landmark\",\"city\":\"ABC\",\"state\":\"ZYX\",\"zip_code\":\"9999999\"}}";

    private final String createBookingSuccessJson = "{\"first_name\":\"testUser1FirstName\",\"last_name\":\"testUser1LastName\",\"date_of_birth\":\"1980-12-07\",\"checkin_datetime\":\"2022-01-21 12:00:00\"," +
            "\"checkout_datetime\":\"2022-01-23 11:59:59\",\"totalprice\":\"2000\",\"deposit\":\"500\",\"address\":{\"addressLine1\":\"house no 1, street 1\",\"addressLine2\":\"near some landmark\"," +
            "\"city\":\"ABC\",\"state\":\"ZYX\",\"zip_code\":\"9999999\"}}";

    @Test
    public void createNewBooking_ShouldReturnMissingParameterValidationError() throws Exception {
        given(bookingDetailsService.createNewBooking(any(Booking.class))).willThrow(new ApplicationException(""));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/booking")
                        .content(createBookingFirstNameValidationErrorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"));
    }

    @Test
    public void createNewBooking_ShouldCreateNewCreatedBooking() throws Exception {
        given(bookingDetailsService.createNewBooking(any(Booking.class))).willReturn(getBookingResponse());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/booking")
                        .content(createBookingSuccessJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("testUser1FirstName"));
    }

    @Test
    public void getBooking_ShouldReturnAllBookings() throws Exception {

        given(bookingDetailsService.getAllBookings(0, 10)).willReturn(getResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/bfs/booking").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageSize").value("10"))
                .andExpect(jsonPath("$.page").value("0"));
    }

    private Booking getBookingResponse() {
        Booking booking = new Booking();
        booking.setId(1);
        booking.setFirstName("testUser1FirstName");
        booking.setLastName("testUser1LastName");
        booking.setDateOfBirth("1980-12-07");
        booking.setDeposit(1000);
        booking.setTotalprice(2000);
        booking.setCheckinDatetime("2022-01-21 12:00:00");
        booking.setCheckoutDatetime("2022-01-23 11:59:59");
        return booking;
    }

    private ApiPaginatedResponse getResponse() {
        ApiPaginatedResponse bookingResponse = new ApiPaginatedResponse();
        bookingResponse.setPage(0);
        bookingResponse.setPageSize(10);

        Booking booking1 = new Booking();
        booking1.setId(1);
        booking1.setFirstName("testUser1FirstName");
        booking1.setLastName("testUser1LastName");
        booking1.setDeposit(1000);
        booking1.setTotalprice(2000);
        booking1.setCheckinDatetime("2022-01-21 12:00:00");
        booking1.setCheckoutDatetime("2022-01-23 11:59:59");

        Booking booking2 = new Booking();
        booking2.setId(1);
        booking2.setFirstName("testUser1FirstName");
        booking2.setLastName("testUser1LastName");
        booking2.setDeposit(1000);
        booking2.setTotalprice(2000);
        booking2.setCheckinDatetime("2022-03-11 12:00:00");
        booking2.setCheckoutDatetime("2022-03-13 11:59:59");

        List<Object> ls = new ArrayList<>(Arrays.asList(booking1, booking2));
        bookingResponse.setResults(ls);

        return bookingResponse;
    }
}
