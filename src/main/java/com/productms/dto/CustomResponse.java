package com.productms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CustomResponse implements Serializable {
  private static final long serialVersionUID = 7727735804132338276L;

  private Integer status_code;
  private String message;
}
