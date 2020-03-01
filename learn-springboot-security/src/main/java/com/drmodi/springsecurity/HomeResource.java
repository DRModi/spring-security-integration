package com.drmodi.springsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {
    @GetMapping("/")
    public String home(){
        return ("<h1> Welcome Everybody to Spring Boot Security Demo!! </h1>") ;
    }

    @GetMapping("/user")
    public String getUser() {
        return ("<h1> Welcome USER to Spring Boot Security Demo!! </h1>") ;
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return ("<h1> Welcome ADMIN to Spring Boot Security Demo!! </h1>") ;
    }


}
