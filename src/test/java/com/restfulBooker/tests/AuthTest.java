package com.restfulBooker.tests;

import com.restfulBooker.apisManager.apis.AuthApi;
import com.restfulBooker.apisManager.models.AuthResponse;
import com.restfulBooker.apisManager.steps.BookingSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.restfulBooker.dataReader.PropertyReader.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Owner("Enas Ehab")
@Feature("Authentication")
public class AuthTest {
    @Test
    @Story("Valid Authentication")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a user can successfully create an authentication token with valid credentials")
    @TmsLink("TC_API_001")
    public void shouldBeAbleToCreateTokenWithValidCredentials(){
        Response response = AuthApi.createToken(BookingSteps.getValidAuthRequest());
        AuthResponse authResponse = response.body().as(AuthResponse.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(authResponse.getToken(),notNullValue());
        assertThat(authResponse.getReason(),not(emptyString()));

    }

    @Test
    @Story("Invalid Authentication")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that authentication fails with invalid password and returns Bad credentials")
    @TmsLink("TC_API_002")
    public void shouldNotBeAbleToCreateTokenWithInvalidPassword(){
      Response response = AuthApi.createToken(BookingSteps.getInvalidAuthRequest());
      AuthResponse authResponse= response.body().as(AuthResponse.class);
        assertThat(response.statusCode(),equalTo(401));
        assertThat(authResponse.getToken(), nullValue());
        assertThat(authResponse.getReason(), equalTo(getProperty("error.bad.credentials")));

    }
    @Test
    @Story("Invalid Authentication")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that authentication fails when username field is missing from the payload")
    @TmsLink("TC_API_003")
    public void shouldNotBeAbleToCreateTokenWithMissingUsername() {
        Response response = AuthApi.createToken(BookingSteps.getMissinUsernameAuthRequest());
        AuthResponse authResponse = response.body().as(AuthResponse.class);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(authResponse.getToken(), nullValue());
        assertThat(authResponse.getReason(), equalTo(getProperty("error.bad.credentials")));
    }
}
