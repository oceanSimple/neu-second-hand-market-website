package com.ocean.entity.chatRoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    private String nickName; // 昵称
    private String remark; // 备注
    private Integer hasNewMessage; // 0: no new message; 1: has new message
}
