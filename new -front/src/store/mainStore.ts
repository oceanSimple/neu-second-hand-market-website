import { defineStore } from 'pinia'
import { getObj } from '@/util/localStorageObj'
import { Setting } from '@/view/main/setting'

// 对设置进行默认初始化，如果本地没有初始化，则进行初始化
let setting = getObj<Setting>('setting')
let fold = false
if (!setting) {
  setting = {
    fold: false,
  }
  localStorage.setItem('setting', JSON.stringify(setting))
} else {
  fold = setting.fold
}

export const useMainStore = defineStore('Main', {
  state: () => {
    return {
      // 侧边栏是否折叠
      fold: fold,
    }
  },
})