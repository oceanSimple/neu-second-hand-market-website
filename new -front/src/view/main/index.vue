<template>
  <el-row style="height: 200vh; transition: all 0.3s; width: 100vw">
    <el-col :span="col.left" class="left">
      <Menu></Menu>
    </el-col>
    <el-col :span="col.right" class="right">
      <Fastening></Fastening>
      <router-view></router-view>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import Fastening from './fastening/index.vue'
import Menu from './menu/index.vue'
import { reactive, watch } from 'vue'
import { useMainStore } from '@/store/mainStore'

let mainStore = useMainStore()
// 根据折叠状态设置左侧栏宽度
const col = reactive({
  left: 3,
  right: 21,
})
if (mainStore.fold) {
  col.left = 1.5
} else {
  col.left = 3
}
watch(
  () => mainStore.fold,
  (val) => {
    if (val) {
      col.left = 1.5
    } else {
      col.left = 3
    }
  },
)
</script>

<style lang="scss" scoped>
.left {
  background-color: #545c64;
}

.right {
  background-color: #e9e9e9;
}
</style>