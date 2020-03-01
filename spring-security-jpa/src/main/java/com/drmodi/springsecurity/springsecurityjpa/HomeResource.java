package com.drmodi.springsecurity.springsecurityjpa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

    @GetMapping("/")
    public String home(){
        return "<h1>Welcome to spring boot security using JPA - MySQL!!";
    }

    @GetMapping("/user")
    public String getUser(){
        return "<h1>Welcome user to spring boot security using JPA - MySQL!!";
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return "<h1>Welcome admin to spring boot security using JPA - MySQL!!";
    }
}
