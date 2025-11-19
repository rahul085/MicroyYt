package com.example.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDto {
    private String username;
    private String password;
    private Set<String> roles=new HashSet<>();
}
