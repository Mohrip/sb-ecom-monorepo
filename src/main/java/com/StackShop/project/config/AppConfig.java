package com.StackShop.project.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    // I convert it into Bean to use it anywhere in the project
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();

    }
}
