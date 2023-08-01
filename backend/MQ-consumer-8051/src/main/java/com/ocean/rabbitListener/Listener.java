package com.ocean.rabbitListener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = "dead_queue")
    public void listenDeadQueue(Message message) {
        String email = new String(message.getBody());
        System.out.println("收到过期消息，删除该邮箱的验证码：" + email);
        stringRedisTemplate.opsForHash().delete("verificationCode", email);
    }

}
