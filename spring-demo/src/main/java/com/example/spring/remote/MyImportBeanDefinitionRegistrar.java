package com.example.spring.remote;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * 模拟类加载
 * 在项目启动是加载。和LoadClassController手动加载，作用相同，两种加载方式
 * @author jiangbaojun
 * @date 2023/2/3 18:01
 */
@Component
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        try {
            ClassLoader classLoader = ClassLoaderUtil.getClassLoader(TestConstant.classLoaderPath);
            Class<?> clazz = classLoader.loadClass(TestConstant.classPath);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            BeanDefinition beanDefinition = builder.getBeanDefinition();
            registry.registerBeanDefinition(clazz.getName(), beanDefinition);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    } 
} 