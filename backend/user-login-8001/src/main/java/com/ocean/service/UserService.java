package com.ocean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocean.common.R;
import com.ocean.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService extends IService<User> {
    R<User> login(User user); // 账号密码登录

    R<User> checkToken(User user); // token登录

    R<User> create(User user); // 注册

    R<String> checkVerificationCode(String email, String code); // 验证码校验

    R<User> getUserByCode(String code); // 根据code获取用户信息

    R<User> update(Map<String, String> map); // 修改个人信息
}
