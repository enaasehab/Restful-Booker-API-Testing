package com.restfulBooker.tests;

import com.restfulBooker.apisManager.apis.BookingApi;
import com.restfulBooker.apisManager.steps.BookingSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Owner("Samah Sameh")
public class DeleteBookingTests {

    @Test
    @Story("Delete Booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a booking can be deleted successfully with valid authentication")
    @TmsLink("TC_API_018")
    public void shouldBeAbleToDeleteBookingSuccessfully() {
        Response createResponse = BookingApi.createBooking(BookingSteps.getValidBooking());
        int bookingId = createResponse.jsonPath().getInt("bookingid");
        String token = BookingSteps.gettToken();
        Response deleteResponse = BookingApi.deleteBooking(bookingId, token);
        assertThat(deleteResponse.statusCode(), equalTo(201));
    }

    @Test
    @Story("Delete Booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that deleting a booking without authentication returns Forbidden")
    @TmsLink("TC_API_019")
    public void shouldNotBeAbleToDeleteBookingWithoutAuthentication() {
        Response createResponse = BookingApi.createBooking(BookingSteps.getValidBooking());
        int bookingId = createResponse.jsonPath().getInt("bookingid");
        String token = BookingSteps.gettToken();
        Response deleteResponse = BookingApi.deleteBookingWithoutAuth(bookingId);
        assertThat(deleteResponse.statusCode(), equalTo(403));
        assertThat(deleteResponse.asString(), equalTo("Forbidden"));
    }
}
