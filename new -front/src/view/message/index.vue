<template>
  <div>
    <span>当前用户：{{ user.code }}</span>
    <br />
    <br />
    <br />
    目的地:
    <el-input v-model="input.destination"></el-input>
    <br />
    消息
    <el-input v-model="input.message"></el-input>
    <br />
    <br />
    <br />
    <el-button type="success" @click="sendMessage">发送</el-button>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { User } from '@/api/user/type.ts'
import { getObj } from '@/util/localStorageObj.ts'
import { useMessageStore } from '@/store/messageStore.ts'
import { Message } from '@/util/websocket.ts'

const user = getObj<User>('user') as User
const input = reactive({
  destination: '78945612',
  message: '',
})
const socket = useMessageStore().socket
const sendMessage = () => {
  const message: Message = {
    destination: input.destination,
    message: input.message,
    time: new Date().toLocaleString().toString(),
  }
  socket.send(JSON.stringify(message))
}
</script>

<style lang="scss" scoped></style>