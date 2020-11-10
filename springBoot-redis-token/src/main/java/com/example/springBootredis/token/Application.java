package com.example.springBootredis.token;


import com.example.springBootredis.token.interceptor.AccessLimitInterceptor;
import com.example.springBootredis.token.interceptor.ApiIdempotentInterceptor;
import com.example.springBootredis.token.interceptor.LimitInterceptor;
import com.example.springBootredis.token.interceptor.SignAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author 小老弟
 */
@SpringBootApplication
@EnableScheduling
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 跨域
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        //corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 接口幂等性拦截器
        registry.addInterceptor(apiIdempotentInterceptor());
        // 接口防刷限流拦截器
        registry.addInterceptor(accessLimitInterceptor());

        // 验签
        registry.addInterceptor(signAuthInterceptor());

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public ApiIdempotentInterceptor apiIdempotentInterceptor() {
        return new ApiIdempotentInterceptor();
    }

    @Bean
    public AccessLimitInterceptor accessLimitInterceptor() {
        return new AccessLimitInterceptor();
    }

    @Bean
    public SignAuthInterceptor signAuthInterceptor() {
        return new SignAuthInterceptor();
    }


}
