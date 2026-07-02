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
public class PartiallyUpdateTests {

    @Test
    @Story("Partial Update Booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a booking can be partially updated successfully with valid authentication")
    @TmsLink("TC_API_016")
    public void shouldBeAbleToPartiallyUpdateBookingSuccessfully() {
        Booking originalBooking = BookingSteps.getValidBooking();
        Response createResponse = BookingApi.createBooking(BookingSteps.getValidBooking());
        int bookingId = createResponse.jsonPath().getInt("bookingid");
        String token = BookingSteps.gettToken();
        Booking updatedBooking = UpdateBookingSteps.getPartiallyUpdatedBooking();

        Response updateResponse = BookingApi.PartiallyUpdateBooking(bookingId, updatedBooking, token);

        assertThat(updateResponse.statusCode(), equalTo(200));
        // Updated fields
        assertThat(updateResponse.jsonPath().getString("firstname"),
                equalTo(updatedBooking.getFirstname()));
        assertThat(updateResponse.jsonPath().getString("lastname"),
                equalTo(updatedBooking.getLastname()));

        // Unchanged fields
        assertThat(updateResponse.jsonPath().getInt("totalprice"),
                equalTo(originalBooking.getTotalprice()));
        assertThat(updateResponse.jsonPath().getBoolean("depositpaid"),
                equalTo(originalBooking.getDepositpaid()));
        assertThat(updateResponse.jsonPath().getString("bookingdates.checkin"),
                equalTo(originalBooking.getBookingdates().getCheckin()));
        assertThat(updateResponse.jsonPath().getString("bookingdates.checkout"),
                equalTo(originalBooking.getBookingdates().getCheckout()));
    }

    @Test
    @Story("Partial Update Booking")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that partially updating a booking with an empty request body returns an error or no change")
    @TmsLink("TC_API_017")
    public void shouldNotChangeBookingWhenPartialUpdateBodyIsEmpty() {
        Booking originalBooking = BookingSteps.getValidBooking();

        Response createResponse = BookingApi.createBooking(originalBooking);
        int bookingId = createResponse.jsonPath().getInt("bookingid");
        String token = BookingSteps.gettToken();

        Booking emptyBooking = Booking.builder().build();
        Response response = BookingApi.PartiallyUpdateBooking(bookingId, emptyBooking, token);

        assertThat(response.statusCode(), equalTo(200));

        // Verify nothing changed
        assertThat(response.jsonPath().getString("firstname"),
                equalTo(originalBooking.getFirstname()));
        assertThat(response.jsonPath().getString("lastname"),
                equalTo(originalBooking.getLastname()));
        assertThat(response.jsonPath().getInt("totalprice"),
                equalTo(originalBooking.getTotalprice()));
        assertThat(response.jsonPath().getBoolean("depositpaid"),
                equalTo(originalBooking.getDepositpaid()));
        assertThat(response.jsonPath().getString("bookingdates.checkin"),
                equalTo(originalBooking.getBookingdates().getCheckin()));
        assertThat(response.jsonPath().getString("bookingdates.checkout"),
                equalTo(originalBooking.getBookingdates().getCheckout()));
        assertThat(response.jsonPath().getString("additionalneeds"),
                equalTo(originalBooking.getAdditionalneeds()));

    }
}
