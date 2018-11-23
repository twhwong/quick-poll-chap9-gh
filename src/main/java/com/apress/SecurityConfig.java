package com.apress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 05-11-18
 *
 * @author Tom
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //logger.debug("In method SecurityConfig.configure");
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // don't create HTTP session and not store logged in user's SecurityContext in the session
        // And let only v3 needs authentication
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v1/**", "/v2/**", "/swagger-ui/**", "/api-docs/**").permitAll()
                .antMatchers("/v3/polls/**").authenticated()
                .and()
                .httpBasic()
                .realmName("Quick Poll")
                .and()
                .csrf().disable();


        // --------------------------------
        // The following is only needed if you want to view H2-console for the development purpose


        // Note! only for development purpose! Enable access to H2 console
        httpSecurity.authorizeRequests().antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/h2-console").permitAll();
        // Note! only for development purpose!
        // disable CRSF (Cross Site Request Forgery)
        httpSecurity.csrf().disable();

        // Note! only for development purpose!
        // add this line to use H2 web console, after Spring security has been enabled
        // this disable X-Frame Options in Spring Security
        httpSecurity.headers().frameOptions().disable();
    }

    // For OAuth Authorisation server
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
