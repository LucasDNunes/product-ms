package com.productms.mapper;

import com.productms.dto.ProductDto;
import com.productms.model.product.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

  private static final ModelMapper mapper = new ModelMapper();

  public static ProductDto toDto(Product product) {
    return mapper.map(product, ProductDto.class);
  }

  public static Product toEntity(ProductDto productDto) {
    return mapper.map(productDto, Product.class);
  }

  public static Product merge(Product toMerge, Product origin) {
    mapper.map(toMerge, origin);
    return origin;
  }
}
