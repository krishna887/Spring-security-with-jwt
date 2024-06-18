package com.example.jwt_project.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String role;
}