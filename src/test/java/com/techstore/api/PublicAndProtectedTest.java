// src/test/java/com/techstore/api/PublicAndProtectedTest.java
package com.techstore.api;

import com.techstore.testsupport.AuthClient;
import com.techstore.testsupport.RestAssuredBase;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PublicAndProtectedTest extends RestAssuredBase {

  static String JWT;
  static final String PRODUCTS = "/api/v1/products";

  @Test @Order(1)
  void publico__get_products_sin_token__200_lista() {
    given().spec(REQ_JSON)
    .when().get(PRODUCTS)
    .then()
      .statusCode(200)
      .contentType(containsString("application/json"))
      .body("$", isA(java.util.List.class));
  }

  @Test @Order(2)
  void protegido__post_products_sin_token__401() {
    given().spec(REQ_JSON)
      .body("""
        {"nombre":"Test","descripcion":"X","precio":1.0,"stock":1,"id_categoria":1}
      """)
    .when().post(PRODUCTS)
    .then().statusCode(401); // sin token debe pedir auth
  }

  @Test @Order(3)
  void protegido__post_products_con_token_user__403_o_201_si_es_admin() {
    // 1) obtener JWT con tus credenciales reales
    var user = System.getProperty("username", "demo_user1");
    var pass = System.getProperty("password", "demo_pass1");
    JWT = AuthClient.loginAndGetToken(user, pass);  // si no loguea, lanza y falla el test

    // 2) intentar crear (si el usuario es USER normal → 403; si es ADMIN → 201/200)
    given().spec(REQ_JSON)
      .header("Authorization", "Bearer " + JWT)
      .body("""
        {"nombre":"Test","descripcion":"X","precio":1.0,"stock":1,"id_categoria":1}
      """)
    .when().post(PRODUCTS)
    .then()
      .statusCode(anyOf(is(403), is(201), is(200)))
      .contentType(anyOf(containsString("application/json"), anything()));
  }
}
