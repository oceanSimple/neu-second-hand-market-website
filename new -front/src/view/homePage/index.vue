<template>
  <div class="container">
    <div class="menu">
      <Sort />
    </div>
    <div class="mainPage">
      <Header />
    </div>
  </div>
</template>

<script lang="ts" setup>
import Sort from './sort/index.vue'
import Header from './header/index.vue'
/*import { ElMessage } from 'element-plus'
import { onMounted } from 'vue'
import { useMessageStore } from '@/store/messageStore.ts'

let socket: WebSocket
onMounted(() => {
  socket = useMessageStore().socket
  socket.onmessage = (e) => {
    ElMessage({
      message: e.data,
      type: 'success',
    })
  }
})*/
</script>

<style lang="scss" scoped>
.container {
  width: 100vw;
  height: 100vh;
  display: flex;
}

.menu {
  width: 5vw;
  height: 100vh;
}

.mainPage {
  width: 95vw;
  height: 100vh;
  background-color: rgba(230, 230, 230, 0.67);
}
</style>