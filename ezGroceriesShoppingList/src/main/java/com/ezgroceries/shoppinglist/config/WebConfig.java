package com.ezgroceries.shoppinglist.config;

import com.ezgroceries.shoppinglist.entities.ShoppingListConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // custom ShoppingList converter
        ShoppingListConverter shoppingListConverter = new ShoppingListConverter(new ObjectMapper());
        shoppingListConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(shoppingListConverter);

        // default JSON <-> Object converter
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
