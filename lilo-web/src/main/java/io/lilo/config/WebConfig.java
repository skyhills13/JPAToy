package io.lilo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import io.lilo.interceptor.LoginInterceptor;

@Configuration
@ComponentScan(basePackages = {"io.lilo"})
public class WebConfig extends WebMvcConfigurationSupport {

    public static final String RESOLVER_PREFIX = "/WEB-INF/view";
    public static final String RESOLVER_SUFFIX = ".jsp";

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(RESOLVER_PREFIX);
        resolver.setSuffix(RESOLVER_SUFFIX);
        return resolver;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/register", "/user/login", "/");
    }

    @Bean
    public HandlerInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/stylesheet/*").addResourceLocations("/stylesheet/");
        super.addResourceHandlers(registry);
    }
}
