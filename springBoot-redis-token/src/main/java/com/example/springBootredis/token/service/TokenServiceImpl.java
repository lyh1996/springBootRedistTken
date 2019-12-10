package com.example.springBootredis.token.service;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springBootredis.token.common.Constant;
import com.example.springBootredis.token.common.ResponseCode;
import com.example.springBootredis.token.domain.ServerResponse;
import com.example.springBootredis.token.exception.ServiceException;
import com.example.springBootredis.token.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 633805 LYH
 * @version V1.0
 * @description 对类的描述
 * @create 2019-08-15 16:32
 * @since 1.7
 */
@Service
public class TokenServiceImpl implements TokenService {
    private static final String TOKEN_NAME = "token";

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ServerResponse createToken() {

        String str = RandomUtil.randomNumbers(32);
        StrBuilder token = new StrBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);

        redisUtil.setex(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_MINUTE);

        return ServerResponse.success(token.toString());
    }

    /**
     * 如果存在, 正常处理业务逻辑, 并从redis中删除此 token, 那么, 如果是重复请求, 由于 token已被删除, 则不能通过校验, 返回 请勿重复操作提示
     * 如果不存在, 说明参数不合法或者是重复请求, 返回提示即可
     *
     * @param request
     */
    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        // header中不存在token
        if (StrUtil.isBlank(token)) {
            token = request.getParameter(TOKEN_NAME);
            if (StrUtil.isBlank(token)) {// parameter中也不存在token
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }

        boolean flag = redisUtil.exists(token);
        if (!flag) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        Long del = redisUtil.del(token);
        if (del <= 0) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
    }

}