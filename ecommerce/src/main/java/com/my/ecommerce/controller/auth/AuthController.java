package com.my.ecommerce.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.ecommerce.auth.LoginRequest;
import com.my.ecommerce.auth.LoginResponse;
import com.my.ecommerce.config.JwtProvider;
import com.my.ecommerce.global.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
    System.out.println("===로그인 컨트롤러 진입===");
    try {
      Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(), 
        request.getPassword()
      )
    );

    String token = jwtProvider.createToken(authentication);

    LoginResponse response = new LoginResponse(token, "Bearer");

    return ResponseEntity.ok(ApiResponse.success(response));  
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    
  }

}
