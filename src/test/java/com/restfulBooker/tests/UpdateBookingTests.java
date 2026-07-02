package com.restfulBooker.tests;

import com.restfulBooker.apisManager.apis.BookingApi;
import com.restfulBooker.apisManager.models.Booking;
import com.restfulBooker.apisManager.steps.BookingSteps;
import com.restfulBooker.apisManager.steps.UpdateBookingSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Owner("Samah Sameh")
public class UpdateBookingTests {

    @Test
    @Story("Update Booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a booking can be fully updated successfully with valid authentication")
    @TmsLink("TC_API_013")
    public void shouldBeAbleToUpdateBookingSuccessfully(){
        Response createResponse = BookingApi.createBooking(BookingSteps.getValidBooking());
        int bookingId = createResponse.jsonPath().getInt("bookingid");
        String token = BookingSteps.gettToken();
        Booking updatedBooking = UpdateBookingSteps.getUpdatedBooking();

        Response updateResponse = BookingApi.updateBooking(bookingId, updatedBooking, token);

        assertThat(updateResponse.statusCode(), equalTo(200));
        assertThat(updateResponse.jsonPath().getString("firstname"),
                equalTo(updatedBooking.getFirstname()));
        assertThat(updateResponse.jsonPath().getString("lastname"),
                equalTo(updatedBooking.getLastname()));
        assertThat(updateResponse.jsonPath().getInt("totalprice"),
                equalTo(updatedBooking.getTotalprice()));
        assertThat(updateResponse.jsonPath().getBoolean("depositpaid"),
                equalTo(updatedBooking.getDepositpaid()));
        assertThat(updateResponse.jsonPath().getString("bookingdates.checkin"),
                equalTo(updatedBooking.getBookingdates().getCheckin()));
        assertThat(updateResponse.jsonPath().getString("bookingdates.checkout"),
                equalTo(updatedBooking.getBookingdates().getCheckout()));
        assertThat(updateResponse.jsonPath().getString("additionalneeds"),
                equalTo(updatedBooking.getAdditionalneeds()));

    }

    @Test
    @Story("Update Booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that updating a booking without an authentication token returns Forbidden")
    @TmsLink("TC_API_014")
    public void shouldNotBeAbleToUpdateBookingWithoutAuthenticationToken() {
        Response createResponse = BookingApi.createBooking(BookingSteps.getValidBooking());
        int bookingId = createResponse.jsonPath().getInt("bookingid");
        Booking updatedBooking = UpdateBookingSteps.getUpdatedBooking();

        Response updateResponse = BookingApi.updateBookingwithoutAuth(bookingId, updatedBooking);
        assertThat(updateResponse.statusCode(), equalTo(403));
        assertThat(updateResponse.asString(), equalTo("Forbidden"));
    }

    @Test
    @Story("Update Booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that updating a booking with an invalid token returns Forbidden")
    @TmsLink("TC_API_015")
    public void shouldNotBeAbleToUpdateBookingWithInvalidToken() {
        Response createResponse = BookingApi.createBooking(BookingSteps.getValidBooking());
        int bookingId = createResponse.jsonPath().getInt("bookingid");
        Booking updatedBooking = UpdateBookingSteps.getUpdatedBooking();
        String invalidToken = "invalidToken123456";

        Response updateResponse = BookingApi.updateBooking(bookingId, updatedBooking,invalidToken);
        assertThat(updateResponse.statusCode(), equalTo(403));
        assertThat(updateResponse.asString(), equalTo("Forbidden"));
    }

}
