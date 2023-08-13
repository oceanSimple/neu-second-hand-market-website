import { defineStore } from 'pinia'
import { websocketConnect } from '@/util/websocket.ts'

export const useMessageStore = defineStore('Message', {
  state: () => {
    return {
      socket: websocketConnect() as WebSocket,
    }
  },
})