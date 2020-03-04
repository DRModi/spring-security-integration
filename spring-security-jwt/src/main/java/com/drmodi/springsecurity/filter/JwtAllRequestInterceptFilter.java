package com.drmodi.springsecurity.filter;

import com.drmodi.springsecurity.services.MyUserDetailsService;
import com.drmodi.springsecurity.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAllRequestInterceptFilter extends OncePerRequestFilter {


    //Intercept all the incoming request, look for the header and findout the jwt is valid or not

    /*
        (1) Extract token from the request header, and extract username
        (2) Take control to SecurityContextHolder and make sure it is null,
            meaning authentication is not done
        (3) Validate token using util class and set the securitycontext with the authenticated user,
            which is usually maintain by the spring security. Since we have taken control of it we need to
            set it back.
        (4) Once all set and done, continue the filter chain.
     */

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {


        //get the header
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwtToken = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwtToken);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);

            if(jwtUtil.validateToken(jwtToken, userDetails)){

                UsernamePasswordAuthenticationToken upAuthToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                upAuthToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(upAuthToken);
            }

        }
        //continue the filter chain
        chain.doFilter(request, response);

    }

}
