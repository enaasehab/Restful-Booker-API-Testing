package com.restfulBooker.apisManager.apis;


import com.restfulBooker.apisManager.base.Specs;
import com.restfulBooker.apisManager.data.Routes;
import com.restfulBooker.apisManager.models.AuthRequest;
import io.restassured.response.Response;


public class AuthApi {

        public static Response createToken(AuthRequest authRequest) {
            return Specs.getRequestSpec()
                    .body(authRequest)
                    .when()
                    .post(Routes.AUTH_ROUTE)
                    .then()
                    .log().all()
                    .extract().response();
        }

}
