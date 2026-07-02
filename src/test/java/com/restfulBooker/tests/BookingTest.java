package com.restfulBooker.tests;

import com.restfulBooker.apisManager.apis.AuthApi;
import com.restfulBooker.apisManager.apis.BookingApi;
import com.restfulBooker.apisManager.models.AuthRequest;
import com.restfulBooker.apisManager.models.AuthResponse;
import com.restfulBooker.apisManager.models.Booking;
import com.restfulBooker.apisManager.steps.BookingSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.restfulBooker.dataReader.PropertyReader.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Owner("Enas Ehab")
@Feature("Authentication")
public class BookingTest {

    @Test
    @Story("Get All Bookings")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that all existing booking IDs can be retrieved successfully")
    @TmsLink("TC_API_004")
    public void shouldBeAbleToGetAllBookings() {
        Response response = BookingApi.getAllBookings();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getList("$"), not(empty()));
        assertThat(response.jsonPath().getList("bookingid"), everyItem(notNullValue()));
    }

    @Test
    @Story("Get Booking By ID")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a specific booking can be retrieved successfully using a valid booking ID")
    @TmsLink("TC_API_005")
    public void shouldBeAbleToGetBookingById() {
        Response response = BookingApi.getAllBookings();
        Booking booking = BookingApi.getBookingById(
                        response.jsonPath().getInt("[0].bookingid"))
                .body().as(Booking.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(booking.getFirstname(), notNullValue());
        assertThat(booking.getLastname(), notNullValue());
        assertThat(booking.getTotalprice(), notNullValue());
        assertThat(booking.getDepositpaid(), notNullValue());
        assertThat(booking.getBookingdates(), notNullValue());
    }

    @Test
    @Story("Filter Bookings")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that booking IDs can be filtered by firstname and lastname query parameters")
    @TmsLink("TC_API_006")
    public void shouldBeAbleToFilterBookingsByName() {
        Response response = BookingApi.getAllBookings();
        Booking booking = BookingApi.getBookingById(
                        response.jsonPath().getInt("[0].bookingid"))
                .body().as(Booking.class);

        Response filterResponse = BookingApi.getBookingsByName(
                booking.getFirstname(), booking.getLastname());

        assertThat(filterResponse.statusCode(), equalTo(200));
        assertThat(filterResponse.jsonPath().getList("$"), not(empty()));
    }
    @Test
    @Story("Get Booking By ID")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that retrieving a booking with a non-existent ID returns 404")
    @TmsLink("TC_API_007")
    public void shouldNotBeAbleToGetBookingWithNonExistentId() {
        Response response = BookingApi.getBookingByInvalidId("999999999");

        assertThat(response.statusCode(), equalTo(404));
        assertThat(response.time(), lessThan(2000L));
    }
    @Test
    @Story("Get Booking By ID")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that retrieving a booking with invalid non-numeric ID returns 404")
    @TmsLink("TC_API_008")
    public void shouldNotBeAbleToGetBookingWithInvalidId() {
        Response response = BookingApi.getBookingByInvalidId("abcXYZ");

        assertThat(response.statusCode(), equalTo(404));
        assertThat(response.body().asString(), not(containsString("stack")));
        assertThat(response.body().asString(), not(containsString("Exception")));
    }

    @Test
    @Story("Create Booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a new booking can be created successfully with valid and complete data")
    @TmsLink("TC_API_009")
    public void shouldBeAbleToCreateBookingWithValidData() {
        Response response = BookingApi.createBooking(BookingSteps.getValidBooking());

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getInt("bookingid"), notNullValue());
        assertThat(response.jsonPath().getString("booking.firstname"),
                equalTo(getProperty("valid.firstname")));
        assertThat(response.jsonPath().getString("booking.lastname"),
                equalTo(getProperty("valid.lastname")));
    }
    @Test
    @Story("Create Booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that creating a booking fails when mandatory field firstname is missing")
    @TmsLink("TC_API_010")
    public void shouldNotBeAbleToCreateBookingWithMissingFirstname() {
        Response response = BookingApi.createBooking(
                BookingSteps.getBookingWithMissingFirstname());

        assertThat(response.statusCode(), equalTo(400));
    }

    @Test
    @Owner("Samah Sameh")
    @Story("Create Booking")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that creating a booking fails when totalprice is a negative boundary value")
    @TmsLink("TC_API_011")
    public void shouldNotCreateBookingWithNegativeTotalPrice() {
        Booking booking = BookingSteps.getBookingWithNegativePrice();
        Response response = BookingApi.createBooking(booking);
        assertThat(response.statusCode(), equalTo(400));
    }

    @Test
    @Owner("Samah Sameh")
    @Story("Create Booking")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that creating a booking fails when checkout date is earlier than checkin date")
    @TmsLink("TC_API_012")
    public void shouldNotCreateBookingWhenCheckoutDateIsEarlierThanCheckinDate() {
        Booking booking = BookingSteps.getBookingWithInvalidDates();
        Response response = BookingApi.createBooking(booking);
        assertThat(response.statusCode(), equalTo(400));
    }



}
