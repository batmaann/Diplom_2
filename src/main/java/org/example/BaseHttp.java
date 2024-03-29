package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

abstract public class BaseHttp {
    public ValidatableResponse doPostRequest(String baseUrl, Object body) {
        RequestSpecification request = given(baseRequest());
        request.body(body);
        return request.post(baseUrl).then();

    }


    public ValidatableResponse doDeleteRequest(String baseUrl, String token) {
        RequestSpecification request = given(baseRequest());
        //request.body(body);
        return request.given().auth().oauth2(token).delete(baseUrl).then();
    }

    public ValidatableResponse doGetRequest(String baseUrl) {
        RequestSpecification request = given(baseRequest());
        return request.get(baseUrl).then();
    }


    public ValidatableResponse doGetRequestUser(String baseUrl, String email, String password, String name, String token) {
        RequestSpecification request = given(baseRequest());
        //request.body(body);
        return request.given().auth().oauth2(token).get(baseUrl).then();

    }


    public ValidatableResponse doPostRequest(String baseUrl, Object body, String token) {
        RequestSpecification request = given(baseRequest());
        request.body(body);
        return request.given().auth().oauth2(token).post(baseUrl).then();
    }


    private static RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setRelaxedHTTPSValidation()
                .build();
    }
}
