import { useUserStore } from '@/store/userStore.ts'

const userStore = useUserStore()

export interface Message {
  destination: string
  message: string
  time: string
}

export const websocketConnect = (): WebSocket => {
  const uri = 'ws://localhost:8002/ws/' + userStore.userInfo.code
  const socket = new WebSocket(uri)
  socket.onopen = () => {
    console.log('连接成功')
  }
  socket.onclose = () => {
    console.log('连接关闭')
  }
  socket.onerror = () => {
    console.log('连接错误')
  }
  return socket
}