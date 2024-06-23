package com.example.chatter.configs;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig extends WebMvcAutoConfiguration {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
