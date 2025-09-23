// src/test/java/com/techstore/testsupport/AuthSupport.java
package com.techstore.testsupport;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class AuthSupport {
  private static String userJwt;

  private AuthSupport() {}

  /** Obtiene (y cachea) un JWT de USER. */
  public static String userJwt() {
    if (userJwt != null) return userJwt;
    String baseUrl = System.getProperty("baseUrl", "http://localhost:9090");
    String user = System.getProperty("username", "demo_user1");
    String pass = System.getProperty("password", "demo_pass1");

    // Permite inyectar el token ya generado (plan B)
    String injected = System.getProperty("jwt");
    if (injected != null && !injected.isBlank()) {
      userJwt = injected;
      return userJwt;
    }

    var res =
      given()
        .baseUri(baseUrl)
        .contentType(ContentType.JSON)
        .header("Accept","application/json")
        .body("""
          {"username":"%s","password":"%s"}
        """.formatted(user, pass))
      .when()
        .post("/api/auth/login")
      .then()
        .statusCode(200)
        .extract().response();

    userJwt = res.jsonPath().getString("token");
    if (userJwt == null || userJwt.isBlank()) {
      userJwt = res.jsonPath().getString("accessToken");
    }
    if (userJwt == null || userJwt.isBlank()) {
      throw new IllegalStateException("Login USER sin token en la respuesta: " + res.asString());
    }
    return userJwt;
  }

  /** Spec JSON con Authorization para USER. */
  public static RequestSpecification userSpec() {
    return new RequestSpecBuilder()
      .setContentType(ContentType.JSON)
      .addHeader("Authorization", "Bearer " + userJwt())
      .build();
  }

  
}
