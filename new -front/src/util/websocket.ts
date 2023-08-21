import { getObj } from '@/util/localStorageObj.ts'
import { User } from '@/api/user/type.ts'

export const websocketConnect = (): WebSocket => {
  const user = getObj<User>('user') as User
  const uri = 'ws://localhost:8002/ws/' + user.code
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