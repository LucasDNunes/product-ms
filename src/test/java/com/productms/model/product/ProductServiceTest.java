package com.productms.model.product;

import com.productms.exception.InvalidPriceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

  @Autowired
  private ProductService productService;

  @AfterEach
  void cleanUpEach() {
    productService.listAll().forEach(p -> productService.delete(p.getId()));
  }

  @Test
  void should_saveProduc_when_correctObject() {
    Product product = Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build();

    Product save = productService.save(product);

    assertNotNull(save.getId(), "shold has id");
    assertEquals(save.getName(), product.getName(), "shold be the same name");
  }

  @Test
  void should_findById_when_produtExists() {
    Product product = productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    Product productById = productService.findById(product.getId());

    assertNotNull(productById, "should find product");
    assertEquals(productById.getId(), product.getId(), "should be the same id");

  }

  @Test
  void should_findAll_when_existsProduct() {
    productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    productService.save(Product.builder()
            .name("product 2 test")
            .description("description of product 2")
            .price(19.99)
            .build());

    List<Product> products = productService.listAll();

    assertFalse(products.isEmpty(), "should has 2 products");
    assertEquals(2 , products.size(), "should has 2 products");

  }

  @Test
  void should_delete_when_existsProduct() {
    Product product = productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    productService.delete(product.getId());
    assertThrows(IllegalArgumentException.class, () -> { productService.findById(product.getId()); });
  }

  @Test
  void should_updateProduct_whenExistProduct() {
    Product product = productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    Product productToUpdate = Product.builder()
            .name("product test")
            .description("description of product")
            .price(11.30)
            .build();

    Product PrpductUpdate = productService.update(productToUpdate, product.getId());

    assertNotNull(productToUpdate);
    assertEquals(PrpductUpdate.getId(), product.getId());
    assertEquals(PrpductUpdate.getPrice().doubleValue(), productToUpdate.getPrice().doubleValue());
  }

  @Test
  void should_searchAndFindProductByQ_whenExists() {
    Product product = productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    productService.save(Product.builder()
            .name("product")
            .description("description of product")
            .price(9.0)
            .build());

    List<Product> products = productService.search("test", null, null);

    assertFalse(products.isEmpty(), "should not be empty");
    assertEquals(1, products.size(), "should find the first product that was saved");
    assertEquals(product.getId(), products.get(0).getId(), "confirming that the product is the same as it should be found");
  }

  @Test
  void should_searchAndListall_whenallParametersIsNull() {
    productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    productService.save(Product.builder()
            .name("product")
            .description("description of product")
            .price(9.0)
            .build());

    List<Product> products = productService.search(null, null, null);

    assertFalse(products.isEmpty(), "should not be empty");
    assertEquals(2, products.size(), "should list all products");
  }

  @Test
  void should_searchAndFindByMinPrice_whenExists() {
    productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    Product product = productService.save(Product.builder()
            .name("product")
            .description("description of product")
            .price(19.0)
            .build());

    List<Product> products = productService.search(null, 10.0, null);

    assertFalse(products.isEmpty(), "should not be empty");
    assertEquals(1, products.size(), "should find the second product that was saved");
    assertEquals(product.getId(), products.get(0).getId(), "confirming that the product is the same as it should be found");
  }

  @Test
  void should_searchAndFindByMaxPrice_whenExists() {
    Product product = productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    productService.save(Product.builder()
            .name("product")
            .description("description of product")
            .price(19.0)
            .build());

    List<Product> products = productService.search(null, null, 11.0);

    assertFalse(products.isEmpty(), "should not be empty");
    assertEquals(1, products.size(), "should find the first product that was saved");
    assertEquals(product.getId(), products.get(0).getId(), "confirming that the product is the same as it should be found");
  }

  @Test
  void should_searchAndFindByQAndMaxPrice_whenExists() {
    Product product = productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    productService.save(Product.builder()
            .name("product")
            .description("description of product")
            .price(19.0)
            .build());

    List<Product> products = productService.search("test", null, 11.0);

    assertFalse(products.isEmpty(), "should not be empty");
    assertEquals(1, products.size(), "should find the first product that was saved");
    assertEquals(product.getId(), products.get(0).getId(), "confirming that the product is the same as it should be found");
  }

  @Test
  void should_searchAndFindByQAndMinPrice_whenExists() {
    productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    Product product = productService.save(Product.builder()
            .name("product")
            .description("description of product")
            .price(19.0)
            .build());

    List<Product> products = productService.search("product", 11.0, null);

    assertFalse(products.isEmpty(), "should not be empty");
    assertEquals(1, products.size(), "should find the second product that was saved");
    assertEquals(product.getId(), products.get(0).getId(), "confirming that the product is the same as it should be found");
  }

  @Test
  void should_searchAndFindByMinPriceAndMaxPrice_whenExists() {
    Product product = productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    productService.save(Product.builder()
            .name("product")
            .description("description of product")
            .price(19.0)
            .build());

    List<Product> products = productService.search(null, 8.0, 10.1);

    assertFalse(products.isEmpty(), "should not be empty");
    assertEquals(1, products.size(), "should find the first product that was saved");
    assertEquals(product.getId(), products.get(0).getId(), "confirming that the product is the same as it should be found");
  }

  @Test
  void should_searchAndFindByQAndMinPriceAndMaxPrice_whenExists() {
    Product product = productService.save(Product.builder()
            .name("product test")
            .description("description of product")
            .price(9.99)
            .build());

    productService.save(Product.builder()
            .name("product")
            .description("description of product")
            .price(19.0)
            .build());

    List<Product> products = productService.search("product", 8.0, 10.1);

    assertFalse(products.isEmpty(), "should not be empty");
    assertEquals(1, products.size(), "should find the first product that was saved");
    assertEquals(product.getId(), products.get(0).getId(), "confirming that the product is the same as it should be found");
  }

  @Test
  void should_error_when_trySavePriceNegative() {
    Product product = Product.builder()
            .name("product test")
            .description("description of product")
            .price(-9.99)
            .build();

    InvalidPriceException invalidPriceException = assertThrows(InvalidPriceException.class, () -> {
      productService.save(product);
    });

    assertEquals("Invalid price. The price need to be positive", invalidPriceException.getMessage());
  }




}