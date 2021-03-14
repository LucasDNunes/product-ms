package com.productms.exception;

public class InvalidPriceException extends IllegalArgumentException {

  private static final long serialVersionUID = 1963809331765581130L;

  public InvalidPriceException() {
    super("Invalid price. The price need to be positive");
  }
}
