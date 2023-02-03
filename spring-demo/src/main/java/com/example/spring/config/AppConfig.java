package com.example.spring.config;

import com.example.spring.remote.MyImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MyImportBeanDefinitionRegistrar.class})
public class AppConfig {

}
