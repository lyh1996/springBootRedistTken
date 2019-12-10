package com.example.springBootredis.token.service;

import com.example.springBootredis.token.domain.ServerResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 633805 LYH
 * @version V1.0
 * @description 对类的描述
 * @create 2019-08-15 16:28
 * @since 1.7
 */
public interface TokenService {

    ServerResponse createToken();

    void checkToken(HttpServletRequest request);
}
