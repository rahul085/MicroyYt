package com.example.auth_service.service;

import com.example.auth_service.Security.CustomUserDetailService;
import com.example.auth_service.Security.JwtUtil;
import com.example.auth_service.dto.LoginRequestDto;
import com.example.auth_service.dto.LoginResponseDto;
import com.example.auth_service.dto.UserRegisterDto;
import com.example.auth_service.entity.Users;
import com.example.auth_service.exception.RoleNotFoundException;
import com.example.auth_service.repository.RoleRepository;
import com.example.auth_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {
private final UserRepository userRepository;
private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService service;
    private  final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

public Users registerUser(UserRegisterDto dto){
    Users user=new Users();
    user.setUsername(dto.getUsername());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setRoles(dto.getRoles().stream().map(role->roleRepository.findByRoleName(role)
            .orElseThrow(()-> new RoleNotFoundException("Role with name: "+role+" not found!")))
            .collect(Collectors.toSet()));
    return userRepository.save(user);
}

public LoginResponseDto login(LoginRequestDto dto){
    LoginResponseDto loginResponseDto=new LoginResponseDto();
    Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
    UserDetails userDetails= (UserDetails) authenticate.getPrincipal();
    loginResponseDto.setActiveToken(jwtUtil.generateActiveJwtToken(userDetails));
    loginResponseDto.setRefreshToken(jwtUtil.generateRefreshToken(userDetails));
    return loginResponseDto;
}

public String generateActiveToken(String refreshToken){
    String username = jwtUtil.extractUsername(refreshToken);
    UserDetails userDetails = service.loadUserByUsername(username);
    return jwtUtil.generateActiveJwtToken(userDetails);
}

}
