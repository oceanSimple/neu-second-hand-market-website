package com.ocean.controller;

import com.ocean.config.TTLRabbitMqConfig;
import com.ocean.util.SendEmail;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/mqProducer")
public class VerificationCodeController {
    private final RabbitTemplate rabbitTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final SendEmail sendEmail;

    @Autowired
    private VerificationCodeController(RabbitTemplate rabbitTemplate, StringRedisTemplate stringRedisTemplate, SendEmail sendEmail) {
        this.rabbitTemplate = rabbitTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.sendEmail = sendEmail;
    }

    @PostMapping("/emailVerificationCode")
    public Integer emailVerificationCode(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        // 生成一个四位数的验证码
        String code = String.valueOf((int) (Math.random() * 9000 + 1000));
        // 捕获异常，如果发送邮件失败，返回0
        try {
            // 发送邮件
            sendEmail.sendEmail(email, "验证码", "您的验证码为：" + code);
        } catch (Exception e) {
            return 0;
        }
        // 将code存入redis
        stringRedisTemplate.opsForHash().put("verificationCode", email, code);
        // 发送消息到ttl，设置过期时间为5分钟，五分钟后删除redis中的验证码
        rabbitTemplate.convertAndSend(TTLRabbitMqConfig.TTL_EXCHANGE_NAME, "ttl", email);
        return 200;
    }
}
