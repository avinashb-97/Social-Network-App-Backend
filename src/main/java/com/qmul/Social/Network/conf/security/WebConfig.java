package com.qmul.Social.Network.conf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@Profile("development")
public class WebConfig implements WebMvcConfigurer  {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//////        registry.addMapping("/**")
//////                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
////////                .exposedHeaders("Authorization");
////
//        registry.addMapping("/***")
//                .allowedOrigins("http://localhost:3000")
//                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
//                .allowedHeaders("X-Requested-With","Origin","Content-Type","Accept","Authorization");
//    }


//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));
//
//        // This allow us to expose the headers
//        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
//                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
