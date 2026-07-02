package com.restfulBooker.apisManager.steps;

import com.restfulBooker.apisManager.apis.AuthApi;
import com.restfulBooker.apisManager.models.Booking;
import com.restfulBooker.apisManager.models.BookingDates;
import io.restassured.response.Response;

import static com.restfulBooker.dataReader.PropertyReader.getProperty;

public class UpdateBookingSteps {

    public static Booking getUpdatedBooking() {
        return Booking.builder()
                .firstname(getProperty("updated.firstname"))
                .lastname(getProperty("updated.lastname"))
                .totalprice(Integer.parseInt(getProperty("updated.totalprice")))
                .depositpaid(Boolean.parseBoolean(getProperty("updated.depositpaid")))
                .bookingdates(
                        BookingDates.builder()
                                .checkin(getProperty("updated.checkin"))
                                .checkout(getProperty("updated.checkout"))
                                .build()
                )
                .additionalneeds(getProperty("updated.additionalneeds"))
                .build();
    }

    public static Booking getPartiallyUpdatedBooking() {
        return Booking.builder()
                .firstname(getProperty("updated.firstname"))
                .lastname(getProperty("updated.lastname"))
                .build();
    }


}
