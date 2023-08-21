package com.ocean.controller;

import com.alibaba.fastjson2.JSON;
import com.ocean.common.R;
import com.ocean.entity.chatRoom.message.GetMessageParams;
import com.ocean.entity.chatRoom.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MessageController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 添加消息
     *
     * @param message
     * @return
     */
    @PostMapping("/add")
    public R<String> addMessage(@RequestBody Message message) {
        String key;
        if (Integer.valueOf(message.getSender()) <= Integer.valueOf(message.getReceiver())) {
            key = message.getSender() + "-" + message.getReceiver();
        } else {
            key = message.getReceiver() + "-" + message.getSender();
        }
        Long result = stringRedisTemplate.opsForList().rightPush(key, JSON.toJSONString(message));
        if (result != null && result > 0) {
            return R.success(200, "success", "success");
        }
        return R.error(0, "error");
    }

    /**
     * 获取用户与好友间的聊天记录
     *
     * @param params
     * @return
     */
    @PostMapping("/getMessage")
    public R<List<Message>> getMessage(@RequestBody GetMessageParams params) {
        String key;
        if (Integer.valueOf(params.getSender()) <= Integer.valueOf(params.getReceiver())) {
            key = params.getSender() + "-" + params.getReceiver();
        } else {
            key = params.getReceiver() + "-" + params.getSender();
        }
        List<String> result = stringRedisTemplate.opsForList().range(key, 0, -1);
        ArrayList<Message> messages = new ArrayList<>(result.size());
        result.stream().forEach(item -> messages.add(JSON.parseObject(item, Message.class)));
        if (messages.size() > 0) {
            return R.success(200, "success", messages);
        } else {
            return R.error(0, "消息为空！");
        }
    }
}
