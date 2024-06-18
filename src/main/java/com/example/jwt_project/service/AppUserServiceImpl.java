//package com.example.jwt_project.service;
//
//import com.example.jwt_project.entity.AppUser;
//import com.example.jwt_project.entity.Roles;
//import com.example.jwt_project.repository.AppUserRepository;
//import com.example.jwt_project.repository.RoleRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class AppUserServiceImpl implements AppUserService{
//    private final AppUserRepository appUserRepository;
//    private final RoleRepository roleRepository;
//    private  final JwtService jwtService;
//
//
//    @Override
//    public AppUser saveUser(AppUser appUser) {
//        return appUserRepository.save(appUser);
//    }
//
//    @Override
//    public Roles saveRole(Roles roles) {
//        return roleRepository.save(roles);
//    }
//
//    @Override
//    public void addRoleToUser(String email, String role) {
//        AppUser user= appUserRepository.findByEmail(email);
//        Roles roles= roleRepository.findByRole(role);
//        user.getRoles().add(roles);
//        log.info("Role {} is added to email {}", role,email);
//
//
//    }
//
//    @Override
//    public AppUser getUser(String email) {
//        return appUserRepository.findByEmail(email);
//    }
//
//    @Override
//    public List<AppUser> getUsers() {
//        return appUserRepository.findAll();
//    }
//
//
//}
