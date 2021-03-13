package com.productms.controller;

import com.productms.ProductMsApplication;
import com.productms.dto.ProductDto;
import com.productms.model.product.Product;
import com.productms.model.product.ProductService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.JsonConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.config.JsonPathConfig;
import org.apache.http.HttpStatus;
import org.apache.http.message.BasicHeader;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProductMsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

  private static final String PRODUCTS = "/products";
  @LocalServerPort
  private int port;

  @Autowired
  private ProductController productController;

  @Autowired
  private ProductService productService;

  private ProductService mockProductService;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    RestAssured.config = RestAssured.config().jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE));
    Headers headers =  new Headers(Arrays.asList(new Header("Content-Type","application/json"), new Header("Accept","application/json" )));
    RestAssured.requestSpecification = new RequestSpecBuilder().build().headers(headers);
    this.mockProductService = Mockito.mock(ProductService.class);
    ReflectionTestUtils.setField(productController, "productService", this.mockProductService);
  }

  @AfterEach
  void CleanContext() {
    ReflectionTestUtils.setField(productController, "productService", this.productService);
  }

  @Test
  void shoud_listAll_when_existsProducts() {
    when(this.mockProductService.listAll())
            .thenReturn(Collections.singletonList(Product.builder().id("1").name("name").description("description").build()));

    given()
            .when()
            .get(PRODUCTS)
            .then().statusCode(HttpStatus.SC_OK)
            .body("$.size()", equalTo(1))
            .body("[0].id", equalTo("1"));
  }

  @Test
  void should_findById_when_existsProduct() {
    when(this.mockProductService.findById("any-id"))
            .thenReturn(Product.builder()
                    .id("any-id")
                    .name("name")
                    .description("description product")
                    .price(9.80)
                    .build());

    given()
            .when()
            .get(PRODUCTS + "/{id}", "any-id")
            .then().statusCode(HttpStatus.SC_OK)
            .body("id", equalTo("any-id"))
            .body("name", equalTo("name"))
            .body("description", equalTo("description product"))
            .body("price", equalTo(9.80));

  }

  @Test
  void should_saveProduct() {
    when(this.mockProductService.save(any())).thenReturn(Product.builder().id("qwerty").build());

    with().body(ProductDto.builder().name("asdas").price(1.1).description("description").build())
            .when().post(PRODUCTS)
            .then().statusCode(HttpStatus.SC_CREATED)
            .body("id", equalTo("qwerty"));
  }

  @Test
  void should_updateProduct() {
    when(this.mockProductService.update(any(), any())).thenReturn(Product.builder().id("id-update").build());

    with().body(ProductDto.builder().name("name").description("description").build())
            .when()
            .put(PRODUCTS + "/qweqw")
            .then().statusCode(HttpStatus.SC_OK)
            .body("id", Matchers.equalTo("id-update"));
  }

}