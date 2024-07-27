package com.dororo.future.gencopilot.config;

import com.dororo.future.gencopilot.interceptor.LoggedUserContextInterceptor;
import com.dororo.future.gencopilot.interceptor.PageParameterContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoggedUserContextInterceptor loggedUserContextInterceptor;
    @Autowired
    private PageParameterContextInterceptor pageParameterContextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggedUserContextInterceptor);
        registry.addInterceptor(pageParameterContextInterceptor);
    }
}
