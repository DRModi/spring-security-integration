package com.drmodi.springsecurity.dbsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfigUsingDB extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource myDataSource; //By default, spring security consider the h2 database and creates default schema
                            // But again external db can configured at application.properties.

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Using jdbc authentication
        // Below using default h2 and not requiring create schema, using default schema
        //below disabled code, since using schema and table entries by sql files.

        // (1) Using default data source and h2 db.
            /*auth.jdbcAuthentication()
            .dataSource(myDataSource)
            .withDefaultSchema()
            .withUser(
                    User.withUsername("foo")
                    .password("foo")
                    .roles("USER")
            )
            .withUser(
                    User.withUsername("admin")
                            .password("admin")
                            .roles("ADMIN")
            );*/

        //(2) Using db default schema, schema.sql, data.sql
        /*
            auth.jdbcAuthentication()
                .dataSource(myDataSource);
        */

        //(3) Using query to user and authorities table, so that spring security is not forcing to use default schema.
        // in below code we are querying default schema, but can be used in custom schema and ant DB.
        auth.jdbcAuthentication()
                .dataSource(myDataSource)
                .usersByUsernameQuery("select username,password,enabled "
                            + "from users "
                            + "where username = ?")
                .authoritiesByUsernameQuery("select username,authority "
                            + "from authorities "
                            + "where username = ?");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .and().formLogin();

    }

    @Bean
    public PasswordEncoder getClearTextPass(){
        return NoOpPasswordEncoder.getInstance();
    }
}
