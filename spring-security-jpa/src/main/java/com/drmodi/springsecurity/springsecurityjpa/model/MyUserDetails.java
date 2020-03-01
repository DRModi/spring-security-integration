package com.drmodi.springsecurity.springsecurityjpa.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;


    /* used to test with the user name
    public MyUserDetails(String username){
        this.userName = username;
    }*/

    public MyUserDetails(User user){
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.active = user.isActive();
        this.authorities = Arrays.stream(user.getRoles().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
    }

    public MyUserDetails(){

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName ;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
