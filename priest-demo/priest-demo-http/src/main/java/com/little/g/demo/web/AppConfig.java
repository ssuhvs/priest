package com.little.g.demo.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by lengligang on 2019/3/12.
 */
@ImportResource(locations = {"classpath:META-INF/spring/dubbo-consume.xml"})
@Configuration
public class AppConfig {
}
