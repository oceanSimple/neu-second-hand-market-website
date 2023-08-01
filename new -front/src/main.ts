import { createApp } from 'vue'
import App from './App.vue'

// 引入element-plus
import 'element-plus/dist/index.css'
import ElementPlus from 'element-plus'
// 引入全局样式
import '@/style/index.scss'
// 引入仓库
import pinia from './store'
// 引入路由
import router from './router'

const app = createApp(App)

app.use(ElementPlus)
app.use(pinia)
app.use(router)

app.mount('#app')
