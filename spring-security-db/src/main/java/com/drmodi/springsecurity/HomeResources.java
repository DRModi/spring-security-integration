package com.drmodi.springsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResources {

    @GetMapping("/")
    public String home(){
        return ("<h1>Welcome to spring security using DB!!</h1>");
    }

    @GetMapping("/user")
    public String getUser(){

        return ("<h1>Welcome user to spring security using DB!!</h1>");
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return ("<h1>Welcome admin to spring security using DB!!</h1>");
    }
}
