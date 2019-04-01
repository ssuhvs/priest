package com.little.g.user.web;

import com.little.g.common.web.config.AppConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by lengligang on 2019/3/18.
 */
@Configuration
@Import(AppConfig.class)
public class ApplicationConfig {
}
