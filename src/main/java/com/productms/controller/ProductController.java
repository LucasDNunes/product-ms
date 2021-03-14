package com.productms.controller;

import com.productms.dto.ProductDto;
import com.productms.mapper.ProductMapper;
import com.productms.model.product.Product;
import com.productms.model.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductDto> listAll() {
    return productService.listAll()
            .stream().map(ProductMapper::toDto).collect(Collectors.toList());
  }

  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDto findById(@PathVariable String id) {
    Product product = productService.findById(id);
    return ProductMapper.toDto(product);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDto save(@RequestBody ProductDto productDto) {
    return ProductMapper.toDto(productService.save(ProductMapper.toEntity(productDto)));
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable String id) {
    productService.delete(id);
  }

  @PutMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDto update(@PathVariable String id, @RequestBody ProductDto productDto) {
    return ProductMapper.toDto(productService.update(ProductMapper.toEntity(productDto), id));
  }

  @GetMapping(value = "/search")
  public List<ProductDto> search(@RequestParam(required = false) String q,
                                 @RequestParam(required = false, name = "min_price") Double minPrice,
                                 @RequestParam(required = false, name = "max_price") Double maxPrice) {
    return productService.search(q, minPrice, maxPrice)
            .stream().map(ProductMapper::toDto).collect(Collectors.toList());
  }



}
