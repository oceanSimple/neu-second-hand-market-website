package com.ocean.entity.chatRoom.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendParams {
    private String code; // 用户code
    private String friendCode; // 好友code
    private String nickName; // 昵称
    private String remark; // 备注
}
