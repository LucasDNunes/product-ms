package com.productms.exception;

import com.productms.dto.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@Slf4j
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<CustomResponse> handleProductNotFoundException(Exception ex) {
    CustomResponse customResponse =  new CustomResponse();
    customResponse.setMessage(ex.getMessage());
    customResponse.setStatus_code(HttpStatus.NOT_FOUND.value());
    log.error("Product not found", ex);
    return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidPriceException.class)
  public ResponseEntity<CustomResponse> handleInvalidPriceexception(Exception ex) {
    CustomResponse customResponse =  new CustomResponse();
    customResponse.setMessage(ex.getMessage());
    customResponse.setStatus_code(HttpStatus.BAD_REQUEST.value());
    log.error("Invalid price", ex);
    return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    CustomResponse customResponse =  new CustomResponse();
    customResponse.setMessage(ex.getMessage());
    customResponse.setStatus_code(HttpStatus.BAD_REQUEST.value());
    log.error("Json error", ex);
    return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    CustomResponse customResponse =  new CustomResponse();
    customResponse.setMessage(ex.getMessage());
    customResponse.setStatus_code(HttpStatus.BAD_REQUEST.value());
    log.error("Parameter error", ex);
    return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
  }

}
