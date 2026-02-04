package com.my.ecommerce.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.my.ecommerce.exception.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  //회원가입
  public User create(String email, String password, String name) {
    User user = new User();
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    user.setName(name);
    this.userRepository.save(user);

    return user;
  }

  //유저 조회
  public User getUser(String email) {
    Optional<User> user = this.userRepository.findByEmail(email);
    if(user.isPresent()){
      return user.get();
    }else {
      throw new DataNotFoundException("user not found");
    }
  }

}
