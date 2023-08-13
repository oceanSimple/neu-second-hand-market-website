package com.ocean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端传给后端的消息实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String destination; // 目的地
    private String message; // 消息
    private String time; // 时间
}
