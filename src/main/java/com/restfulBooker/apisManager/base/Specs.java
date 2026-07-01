package com.restfulBooker.apisManager.base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.restfulBooker.dataReader.PropertyReader.getProperty;
import static io.restassured.RestAssured.given;


public class Specs {

    public static RequestSpecification getRequestSpec() {
        return given()
                .baseUri(getProperty("baseUrl"))
                .contentType(ContentType.JSON)
                .log().all();
    }
}