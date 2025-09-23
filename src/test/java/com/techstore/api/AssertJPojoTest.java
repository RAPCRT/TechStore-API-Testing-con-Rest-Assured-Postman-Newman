// src/test/java/com/techstore/api/AssertJPojoTest.java
package com.techstore.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techstore.model.Product;
import com.techstore.testsupport.RestAssuredBase;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class AssertJPojoTest extends RestAssuredBase {

  static final String PRODUCTS = "/api/v1/products";
  ObjectMapper om = new ObjectMapper();

  @Test
  void validar_lista_con_assertj_avanzado() throws Exception {
    String json =
      given().spec(REQ_JSON)
      .when().get(PRODUCTS)
      .then().statusCode(200)
      .extract().asString();

    List<Product> list = om.readValue(json, new TypeReference<>() {});

    // AssertJ: condiciones, filtrado, mapeos
    Assertions.assertThat(list)
      .isNotEmpty()
      .allSatisfy(p -> {
        Assertions.assertThat(p.id).isNotNull().isPositive();
        Assertions.assertThat(p.nombre).isNotBlank();
        Assertions.assertThat(p.precio).isNotNull().isGreaterThanOrEqualTo(0.0);
      })
      // al menos uno con stock > 0
      .anySatisfy(p -> Assertions.assertThat(p.stock).isNotNull().isGreaterThan(0));

    // filtrar y proyectar con tuples
    Assertions.assertThat(list)
      .filteredOn(p -> p.category != null && p.category.id != null)
      .extracting(p -> p.category.id, p -> p.category.nombre)
      .isNotEmpty();

    // Soft assertions (todas las verificaciones juntas)
    SoftAssertions softly = new SoftAssertions();
    Product first = list.get(0);
    softly.assertThat(first.nombre).as("nombre").isNotBlank();
    softly.assertThat(first.precio).as("precio").isBetween(0.0, 100000.0);
    softly.assertThat(first.stock).as("stock").isGreaterThanOrEqualTo(0);
    softly.assertAll();
  }

  @Test
  void validar_un_producto_por_id_con_assertj() throws Exception {
    String json =
      given().spec(REQ_JSON)
      .when().get(PRODUCTS)
      .then().statusCode(200)
      .extract().asString();

    List<Product> list = om.readValue(json, new TypeReference<>() {});
    Product p = list.stream().filter(it -> it.id == 1L).findFirst().orElse(list.get(0));

    Assertions.assertThat(p)
      .isNotNull()
      .extracting(x -> x.id, x -> x.nombre, x -> x.precio, x -> x.stock)
      .satisfies(tuple -> {
        // ejemplo: validar tipos/rangos
        Assertions.assertThat((Long) tuple.get(0)).isPositive();
        Assertions.assertThat((String) tuple.get(1)).isNotBlank();
        Assertions.assertThat((Double) tuple.get(2)).isGreaterThanOrEqualTo(0.0);
        Assertions.assertThat((Integer) tuple.get(3)).isGreaterThanOrEqualTo(0);
      });
  }
}
