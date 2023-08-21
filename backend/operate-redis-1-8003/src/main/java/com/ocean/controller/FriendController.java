package com.ocean.controller;

import com.alibaba.fastjson2.JSON;
import com.ocean.common.R;
import com.ocean.entity.chatRoom.friend.AddFriendParams;
import com.ocean.entity.chatRoom.friend.DeleteFriendParams;
import com.ocean.entity.chatRoom.friend.Friend;
import com.ocean.entity.chatRoom.friend.UpdateFriendParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/friend")
@CrossOrigin("*")
public class FriendController {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public FriendController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 获取用户的好友列表
     *
     * @param code
     * @return
     */
    @GetMapping("/{code}")
    public R<List<Friend>> getFriendList(@PathVariable("code") String code) {
        /*Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(code);

        if (map == null || map.size() == 0) {
            return R.error(0, "fail");
        }

        ArrayList<Friend> friends = new ArrayList<>(map.size());
        map.forEach((k, v) -> {
            Friend friend = JSON.parseObject(v.toString(), Friend.class);
            friends.add(friend);
        });*/
        List<String> list = stringRedisTemplate.opsForList().range(code, 0, -1);
        ArrayList<Friend> friends = new ArrayList<>(list.size());
        list.stream().forEach(item -> {
                    Friend friend = JSON.parseObject(item, Friend.class);
                    friends.add(friend);
                }
        );
        return R.success(200, "success", friends);
    }

    /**
     * 添加好友
     *
     * @param addFriendParams
     * @return
     */
    @PostMapping("")
    public R<String> addFriend(@RequestBody AddFriendParams addFriendParams) {
        Friend friend = new Friend(addFriendParams.getFriendCode(), addFriendParams.getNickName(), addFriendParams.getRemark(), 1);
        stringRedisTemplate.opsForList().leftPush(addFriendParams.getCode(), JSON.toJSONString(friend));
        //stringRedisTemplate.opsForHash().put(addFriendParams.getCode(), addFriendParams.getFriendCode(), JSON.toJSONString(friend));
        return R.success(200, "success", "success");
    }

    /**
     * 删除好友
     *
     * @param deleteFriendParams
     * @return
     */
    @PostMapping("/delete")
    public R<String> deleteFriend(@RequestBody DeleteFriendParams deleteFriendParams) {
        stringRedisTemplate.opsForList().set(deleteFriendParams.getUserCode(), deleteFriendParams.getIndex(), "delete");
        Long delete = stringRedisTemplate.opsForList().remove(deleteFriendParams.getUserCode(), 1, "delete");
        //Long delete = stringRedisTemplate.opsForHash().delete(deleteFriendParams.getUserCode(), deleteFriendParams.getFriendCode());
        if (delete == 0) {
            return R.error(0, "fail");
        }
        return R.success(200, "success", "success");
    }

    /**
     * 更新好友信息
     * <p>
     * 该方法与添加好友方法一致
     *
     * @param updateFriendParams
     * @return
     */
    @PutMapping("")
    public R<String> updateFriend(@RequestBody UpdateFriendParams updateFriendParams) {
        stringRedisTemplate.opsForList().set(updateFriendParams.getCode(), updateFriendParams.getIndex(), JSON.toJSONString(updateFriendParams.getFriend()));
        return R.success(200, "success", "success");
    }
}
