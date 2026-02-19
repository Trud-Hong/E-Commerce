package com.my.ecommerce.user;

import lombok.Getter;

@Getter
public enum UserRole {
  ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), SELLER("ROLE_SELLER");

  UserRole(String value) {
    this.value = value;
  }

  private String value;

}
