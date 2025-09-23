// src/test/java/com/techstore/testsupport/AuthClient.java
package com.techstore.testsupport;

import io.restassured.http.ContentType;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthClient {

  public static String loginAndGetToken(String username, String password) {
    final String baseUrl   = System.getProperty("baseUrl", "http://localhost:9090");
    final String loginPath = System.getProperty("login.path", "/api/auth/login");
    final String tokenKey  = System.getProperty("login.tokenKey", "token"); // usa "accessToken" si tu API devuelve así

    var res =
      given()
        .baseUri(baseUrl)
        .basePath("") // usamos path completo en post()
        .header("Accept", "application/json")         // igual que Postman
        .contentType(ContentType.JSON)                // application/json
        .body(Map.of(                                // evita errores de string
          "username", username,
          "password", password
        ))
        .log().all()
      .when()
        .post(loginPath)                              // /api/auth/login
      .then()
        .log().all()
        .extract().response();

    if (res.statusCode() != 200) {
      throw new IllegalStateException("Login fallo: HTTP " + res.statusCode()
        + " | body=" + res.asString());
    }

    String token = res.jsonPath().getString(tokenKey);
    if (token == null || token.isBlank()) {
      token = res.jsonPath().getString("accessToken"); // fallback
    }
    if (token == null || token.isBlank()) {
      throw new IllegalStateException("No se encontró el campo de token ("+tokenKey+"/accessToken) en: " + res.asString());
    }
    return token;
  }
}
