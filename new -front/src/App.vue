<template>
  <h1>hello</h1>
  <router-view />
</template>

<script lang='ts' setup>
import { useUserStore } from '@/store/userStore'
import { LoginParams } from '@/api/user/type'

let userStore = useUserStore()
const data: LoginParams = {
  code: '20216901',
  password: '123456',
}
// @ts-ignore
// onMounted(() => {
//   //userStore.checkToken()
//   userStore.getVerifyCodeByEmail({
//     email: '2265281187@qq.com',
//   })
// })
</script>

<style lang='scss' scoped>
h1 {
  color: $color;
}
</style>