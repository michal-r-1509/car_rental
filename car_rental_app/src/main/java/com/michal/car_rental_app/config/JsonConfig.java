package com.michal.car_rental_app.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.DatatypeFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JsonConfig {

    @Primary
    @Bean
    ObjectMapper objectMapper(){
//        not working for ENUMs
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES, true);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        return mapper;
    }
}
