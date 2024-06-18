package com.example.jwt_project.service;

import com.example.jwt_project.entity.AppUser;
import com.example.jwt_project.entity.Roles;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser appUser);
    Roles saveRole(Roles roles);

    void addRoleToUser(String role, String email);
    AppUser getUser(String email);
    List<AppUser> getUsers();

}
