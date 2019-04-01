package com.little.g.admin.web;

import com.little.g.common.web.config.MessageSourceUtil;
import com.little.g.common.web.utils.ReloadableResourceBundleMessageSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Created by lengligang on 2019/3/18.
 */
@Configuration
@ImportResource(locations = {"classpath:META-INF/spring/dubbo-consume.xml"})
public class ApplicationConfig {

    @Value("${spring.messages.basename}")
    private String baseName;


    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource=new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        messageSource.setCacheSeconds(3600);
        return messageSource;
    }


    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
    @Bean
    public MessageSourceUtil messageSourceUtil(){
        return new MessageSourceUtil();
    }
}
