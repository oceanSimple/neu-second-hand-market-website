package com.ocean.entity.chatRoom.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteFriendParams {
    private String userCode;
    private Long index;
}
