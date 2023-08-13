package com.ocean.controller;

import com.ocean.common.R;
import com.ocean.entity.User;
import com.ocean.service.UserService;
import com.ocean.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    private UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     * 用户通过账号密码进行登录
     *
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

    @PostMapping("/register")
    public R<User> register(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping("/checkVerificationCode")
    public R<String> checkVerificationCode(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String code = data.get("code");
        if (email == null || email == "" || code == null || code == "") {
            return R.error(0, "邮箱或验证码不能为空");
        }
        return userService.checkVerificationCode(email, code);
    }

    @PostMapping("/update")
    public R<User> update(@RequestBody Map<String, String> data) {
        return userService.update(data);
    }
}
