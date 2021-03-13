package com.productms.model.product;

import com.productms.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public List<Product> listAll() {
    return productRepository.findAll();
  }

  @Override
  public Product findById(String id) {
    Objects.requireNonNull(id);
    return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found wih id" + id));
  }

  @Override
  public Product save(Product product) {
    if (product.getId() == null) {
      product.setId(UUID.randomUUID().toString());
    }
    return productRepository.save(product);
  }

  @Override
  public Product update(Product product, String id) {
    Objects.requireNonNull(id);
    Product productById = this.findById(id);
    product.setId(id);
    Product merge = ProductMapper.merge(product, productById);
    return this.save(merge);
  }

  @Override
  public void delete(String id) {
    Objects.requireNonNull(id);
    Product product = this.findById(id);
    productRepository.delete(product);
  }
}
