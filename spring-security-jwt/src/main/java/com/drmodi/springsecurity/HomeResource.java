package com.drmodi.springsecurity;


import com.drmodi.springsecurity.models.AuthenticateRequest;
import com.drmodi.springsecurity.models.AuthenticationResponse;
import com.drmodi.springsecurity.services.MyUserDetailsService;
import com.drmodi.springsecurity.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JWTUtil jwtTokenUtil;

    @RequestMapping({"/hello"})
    public String welcome() {
        return "Welcome, Hello World, Learn Spring Security with JWT";
    }


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthentication(@RequestBody AuthenticateRequest authenticateRequest)
            throws Exception {

        //Now using AuthenticationManager, using standard spring token, called usernamepasswordAuthenticationToken
        // To perform Authentication
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(), authenticateRequest.getPassword())
            );

        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials!");
        }

        //once authentication done, now generate the jwt token.
        //Get the userdetails, which is required in jwt generation

        final UserDetails userDetailsForTokenGeneration = myUserDetailsService
                .loadUserByUsername(authenticateRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetailsForTokenGeneration);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
}