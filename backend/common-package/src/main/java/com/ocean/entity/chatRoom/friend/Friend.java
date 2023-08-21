package com.ocean.entity.chatRoom.friend;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Friend {
    private String code; // 好友code
    private String nickName; // 昵称
    private String remark; // 备注
    private Integer hasNewMessage; // 0: no new message; 1: has new message

    public Friend(String code, String nickName, String remark, Integer hasNewMessage) {
        this.code = code;
        this.nickName = nickName;
        this.remark = remark;
        this.hasNewMessage = hasNewMessage;
    }
}
