package com.productms.model.product;

import java.util.List;

public interface ProductService {

  List<Product> listAll();

  Product findById(String id);

  Product save(Product product);

  Product update(Product product, String id);

  void delete(String id);


}
