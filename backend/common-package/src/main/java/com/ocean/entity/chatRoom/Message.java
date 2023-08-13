package com.ocean.entity.chatRoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String destination; // 目的地
    private String message; // 消息
    private String time; // 时间
    private Integer isRead; // 0: not read; 1: read
}
