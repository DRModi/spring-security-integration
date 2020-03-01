package com.drmodi.springsecurity.springsecurityjpa.securityconfig;

import com.drmodi.springsecurity.springsecurityjpa.model.MyUserDetails;
import com.drmodi.springsecurity.springsecurityjpa.model.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.drmodi.springsecurity.springsecurityjpa.model.User;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserJPARepository userJPARepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //return new MyUserDetails(s);

        Optional<User> user = userJPARepository.findByUserName(userName);

        //throwing the exception for user not found.
        user.orElseThrow(() -> new UsernameNotFoundException("Not Found the User: "+userName));

        return user.map(MyUserDetails::new).get();

    }
}
