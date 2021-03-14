package com.productms.model.product;

import com.productms.exception.InvalidPriceException;
import com.productms.exception.ProductNotFoundException;
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
            .orElseThrow(() -> new ProductNotFoundException(id));
  }

  @Override
  public Product save(Product product) {
    if (product.getId() == null) {
      product.setId(UUID.randomUUID().toString());
    }
    if (product.getPrice() < 0) {
      throw new InvalidPriceException();
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

  @Override
  public List<Product> search(String q, Double minPrice, Double maxPrice) {
    if (q == null && maxPrice == null && minPrice == null) {
      return this.listAll();
    } else if (maxPrice == null && minPrice == null) {
      return productRepository.findByNameContainingOrDescriptionContaining(q, q);
    } else if (q == null && minPrice == null) {
      return productRepository.findByPriceLessThanEqual(maxPrice);
    } else if (q == null && maxPrice == null) {
      return productRepository.findByPriceGreaterThanEqual(minPrice);
    } else if (minPrice == null) {
      return productRepository.findByNameContainingOrDescriptionContainingAndPriceLessThanEqual(q, maxPrice);
    } else if (maxPrice == null) {
      return productRepository.findByNameContainingOrDescriptionContainingAndPriceGreaterThanEqual(q, minPrice);
    } else if (q == null){
      return productRepository.findByPriceLessThanEqualAndPriceGreaterThanEqual(maxPrice, minPrice);
    } else {
      return productRepository.findByNameContainingOrDescriptionContainingAndPriceLessThanEqualAndPriceGreaterThanEqual(q, maxPrice, minPrice);
    }
  }
}
