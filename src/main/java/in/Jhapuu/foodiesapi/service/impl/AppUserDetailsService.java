package in.Jhapuu.foodiesapi.service.impl;

import in.Jhapuu.foodiesapi.entity.UserEntity;
import in.Jhapuu.foodiesapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      UserEntity user =  userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
     return new User(user.getEmail(),user.getPassword(), Collections.emptyList());
    }
}
