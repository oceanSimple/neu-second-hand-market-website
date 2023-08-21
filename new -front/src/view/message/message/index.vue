<template>
  <el-card v-if="messageStore.currentFriend">
    <template #header>
      <span>{{ messageStore.currentFriend.nickName }}</span>
    </template>
  </el-card>
</template>

<script lang="ts" setup>
import { useMessageStore } from '@/store/messageStore.ts'
import { onMounted, watch } from 'vue'
import { User } from '@/api/user/type.ts'
import { getObj } from '@/util/localStorageObj.ts'
import { Friend } from '@/api/message/type.ts'

let messageStore = useMessageStore()
const user = getObj<User>('user') as User
watch(
  () => messageStore.currentFriend,
  () => {},
)
onMounted(() => {
  const friend = messageStore.currentFriend as Friend
  if (friend) {
    messageStore.getMessageList({
      sender: user.code,
      receiver: friend.code,
    })
  }
})
</script>

<style lang="scss" scoped></style>