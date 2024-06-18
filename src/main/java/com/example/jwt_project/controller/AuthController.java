package com.example.jwt_project.controller;

import com.example.jwt_project.dto.AuthResponseDto;
import com.example.jwt_project.dto.LoginDto;
import com.example.jwt_project.dto.RegisterDto;
import com.example.jwt_project.entity.AppUser;
import com.example.jwt_project.entity.Roles;
import com.example.jwt_project.repository.AppUserRepository;
import com.example.jwt_project.repository.RoleRepository;
import com.example.jwt_project.security.JwtGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

        if (appUserRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(registerDto.getUsername());
        appUser.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Roles roles = roleRepository.findByRole(registerDto.getRole()).get();
        appUser.setRoles(Collections.singletonList(roles));

        appUserRepository.save(appUser);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AppUser loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(loginDto);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }
}
