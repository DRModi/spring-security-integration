package com.drmodi.springsecurity;

import com.drmodi.springsecurity.filter.JwtAllRequestInterceptFilter;
import com.drmodi.springsecurity.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtAllRequestInterceptFilter jwtAllRequestInterceptFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //in order to allow all the traffic to "/authenticate" end point, which will be authenticated by JWT
        // disable cross-site-request-forgery and authenticate other request as form based.


        http.csrf().disable()
               .authorizeRequests().antMatchers("/authenticate").permitAll()
               .anyRequest().authenticated()
               .and().sessionManagement() // in order to validate each request through jwt then
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //  Lets spring know don't do any session managed , we do all this to make Stateless. so no session managed.

        //Adding custom filter to do all request intercept at endpoint /authenticate and let spring know
        // run custom filter before default authentication filter run.
        http.addFilterBefore(jwtAllRequestInterceptFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder noOpPassEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    /*
    ***************************
    APPLICATION FAILED TO START
    ***************************

    Description:

    Field authenticationManager in com.drmodi.springsecurity.HomeResource required a bean of type 'org.springframework.security.authentication.AuthenticationManager' that could not be found.

    The injection point has the following annotations:
            - @org.springframework.beans.factory.annotation.Autowired(required=true)


    Action:

    Consider defining a bean of type 'org.springframework.security.authentication.AuthenticationManager' in your configuration.

    */
    //So previous version in spring boot it was provided, the bean "AuthenticationManager", but in 2.0 onwards it was not
    //provided so, we have create the bean, in order work in runtime. Previously default implementation of Authentication
    //Manager bean was present in spring, but now not the case so create the bean to inject at runtime..
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }




}
