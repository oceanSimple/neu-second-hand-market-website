<template>
  <div>
    <el-affix>
      <el-tooltip content="折叠菜单" effect="dark" placement="bottom">
        <Fold
          style="width: 1em; height: 1em; margin-right: 8px"
          @click="changeFold"
        />
      </el-tooltip>
      <span class="title">东北大学市场</span>
    </el-affix>
  </div>
</template>

<script lang="ts" setup>
import { useMainStore } from '@/store/mainStore'
import { Setting } from '@/view/main/setting'
import { getObj, setObj } from '@/util/localStorageObj'

let mainStore = useMainStore()
const changeFold = () => {
  // 改变折叠状态
  mainStore.fold = !mainStore.fold
  // 保存到本地
  const setting = getObj<Setting>('setting') as Setting
  setting.fold = mainStore.fold
  setObj<Setting>('setting', setting)
}
</script>

<style lang="scss" scoped>
.title {
  font-family: '华文行楷';
  font-size: 2rem;
}

.pic {
  margin-right: 1rem;
}
</style>