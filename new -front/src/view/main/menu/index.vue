<template>
  <div>
    <el-menu :collapse="mainStore.fold">
      <el-menu-item
        v-for="item in menuItems"
        :key="item.name"
        :index="item.route"
        @click="goRoute"
      >
        <el-icon>
          <component :is="item.icon"></component>
        </el-icon>
        <template #title>
          <span>{{ item.name }}</span>
        </template>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script lang="ts" setup>
import { menuItems } from '@/view/main/menu/static'
import { useMainStore } from '@/store/mainStore'
import { watch } from 'vue'
import router from '@/router'

let mainStore = useMainStore()
const goRoute = (route: any) => {
  router.push(route.index)
}
</script>

<style lang="scss" scoped></style>