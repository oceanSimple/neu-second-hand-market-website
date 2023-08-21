export interface Friend {
  code: string
  nickName: string
  remark: string
  hasNewMessage: number
}

export interface Message {
  sender: string
  receiver: string
  message: string
  time: string
  isRead: number
}

export interface AddFriendParams {
  code: string // 用户code
  friendCode: string // 好友code
  remark: string // 备注
  nickName: string // 昵称
}

export interface DeleteFriendParams {
  userCode: string // 用户code
  index: number // 删除好友的下标
}

export interface GetMessageParams {
  sender: string // 发送者code
  receiver: string // 接收者code
}

