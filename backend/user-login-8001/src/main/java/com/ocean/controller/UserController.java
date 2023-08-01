package com.ocean.controller;

import com.ocean.common.R;
import com.ocean.entity.User;
import com.ocean.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    /**
     * 登录
     * 用户通过账号密码进行登录
     * @param user 用户信息，主要包含code和password
     * @return 返回查询的用户信息
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody User user) {
        return userService.login(user);
    }


    @PostMapping("/checkToken")
    public R<User> checkToken(@RequestBody User user) {
        return userService.checkToken(user);
    }
}
