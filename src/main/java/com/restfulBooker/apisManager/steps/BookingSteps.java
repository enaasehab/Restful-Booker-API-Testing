package com.restfulBooker.apisManager.steps;

import com.restfulBooker.apisManager.apis.AuthApi;
import com.restfulBooker.apisManager.models.AuthRequest;
import com.restfulBooker.apisManager.models.Booking;
import com.restfulBooker.apisManager.models.BookingDates;
import com.restfulBooker.dataReader.PropertyReader;
import io.restassured.response.Response;

import java.awt.print.Book;

import static com.restfulBooker.dataReader.PropertyReader.getProperty;

public class BookingSteps {

    public static AuthRequest getValidAuthRequest(){
        return AuthRequest.builder()
                .username(getProperty("valid.username"))
                .password(getProperty("valid.password"))
                .build();
    }

    public static AuthRequest getInvalidAuthRequest(){
        return AuthRequest.builder()
                .username(getProperty("valid.username"))
                .password(getProperty("invalid.password"))
                .build();
    }

    public static AuthRequest getMissinUsernameAuthRequest(){
        return AuthRequest.builder()
                .password(getProperty("valid.password"))
                .build();
    }
    public static String getToken(){
        Response response= AuthApi.createToken(getInvalidAuthRequest());
        return response.body().path("token");
    }

    public static Booking getValidBooking(){

        return Booking.builder()
                .firstname(getProperty("valid.firstname"))
                .lastname(getProperty("valid.lastname"))
                .totalprice(Integer.parseInt(getProperty("valid.totalprice")))
                .depositpaid(Boolean.parseBoolean(getProperty("valid.depositpaid")))
                .bookingdates(BookingDates.builder()
                        .checkin(getProperty("valid.checkin"))
                        .checkout(getProperty("valid.checkout"))
                        .build())
                .additionalneeds(getProperty("valid.additionalneeds"))
                .build();
    }
    public static Booking getBookingWithMissingFirstname() {
        return Booking.builder()
                .lastname(getProperty("valid.lastname"))
                .totalprice(Integer.parseInt(getProperty("valid.totalprice")))
                .depositpaid(Boolean.parseBoolean(getProperty("valid.depositpaid")))
                .bookingdates(BookingDates.builder()
                        .checkin(getProperty("valid.checkin"))
                        .checkout(getProperty("valid.checkout"))
                        .build())
                .additionalneeds(getProperty("valid.additionalneeds"))
                .build();
    }
    public static Booking getBookingWithNegativePrice() {
        return Booking.builder()
                .firstname(getProperty("valid.firstname"))
                .lastname(getProperty("valid.lastname"))
                .totalprice(-100)
                .depositpaid(Boolean.parseBoolean(getProperty("valid.depositpaid")))
                .bookingdates(BookingDates.builder()
                        .checkin(getProperty("valid.checkin"))
                        .checkout(getProperty("valid.checkout"))
                        .build())
                .additionalneeds(getProperty("valid.additionalneeds"))
                .build();
    }

    public static String gettToken() {
        Response response = AuthApi.createToken(getValidAuthRequest());
        return response.jsonPath().getString("token");
    }

    public static Booking getBookingWithInvalidDates() {
        return Booking.builder()
                .firstname(getProperty("valid.firstname"))
                .lastname(getProperty("valid.lastname"))
                .totalprice(Integer.parseInt(getProperty("valid.totalprice")))
                .depositpaid(Boolean.parseBoolean(getProperty("valid.depositpaid")))
                .bookingdates(
                        BookingDates.builder()
                                .checkin("2026-08-10")
                                .checkout("2026-08-01")
                                .build()
                )
                .additionalneeds(getProperty("valid.additionalneeds"))
                .build();
    }



}
