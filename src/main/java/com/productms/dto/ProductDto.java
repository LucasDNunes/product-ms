package com.productms.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto implements Serializable {

  private static final long serialVersionUID = 441686207147213390L;
  private String id;
  private String name;
  private String description;
  private Double price;
}
