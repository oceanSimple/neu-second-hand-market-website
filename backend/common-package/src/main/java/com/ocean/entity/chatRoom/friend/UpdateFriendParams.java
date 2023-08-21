package com.ocean.entity.chatRoom.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFriendParams {
    private String code; // 用户code
    private Long index; // 好友列表中的索引
    private Friend friend; // 好友信息
}
