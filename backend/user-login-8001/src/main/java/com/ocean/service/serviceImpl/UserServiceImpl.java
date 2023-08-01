package com.ocean.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.common.R;
import com.ocean.entity.User;
import com.ocean.mapper.UserMapper;
import com.ocean.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public R<User> login(User user) {
        // 查找数据库，判断账号密码是否正确，以及是否禁止登录
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", user.getCode())
                .eq("password", user.getPassword())
                .eq("is_deleted", "0");
        User result = getOne(queryWrapper);
        if (result == null) {
            return R.error(0, "账号或密码错误");
        } else {
            if (result.getIsDeleted().equals("1")) {
                return R.error(0, "账号已被禁用");
            }
            // 生成token，存入redis
            result.setToken(UUID.randomUUID().toString());
            Map<String, String> map = new HashMap<>(4);
            map.put("code", result.getCode());
            map.put("token", result.getToken());
            stringRedisTemplate.opsForHash().putAll(result.getCode(), map);
            // 设置过期时间，2day(60*60*24*2)后过期
            stringRedisTemplate.expire(result.getCode(), 60 * 60 * 24 * 2, TimeUnit.SECONDS);

            return R.success(201, "登录成功，欢迎您！", result);
        }
    }

    @Override
    public R<User> checkToken(User user) {
        if (user.getToken() != null && user.getToken() != "") {
            ArrayList<Object> queryList = new ArrayList<>(4);
            queryList.add("code");
            queryList.add("token");
            List<Object> objects = stringRedisTemplate.opsForHash().multiGet(user.getCode(), queryList);
            if (objects.get(0) != null && objects.get(1) != null) {
                if (user.getToken().equals(objects.get(1))) {
                    return R.success(200, "自动登录成功！！！", user);
                }
            }
        }
        return R.error(0, "自动登录失败！！！");
    }

    @Override
    public R<User> create(User user) {
        return null;
    }

    @Override
    public R<String> sendVerificationCode(String code, String email) {
        // 随机生成4位验证码
        String verificationCode = String.valueOf((int)((Math.random() * 9 + 1) * 1000));
        // 将验证码存入redis
        stringRedisTemplate.opsForHash().put("verificationCode", code, verificationCode);
        return null;
    }
}
