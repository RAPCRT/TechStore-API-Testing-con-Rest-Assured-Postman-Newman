
package com.techstore.api;

import com.techstore.testsupport.RestAssuredBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;

public class HeadersAndBodyHamcrestTest extends RestAssuredBase {

  static final String PRODUCTS = "/api/v1/products";

  @Test
  void headers_basicos_y_no_cache() {
    given().spec(REQ_JSON)
        .when().get(PRODUCTS)
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        // cabeceras de seguridad/cache típicas (acepta que puedan faltar)
        .header("X-Content-Type-Options", anyOf(nullValue(), equalToIgnoringCase("nosniff")))
        .header("Cache-Control", containsString("no-cache"));
  }

  @Test
  void cuerpo_lista_y_campos_basicos() {
    given().spec(REQ_JSON)
        .when().get(PRODUCTS)
        .then()
        .statusCode(200)
        .contentType(containsString("application/json"))
        .body("$", isA(java.util.List.class))
        // al menos un elemento
        .body("size()", greaterThanOrEqualTo(1))
        // todos los precios >= 0
        .body("precio", everyItem(greaterThanOrEqualTo(0f)))
        // todos tienen id y nombre no vacío
        .body("id", everyItem(notNullValue()))
        .body("nombre", everyItem(allOf(notNullValue(), not(isEmptyOrNullString()))))
        // algún nombre contiene "Laptop" o "Teclado"
        .body("nombre", hasItem(anyOf(containsStringIgnoringCase("Laptop"), containsStringIgnoringCase("Teclado"))));
  }

  // HeadersAndBodyHamcrestTest.java
  @Test
  void filtrar_y_validar_un_item_por_id() {
    given().spec(REQ_JSON)
        .when().get(PRODUCTS)
        .then()
        .statusCode(200)
        // al menos un item
        .body("size()", greaterThan(0))
        // el primer item tiene id y nombre válidos
        .body("[0].id", notNullValue())
        .body("[0].nombre", not(isEmptyOrNullString()))
        // ejemplo de validación por nombre conocido (si existe en tu seed):
        .body("findAll { it.nombre =~ /(?i)(Laptop|Teclado)/ }.size()", greaterThan(0));
  }

}
