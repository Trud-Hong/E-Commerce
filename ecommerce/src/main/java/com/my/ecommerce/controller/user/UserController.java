package com.my.ecommerce.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.ecommerce.user.UserCreateForm;
import com.my.ecommerce.user.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @GetMapping("/signup")
  public String signup(UserCreateForm userCreateForm) {
      return new String();
  }

  @PostMapping("/signup")
  public String signup(@RequestBody UserCreateForm userCreateForm, BindingResult bindingResult) {
      //TODO: process POST request
      
      return "entity";
  }
  
  

}
