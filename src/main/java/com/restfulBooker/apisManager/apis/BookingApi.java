package com.restfulBooker.apisManager.apis;

import com.restfulBooker.apisManager.base.Specs;
import com.restfulBooker.apisManager.data.Routes;
import com.restfulBooker.apisManager.models.Booking;
import io.restassured.response.Response;

public class BookingApi {

    public static Response getAllBookings() {
        return Specs.getRequestSpec()
                .when()
                .get(Routes.BOOKING_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getBookingById(int id) {
        return Specs.getRequestSpec()
                .when()
                .get(Routes.BOOKING_ROUTE + "/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response createBooking(Booking booking) {
        return Specs.getRequestSpec()
                .body(booking)
                .when()
                .post(Routes.BOOKING_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response updateBooking(int id, Booking booking, String token) {
        return Specs.getRequestSpec()
                .cookie("token", token)
                .body(booking)
                .when()
                .put(Routes.BOOKING_ROUTE + "/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteBooking(int id, String token) {
        return Specs.getRequestSpec()
                .cookie("token", token)
                .when()
                .delete(Routes.BOOKING_ROUTE + "/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getBookingsByName(String firstname, String lastname) {
        return Specs.getRequestSpec()
                .queryParam("firstname", firstname)
                .queryParam("lastname", lastname)
                .when()
                .get(Routes.BOOKING_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response getBookingByInvalidId(String id) {
        return Specs.getRequestSpec()
                .when()
                .get(Routes.BOOKING_ROUTE + "/" + id)
                .then()
                .log().all()
                .extract().response();
    }

}
