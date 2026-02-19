package com.my.ecommerce.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.ecommerce.global.ApiResponse;
import com.my.ecommerce.user.UserCreateForm;
import com.my.ecommerce.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

//   @GetMapping("/signup")
//   public String signup(UserCreateForm userCreateForm) {
//       return new String();
//   }

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<Long>> signup(
          @Valid @RequestBody UserCreateForm userCreateForm) {
            
      Long userId = userService.create(userCreateForm);
      
      return ResponseEntity.ok(ApiResponse.success(userId));
  }

  
  
  

}
