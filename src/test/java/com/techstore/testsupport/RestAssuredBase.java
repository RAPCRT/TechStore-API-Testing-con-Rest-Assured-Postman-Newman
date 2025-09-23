package com.techstore.testsupport;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class RestAssuredBase {
  protected static RequestSpecification REQ_JSON;

  @BeforeAll
  static void setup() {
    RestAssured.baseURI = System.getProperty("baseUrl", "http://localhost:9090");
    REQ_JSON = new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .log(LogDetail.METHOD)
        .log(LogDetail.URI)
        .log(LogDetail.BODY)
        .build();
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }
}
