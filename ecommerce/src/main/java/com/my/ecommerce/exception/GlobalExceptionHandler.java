package com.my.ecommerce.exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.my.ecommerce.global.ApiResponse;

import org.springframework.web.bind.MethodArgumentNotValidException;


@RestControllerAdvice
public class GlobalExceptionHandler {

  //비지니스 예외처리
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
    Map<String, String> errors = new HashMap<>();
    errors.put("email", e.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.fail(errors));
  }

  //유효성 예외처리
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
    Map<String, String> errors = new LinkedHashMap<>();

    e.getBindingResult()
      .getFieldErrors()
      .forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage()));  
    return ResponseEntity.badRequest().body(ApiResponse.fail(errors));
  }

  //로그인 인증 실패시 예외처리
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiResponse<?>> handleBadCredentials() {

    Map<String, String> errors = Map.of(
      "login", "이메일 또는 비밀번호가 올바르지 않습니다."
    );

    return ResponseEntity.badRequest().body(ApiResponse.fail(errors));
  }


}
