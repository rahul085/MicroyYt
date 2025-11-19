package com.example.auth_service.Controller;

import com.example.auth_service.dto.LoginRequestDto;
import com.example.auth_service.dto.LoginResponseDto;
import com.example.auth_service.dto.UserRegisterDto;
import com.example.auth_service.entity.Users;
import com.example.auth_service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.webauthn.api.UserVerificationRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Users> createUsers(@RequestBody UserRegisterDto dto){
        return new ResponseEntity<>(service.registerUser(dto), HttpStatus.OK);


    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto){


        return new ResponseEntity<>(service.login(dto),HttpStatus.OK);
    }

    @GetMapping("/activeToken")
    public ResponseEntity<String> generateActiveToken(@RequestParam String refreshToken){
        return new ResponseEntity<>(service.generateActiveToken(refreshToken),HttpStatus.OK);
    }
}
