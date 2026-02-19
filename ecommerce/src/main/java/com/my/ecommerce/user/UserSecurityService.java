package com.my.ecommerce.user;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    com.my.ecommerce.user.User user = userRepository.findByEmail(username)
        .orElseThrow(() -> 
            new UsernameNotFoundException("사용자를 찾을 수 없습니다"));

    List<GrantedAuthority> authorities = 
      List.of(new SimpleGrantedAuthority(user.getRole().getValue()));

    return new User(user.getEmail(), user.getPassword(), authorities);
  }
}
