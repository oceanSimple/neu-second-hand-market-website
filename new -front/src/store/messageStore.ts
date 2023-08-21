import { defineStore } from 'pinia'
import {
  reqAddFriend,
  reqDeleteFriend,
  reqGetFriendList,
  reqGetMessageList,
} from '@/api/message/message.ts'
import {
  AddFriendParams,
  DeleteFriendParams,
  Friend,
  GetMessageParams,
} from '@/api/message/type.ts'

export const useMessageStore = defineStore('Message', {
  state: () => {
    return {
      socket: null as WebSocket | null,
      currentFriend: null as Friend | null,
    }
  },
  actions: {
    // 查询所有好友
    async getFriendList(code: string) {
      const result = await reqGetFriendList(code)
      return result
    },
    // 添加好友
    async addFriend(data: AddFriendParams) {
      const result = await reqAddFriend(data)
      return result
    },
    // 删除好友
    async deleteFriend(data: DeleteFriendParams) {
      const result = await reqDeleteFriend(data)
      return result
    },
    // 获取消息列表
    async getMessageList(data: GetMessageParams) {
      const result = await reqGetMessageList(data)
      return result
    },
  },
})