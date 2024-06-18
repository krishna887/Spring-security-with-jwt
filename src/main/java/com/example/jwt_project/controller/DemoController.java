package com.example.jwt_project.controller;

import org.springframework.web.bind.annotation.*;

@RestController

public class DemoController {
    @PutMapping("/put")
    public String putRequest(){
        return " This Page is only for User No one can get other than user";
    }
    @DeleteMapping("/delete")
    public String deleteRequest(){
        return " This Page is only  for admin No one can get other than Admin";
    }
    @GetMapping("/get")
    public String getRequest(){
        return " This Page is accessible for anyone";
    }
    @PostMapping("/post")
    public String postRequest(){
        return " This Page is accessible for anyone";
    }
}
