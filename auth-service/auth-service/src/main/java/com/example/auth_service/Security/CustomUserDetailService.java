package com.example.auth_service.Security;

import com.example.auth_service.entity.Users;
import com.example.auth_service.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
@Component

public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user= userRepository.findByUsername(username).orElseThrow(()
        -> new UsernameNotFoundException("User not found with name: "+username));

        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoleName()))
                .collect(Collectors.toSet());

        return new User(user.getUsername(),user.getPassword(),authorities);


    }
}
