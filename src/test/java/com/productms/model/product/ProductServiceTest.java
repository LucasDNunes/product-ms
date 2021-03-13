package com.productms.model.product;

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
    assertEquals(products.size(), 2, "should has 2 products");

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


}