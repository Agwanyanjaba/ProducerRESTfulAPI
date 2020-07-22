package com.wycliffe.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@SpringBootApplication
public class WybosoftApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WybosoftApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WybosoftApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer1() {
        return new WebMvcConfigurer() {
            @Override
            public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

            }

            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

            }

            @Override
            public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

            }

            @Override
            public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

            }

            @Override
            public void addFormatters(FormatterRegistry formatterRegistry) {

            }

            @Override
            public void addInterceptors(InterceptorRegistry interceptorRegistry) {

            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
            }

            @Override
            public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

            }

            @Override
            public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

            }

            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

            }

            @Override
            public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

            }

            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> list) {

            }

            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

            }

            @Override
            public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

            }

            @Override
            public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

            }

            @Override
            public Validator getValidator() {
                return null;
            }

            @Override
            public MessageCodesResolver getMessageCodesResolver() {
                return null;
            }
        };
    }
}
