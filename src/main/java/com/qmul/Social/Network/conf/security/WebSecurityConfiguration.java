package com.qmul.Social.Network.conf.security;

import com.qmul.Social.Network.conf.constants.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private static final String[] SWAGGER_AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.INSTITUTE_SIGN_UP_URL).permitAll()

                .antMatchers(HttpMethod.GET, "/api/product/**").permitAll()
                .antMatchers("/api/product/**").hasAnyAuthority("MODERATOR", "ADMIN")

                .antMatchers(HttpMethod.GET, "/api/category/**").permitAll()
                .antMatchers("/api/category/**").hasAnyAuthority("MODERATOR", "ADMIN")

                .antMatchers(HttpMethod.GET, "/api/user/confirm-account").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/**").permitAll()

                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/order/**").hasAnyAuthority("USER")
                .antMatchers("/api/admin/**").hasAnyAuthority("MODERATOR", "ADMIN")

                .antMatchers("/api/query").permitAll()

                .antMatchers(HttpMethod.POST,"/api/guest/order").permitAll()

                .antMatchers(SWAGGER_AUTH_WHITELIST).permitAll()
                .antMatchers("/").hasAnyAuthority("USER", "MODERATOR", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationVerficationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors(c->{
            CorsConfiguration cc = new CorsConfiguration();
            cc.setAllowedOrigins(List.of("*"));
            cc.setAllowedMethods(List.of("POST","GET","PUT","DELETE"));
        });
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

    }
}