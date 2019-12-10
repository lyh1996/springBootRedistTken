package com.example.springBootredis.token.service;

import com.example.springBootredis.token.domain.ServerResponse;

public interface TestService {

    ServerResponse testIdempotence();

    ServerResponse accessLimit();

}
