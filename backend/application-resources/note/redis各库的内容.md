> 本文件用来记录redis各个库保存的内容，以及其对应的数据结构

# database 0

1. 用户的token
    - name: userToken
    - 结构：map
    - key: 账号code
    - value: token
2. 邮箱验证码
    - name: verificationCode
    - 结构：map
    - key: 邮箱
    - value: 验证码

# database 1

> 存储用户的好友列表
> - name: 用户的code
> - 结构：map
> - key: 好友的code
> - value: {nickname: 好友的昵称, remark: 好友的备注, hasNewMsg: 是否有新消息}

# database 2

> 存储用户的聊天记录
> - name: 聊天双方的code（20210000_20210001），小号在前
> - 结构：list
> - value: {sender:发送者，receiver: 接收者的code, message: 消息内容, time: 发送时间, isRead: 是否已读}

