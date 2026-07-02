package com.restfulBooker.tests;

import com.restfulBooker.apisManager.apis.BookingApi;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Owner("Samah Sameh")
public class HealthCheckTests {

    @Test
    @Story("Health Check")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify the health check endpoint confirms the API is up and running")
    @TmsLink("TC_API_015")
    public void shouldConfirmApiIsUpAndRunning() {
        Response response = BookingApi.healthCheck();
        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.asString(), equalTo("Created"));
    }
}
