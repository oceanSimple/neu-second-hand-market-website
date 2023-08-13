package com.ocean.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.common.R;
import com.ocean.entity.User;
import com.ocean.mapper.UserMapper;
import com.ocean.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public UserServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // 登录
    @Override
    public R<User> login(User user) {
        // 将密码进行md5处理
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        // 查找数据库，判断账号密码是否正确，以及是否禁止登录
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", user.getCode())
                .eq("password", user.getPassword())
                .eq("is_deleted", "0");
        User result = getOne(queryWrapper);
        if (result == null) {
            return R.error(0, "账号或密码错误");
        } else {
            if (result.getIsDeleted() == 1) {
                return R.error(0, "账号已被禁用");
            }
            // 生成token，存入redis
            result.setToken(UUID.randomUUID().toString());
            stringRedisTemplate.opsForHash().put("userToken", result.getCode(), result.getToken());

           /* Map<String, String> map = new HashMap<>(4);
            map.put("code", result.getCode());
            map.put("token", result.getToken());
            stringRedisTemplate.opsForHash().putAll(result.getCode(), map);
            // 设置过期时间，2day(60*60*24*2)后过期
            stringRedisTemplate.expire(result.getCode(), 60 * 60 * 24 * 2, TimeUnit.SECONDS);*/

            return R.success(201, "登录成功，欢迎您！", result);
        }
    }

    // token登录
    @Override
    public R<User> checkToken(User user) {
        if (user.getToken() != null && !user.getToken().equals("")) {
            Object userToken = stringRedisTemplate.opsForHash().get("userToken", user.getCode());
            if (userToken != null) {
                if (user.getToken().equals(userToken)) {
                    return R.success(200, "自动登录成功！！！", user);
                }
            }
        }
        return R.error(0, "自动登录失败！！！");
    }

    // 注册
    @Override
    public R<User> create(User user) {
        // 将密码进行md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        // 这一步很神奇地帮忙把id设置赋值为数据库自增的id，暂时不知道原理
        boolean save = save(user);
        if (save) {
            // 通过id查找用户
            R<User> result = getUserByCode(user.getCode());
            if (result.getCode() != 200) {
                return R.error(0, "注册失败！！！");
            }
            return R.success(200, "注册成功！！！", result.getData());
        }
        return R.error(0, "注册失败！！！");
    }

    // 检查验证码
    @Override
    public R<String> checkVerificationCode(String email, String code) {
        Object verificationCode = stringRedisTemplate.opsForHash().get("verificationCode", email);
        if (verificationCode != null) {
            if (verificationCode.equals(code)) {
                return R.success(200, "验证码正确！！！", null);
            }
        }
        return R.error(0, "验证码错误！！！");
    }

    // 根据code获取用户信息
    @Override
    public R<User> getUserByCode(String code) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        User user = getOne(queryWrapper);
        if (user == null) {
            return R.error(0, "查询失败！");
        }
        return R.success(200, "查询成功！", user);
    }

    // 根据修改用户信息
    @Override
    public R<User> update(Map<String, String> map) {
        // 获取参数
        String code = map.get("code");
        String target = map.get("target");
        String data = map.get("data");
        // 判断参数是否为空
        if (code == null || target == null || data == null) {
            return R.error(0, "修改失败！");
        }
        // 如果修改的是密码，需要进行md5加密
        if (target.equals("password")) {
            data = DigestUtils.md5DigestAsHex(data.getBytes());
        }
        // 根据code进行修改
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", code).set(target, data);
        boolean update = update(updateWrapper);
        if (update) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", code);
            User user = getOne(queryWrapper);
            return R.success(200, "修改成功！", user);
        } else {
            return R.error(0, "修改失败！");
        }
    }
}
