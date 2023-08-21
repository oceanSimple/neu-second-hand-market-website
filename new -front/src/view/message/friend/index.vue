<template>
  <div class="title">好友列表</div>

  <ul v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
    <li
      v-for="item in friendList"
      :key="item.code"
      class="infinite-list-item"
      @click="chat(item)"
    >
      <div class="left">
        <span>{{ item.nickName }}</span>
      </div>
      <div class="right">
        <el-icon v-show="item.hasNewMessage === 1">
          <Component is="ChatLineRound" style="color: red"></Component>
        </el-icon>
      </div>
    </li>
  </ul>
</template>

<script lang="ts" setup>
import { onMounted, reactive } from 'vue'
import { useMessageStore } from '@/store/messageStore.ts'
import { getObj } from '@/util/localStorageObj.ts'
import { User } from '@/api/user/type.ts'
import { Friend } from '@/api/message/type.ts'

const messageStore = useMessageStore()
const user = getObj<User>('user') as User
const load = () => {}
let friendList: Friend[] = reactive([])
onMounted(async () => {
  const result = await messageStore.getFriendList(user.code)
  friendList.push(...result.data)
})
const chat = (item: Friend) => {
  messageStore.currentFriend = item
}
</script>

<style lang="scss" scoped>
.infinite-list {
  height: 400px;
  padding: 0;
  margin: 0;
  list-style: none;
}

.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  background: var(--el-color-primary-light-9);
  margin: 10px;
  color: var(--el-color-primary);
  text-align: center;
}

.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}

.title {
  font-size: 20px;
  font-weight: bold;
  color: var(--el-color-primary);
  text-align: center;
  margin: 10px;
}

.left {
  width: 80%;
}
</style>