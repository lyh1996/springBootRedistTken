package com.example.springBootredis.token.controller;

import com.example.springBootredis.token.domain.ServerResponse;
import com.example.springBootredis.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 为需要保证幂等性的每一次请求创建一个唯一标识 token, 先获取 token, 并将此 token存入redis, 请求接口时,
 * 将此 token放到header或者作为请求参数请求接口, 后端接口判断redis中是否存在此 token:
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ServerResponse token() {
        return tokenService.createToken();
    }

}
