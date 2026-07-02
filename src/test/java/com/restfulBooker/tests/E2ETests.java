package com.restfulBooker.tests;

import com.restfulBooker.apisManager.apis.BookingApi;
import com.restfulBooker.apisManager.models.Booking;
import com.restfulBooker.apisManager.steps.BookingSteps;
import com.restfulBooker.apisManager.steps.UpdateBookingSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class E2ETests {

    @Test
    @Owner("Samah Sameh")
    @Story("End-to-End Booking Workflow")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify complete booking lifecycle: Create Token -> Create Booking -> Retrieve Booking -> Update Booking -> Delete Booking -> Confirm Deletion")
    @TmsLink("TC_API_E2E_21")
    public void shouldCompleteBookingLifecycleSuccessfully() {

        //Create Authentication Token
        String token = BookingSteps.gettToken();
        assertThat(token, notNullValue());
        assertThat(token, not(emptyString()));

        //Create Booking
        Booking booking = BookingSteps.getValidBooking();
        Response createResponse = BookingApi.createBooking(booking);
        assertThat(createResponse.statusCode(), equalTo(200));
        int bookingId = createResponse.jsonPath().getInt("bookingid");
        assertThat(bookingId, greaterThan(0));

        //Retrieve Booking
        Response getResponse = BookingApi.getBookingById(bookingId);
        assertThat(getResponse.statusCode(), equalTo(200));
        assertThat(getResponse.jsonPath().getString("firstname"),
                equalTo(booking.getFirstname()));
        assertThat(getResponse.jsonPath().getString("lastname"),
                equalTo(booking.getLastname()));

        //Update Booking
        Booking updatedBooking = UpdateBookingSteps.getUpdatedBooking();
        Response updateResponse =
                BookingApi.updateBooking(bookingId, updatedBooking, token);
        assertThat(updateResponse.statusCode(), equalTo(200));
        assertThat(updateResponse.jsonPath().getString("firstname"),
                equalTo(updatedBooking.getFirstname()));
        assertThat(updateResponse.jsonPath().getString("lastname"),
                equalTo(updatedBooking.getLastname()));

        //Delete Booking
        Response deleteResponse =
                BookingApi.deleteBooking(bookingId, token);
        assertThat(deleteResponse.statusCode(), equalTo(201));

        //Confirm Deletion
        Response retrieveAfterDelete =
                BookingApi.getBookingById(bookingId);
        assertThat(retrieveAfterDelete.statusCode(), equalTo(404));
    }


    @Test
    @Owner("Enas Ehab")
    @Story("End-to-End Booking Workflow")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify booking lifecycle with partial update: Create Token -> Create Booking -> Retrieve Booking -> Partially Update Booking -> Retrieve Updated Booking -> Delete Booking")
    @TmsLink("TC_API_022")
    public void shouldCompleteBookingLifecycleWithPartialUpdateSuccessfully() {

        // Create Authentication Token
        String token = BookingSteps.gettToken();
        assertThat(token, notNullValue());
        assertThat(token, not(emptyString()));

        // Create Booking
        Booking booking = BookingSteps.getValidBooking();
        Response createResponse = BookingApi.createBooking(booking);
        assertThat(createResponse.statusCode(), equalTo(200));
        int bookingId = createResponse.jsonPath().getInt("bookingid");
        assertThat(bookingId, greaterThan(0));

        // Retrieve Booking
        Response getResponse = BookingApi.getBookingById(bookingId);
        assertThat(getResponse.statusCode(), equalTo(200));
        assertThat(getResponse.jsonPath().getString("firstname"),
                equalTo(booking.getFirstname()));
        assertThat(getResponse.jsonPath().getString("lastname"),
                equalTo(booking.getLastname()));

        // Partially Update Booking
        Booking partialBooking = UpdateBookingSteps.getPartiallyUpdatedBooking();
        Response patchResponse = BookingApi.PartiallyUpdateBooking(bookingId, partialBooking, token);
        assertThat(patchResponse.statusCode(), equalTo(200));
        assertThat(patchResponse.jsonPath().getString("firstname"),
                equalTo(partialBooking.getFirstname()));
        assertThat(patchResponse.jsonPath().getString("lastname"),
                equalTo(partialBooking.getLastname()));
        // Unchanged fields should remain the same
        assertThat(patchResponse.jsonPath().getInt("totalprice"),
                equalTo(booking.getTotalprice()));
        assertThat(patchResponse.jsonPath().getBoolean("depositpaid"),
                equalTo(booking.getDepositpaid()));

        // Retrieve Updated Booking to Verify Patch
        Response verifyResponse = BookingApi.getBookingById(bookingId);
        assertThat(verifyResponse.statusCode(), equalTo(200));
        assertThat(verifyResponse.jsonPath().getString("firstname"),
                equalTo(partialBooking.getFirstname()));
        assertThat(verifyResponse.jsonPath().getString("lastname"),
                equalTo(partialBooking.getLastname()));

        // Delete Booking
        Response deleteResponse = BookingApi.deleteBooking(bookingId, token);
        assertThat(deleteResponse.statusCode(), equalTo(201));
    }
}
