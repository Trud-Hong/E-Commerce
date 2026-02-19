package com.my.ecommerce.user;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
  
  private final UserRepository userRepository;
  
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
    User user = userRepository.findByEmail(email)
                .orElseThrow(() -> 
                        new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
      
    return new UserDetail(user);
  }

}
