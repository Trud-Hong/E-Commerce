package com.my.ecommerce.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.ecommerce.exception.BusinessException;
import com.my.ecommerce.exception.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  //회원가입
  @Transactional
  public Long create(UserCreateForm userCreateForm) {
    if(userRepository.findByEmail(userCreateForm.getEmail()).isPresent()){
      throw new BusinessException("email", "이미 존재하는 이메일 입니다.");
    }
    if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
      throw new BusinessException("password2","비밀번호가 일치하지 않습니다.");
    }
    User user = User.builder()
                    .email(userCreateForm.getEmail())
                    .password(passwordEncoder.encode(userCreateForm.getPassword1()))
                    .name(userCreateForm.getName())
                    .build();

    userRepository.save(user);

    return user.getId();
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
