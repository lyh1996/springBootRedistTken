package com.example.springBootredis.token.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 633805 LYH
 * @version V1.0
 * @description 在需要保证 接口幂等性 的Controller的方法上使用此注解
 * @create 2019-08-15 15:52
 * @since 1.7
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {
}
