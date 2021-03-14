package com.productms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends IllegalArgumentException {

  private static final long serialVersionUID = 7694966813703546113L;

  public ProductNotFoundException(String id) {
    super("Product not found with id: " + id);
  }
}
