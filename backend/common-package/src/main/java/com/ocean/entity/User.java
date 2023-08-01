package com.ocean.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    private BigInteger id;
    private String code;
    private String password;
    private String nickname;
    private String address;
    private String isDeleted; // 数据库插入时会默认为0，删除时会改为1
    private String email;
    private String phone;
    @TableField(fill = FieldFill.INSERT)
    private String gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String gmtModified;
    @TableField(exist = false)
    private String token; // 排除字段，不与数据库进行匹配，只是用来判断登录的权限
}
