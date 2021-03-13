package com.productms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

  private String id;
  private String name;
  private String description;
  private Double price;
}
