package com.qmul.Social.Network.conf.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("development")
public class WebConfig implements WebMvcConfigurer  {

}
