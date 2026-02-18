package com.my.ecommerce.global;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {

  private boolean success;
  private T data;
  private Map<String, String> errors;

  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T>builder()
                      .success(true)
                      .data(data)
                      .errors(null)
                      .build();
  }

  public static ApiResponse<?> fail(Map<String, String> errors) {
    return ApiResponse.builder()
                      .success(false)
                      .data(null)
                      .errors(errors)
                      .build();
  }
}
