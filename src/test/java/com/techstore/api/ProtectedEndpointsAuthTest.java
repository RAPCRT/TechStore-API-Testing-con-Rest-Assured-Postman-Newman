// src/test/java/com/techstore/api/ProtectedEndpointsAuthTest.java
package com.techstore.api;

import com.techstore.testsupport.AuthSupport;
import com.techstore.testsupport.RestAssuredBase;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProtectedEndpointsAuthTest extends RestAssuredBase {

    static final String PRODUCTS = "/api/v1/products";

    /** 401: sin token no entra. */
    @Test
    void post_sin_token__401() {
        given()
                .spec(REQ_JSON)
                .body("""
                          {"nombre":"Test","descripcion":"X","precio":1.0,"stock":1,"id_categoria":1}
                        """)
                .when().post(PRODUCTS)
                .then()
                .statusCode(401);
    }

    /** 403: con token de USER, pero sin rol suficiente. */
    @Test
    void post_con_token_user__2xx() {
        given()
                .spec(AuthSupport.userSpec())
                .body("""
                          {"nombre":"Test USER","descripcion":"X","precio":1.0,"stock":1,"id_categoria":1}
                        """)
                .when().post(PRODUCTS)
                .then()
                .statusCode(anyOf(is(201), is(200)))
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("nombre", equalTo("Test USER"));
    }

    /** Token inválido/expirado → 401. */
    @Test
    void token_invalido__401() {
        given()
                .spec(REQ_JSON)
                .header("Authorization", "Bearer " + "ey.Invalid.Token")
                .when().post(PRODUCTS)
                .then().statusCode(401);
    }
}
