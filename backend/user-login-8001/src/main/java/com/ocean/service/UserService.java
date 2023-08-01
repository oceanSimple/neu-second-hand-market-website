package com.ocean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocean.common.R;
import com.ocean.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
    R<User> login(User user); // 账号密码登录

    R<User> checkToken(User user); // token登录

    R<User> create(User user);

    R<String> sendVerificationCode(String code, String email); // 发送验证码
}
