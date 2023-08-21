<template>
  <el-row>
    <el-col :span="20">
      <Message></Message>
    </el-col>
    <el-col :span="4" class="friend">
      <Friend></Friend>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import Friend from '@/view/message/friend/index.vue'
import Message from '@/view/message/message/index.vue'
</script>

<style lang="scss" scoped>
.friend {
  height: 400px;
  background-color: #e8e8e8;
}
</style>