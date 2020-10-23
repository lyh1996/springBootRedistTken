package com.example.springBootredis.token.common;

/**
 * 限流类型
 *
 * @author LYH
 * @date 2020/09/24 14:07
 */
public enum LimitType {
    /**
     * 自定义key
     */
    CUSTOMER,

    /**
     * 请求者IP
     */
    IP;
}
