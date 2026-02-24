package com.my.ecommerce.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.my.ecommerce.global.config.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;

  public LoginResponse login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    String token = jwtProvider.createToken(authentication);
    return new LoginResponse(token, "Bearer");
  }

}
