# 创建项目

1. 安装pnpm
    ```shell
    npm install -g pnpm
    ```
2. 创建项目
    ```shell
    pnpm create vite
    ```
3. 安装依赖（进步项目文件夹）
    ```shell
    pnpm install
    ```
4. 运行项目
    ```shell
    pnpm run dev
    ```

# 初始化项目

## 解决main.ts报错

> 在src/vite-env.d.ts中添加

```ts
/// <reference types="vite/client" />
//解决ts文件引入vue文件出现红色警告问题
declare module '*.vue' {
  import { defineComponent } from 'vue'
  const Component: ReturnType<typeof defineComponent>
  export default Component
}
```

## 安装eslint

> 没事就不要安装了，破事多唉

1. 安装eslint(本地安装)
   ```shell
   pnpm install eslint --save-dev
   ```
2. 初始化eslint配置文件
   ```shell
   npx eslint --init
   ```
   安装选择检查代码和错误，vue，js，ts，浏览器，js格式配置文件

## 安装prettier

1. 安装
   ```shell
   pnpm install -D eslint-plugin-prettier prettier eslint-config-prettier
   ```
2. 创建.prettierrc.json
   > 1. singleQuote: true, // 使用单引号
   > 2. semi: false, // 结尾不用分号
   > 3. bracketSpacing: true, // 对象中的空格 { foo: bar }
   > 4. htmlWhitespaceSensitivity: 'ignore', // html空格敏感度
   > 5. endOfLine: 'auto', // 结尾换行
   > 6. trailingComma: 'all', // 尾随逗号
   > 7. tabWidth: 2 // tab缩进

   ```json
   {
      "singleQuote": true,
      "semi": false,
      "bracketSpacing": true,
      "htmlWhitespaceSensitivity": "ignore",
      "endOfLine": "auto",
      "trailingComma": "all",
      "tabWidth": 2
   }
   ```

# 插件安装

## element-plus

1. 安装依赖
   ```shell
   pnpm i element-plus
   ```
2. 下载图表库
   ```shell
   pnpm i @element-plus/icons-vue
   ```
3. 在main.ts中引入
   ```ts
   import 'element-plus/dist/index.css'
   import ElementPlus from 'element-plus'
   app.use(ElementPlus)
   ```

## src别名@的配置

1. 安装path
   ```shell
   pnpm install path --save
   ```
2. vite.config.ts
   ```ts
   import { defineConfig } from 'vite'
   import vue from '@vitejs/plugin-vue'
   import path from 'path'
   
   // https://vitejs.dev/config/
   export default defineConfig({
     plugins: [vue()],
     resolve: {
       alias: {
         '@': path.resolve('./src'),
       },
     },
   })
   ```
3. tsconfig.json
   ```
   "compilerOptions": {
      // src的别名
      // 解析非相对模块的基地址，默认是当前目录
      "baseUrl": "./",
      //路径映射，相对于baseUrl
      "paths": {
      "@/*": [
      "src/*"
      ]
      },
   }
   ```

## 继承sass

1. 安装
   ```shell
   pnpm install sass node-sass sass-loader
   ```
2. 创建src/style/index.scss
   ```
   // 引入清除默认样式
   @import './reset.scss';
   ```
3. main.ts中引入
   ```ts
   import '@/style/index.scss'
   ```
4. 创建reset.scss
   ```scss
   *,
   *:after,
   *:before {
      box-sizing: border-box;
   
      outline: none;
   }
   
   html,
   body,
   div,
   span,
   applet,
   object,
   iframe,
   h1,
   h2,
   h3,
   h4,
   h5,
   h6,
   p,
   blockquote,
   pre,
   a,
   abbr,
   acronym,
   address,
   big,
   cite,
   code,
   del,
   dfn,
   em,
   img,
   ins,
   kbd,
   q,
   s,
   samp,
   small,
   strike,
   strong,
   sub,
   sup,
   tt,
   var,
   b,
   u,
   i,
   center,
   dl,
   dt,
   dd,
   ol,
   ul,
   li,
   fieldset,
   form,
   label,
   legend,
   table,
   caption,
   tbody,
   tfoot,
   thead,
   tr,
   th,
   td,
   article,
   aside,
   canvas,
   details,
   embed,
   figure,
   figcaption,
   footer,
   header,
   hgroup,
   menu,
   nav,
   output,
   ruby,
   section,
   summary,
   time,
   mark,
   audio,
   video {
      font: inherit;
      font-size: 100%;
   
      margin: 0;
      padding: 0;
   
      vertical-align: baseline;
   
      border: 0;
   }
   
   article,
   aside,
   details,
   figcaption,
   figure,
   footer,
   header,
   hgroup,
   menu,
   nav,
   section {
      display: block;
   }
   
   body {
      line-height: 1;
   }
   
   ol,
   ul {
      list-style: none;
   }
   
   blockquote,
   q {
      quotes: none;
   
      &:before,
      &:after {
         content: '';
         content: none;
      }
   }
   
   sub,
   sup {
      font-size: 75%;
      line-height: 0;
   
      position: relative;
   
      vertical-align: baseline;
   }
   
   sup {
      top: -.5em;
   }
   
   sub {
      bottom: -.25em;
   }
   
   table {
      border-spacing: 0;
      border-collapse: collapse;
   }
   
   input,
   textarea,
   button {
      font-family: inhert;
      font-size: inherit;
   
      color: inherit;
   }
   
   select {
      text-indent: .01px;
      text-overflow: '';
   
      border: 0;
      border-radius: 0;
   
      -webkit-appearance: none;
      -moz-appearance: none;
   }
   
   select::-ms-expand {
      display: none;
   }
   
   code,
   pre {
      font-family: monospace, monospace;
      font-size: 1em;
   }
   ```
5. 全局变量设置

- 创建src/style/variables.scss
- 在vite.config.ts中引入

   ```
   // scss全局变量配置
   css: {
      preprocessorOptions: {
         scss: {
            javascriptEnabled: true,
                    additionalData: '@import "./src/style/variable.scss";',
         },
      },
   },
   ```

## 引入全局icon

1. src/components/index.ts

   ```ts
   import * as ElementPlusIconVue from '@element-plus/icons-vue'
   
   export default {
     install: (app: any) => {
       //将element-plus提供图标注册为全局组件
       for (const [key, component] of Object.entries(ElementPlusIconVue)) {
         app.component(key, component)
       }
     },
   }
   ```
2. main.ts中引入

   ```ts
   //引入自定义插件对象:注册整个项目全局组件
   import gloalComponent from '@/components'
   
   app.use(gloalComponent)
   ```

## 引入全局组件icon

1. 安装

   ```shell
   pnpm install vite-plugin-svg-icons -D
   ```

2. vite.config.ts中引入

   ```ts
   import { defineConfig } from 'vite'
   import vue from '@vitejs/plugin-vue'
   import path from 'path'
   // 引入svg图标插件
   import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
   
   export default defineConfig({
     plugins: [
       vue(),
       // 注册svg图标插件
       createSvgIconsPlugin({
         // 配置需要自动导入的svg文件路径
         iconDirs: [path.resolve(process.cwd(), 'src/assets/icon')],
         // 配置symbolId格式
         symbolId: 'icon-[dir]-[name]',
       }),
     ],
     resolve: {
       // 相对路径别名配置，使用 @ 代替 src
       alias: {
         '@': path.resolve('./src'),
       },
     },
     // scss全局变量配置
     css: {
       preprocessorOptions: {
         scss: {
           javascriptEnabled: true,
           additionalData: '@import "./src/style/variable.scss";',
         },
       },
     },
     // 跨域配置
     server: {
       proxy: {
         '/api': {
           target: 'http://localhost:8000',
           changeOrigin: true,
           rewrite: (path) => path.replace(/^\/api/, ''),
         },
       },
     },
   })
   ```
3. 在main.ts中引入

   ```ts
   // 引入svg图标
   import 'virtual:svg-icons-register'
   ```
4. 在src/assets/icon中创建svg文件
   ```svg
   <svg>
     <use xlink:href="#icon-vue"></use>
   </svg>
   ```
5. 自定义模板
   > src/components/svgIcon/index.vue
   ```
   <template>
     <div>
       <svg :style="{ width, height }">
         <use :fill="color" :xlink:href="prefix + name"></use>
       </svg>
     </div>
   </template>
   
   <script lang="ts" setup>
   // 接收父组件传递的参数
   defineProps({
     // xlink:href的前缀
     prefix: {
       type: String,
       default: '#icon-',
     },
     // 图标名称
     name: String,
     // 颜色
     color: {
       type: String,
       default: '',
     },
     // 大小
     width: {
       type: String,
       default: '50px',
     },
     height: {
       type: String,
       default: '50px',
     },
   })
   </script>
   
   <style scoped></style>
   ```

# pinia仓库

1. 安装
   ```shell
   pnpm install pinia
   ```
2. 创建src/store/index.ts
   ```ts
    // 大仓库
   import { createPinia } from 'pinia'
   
   const pinia = createPinia()
   // 入口文件需要引入仓库
   export default pinia
   ```
3. main.ts中引入
   ```ts
   // 引入仓库
   import pinia from './store'
   app.use(pinia)
   ```
4. 创建小仓库
   ```ts
   //创建用户相关的小仓库
   import { defineStore } from 'pinia'
   
   export const useUserStore = defineStore('User', {
     state: () => {
       return {
         token: 'token test',
       }
     },
     actions: {},
   })
   ```
5. 注册全局组件
   ```ts
   // 对外暴露插件对象
   import SvgIcon from './SvgIcon/index.vue'
   import type { App, Component } from 'vue'
   
   const components: { [name: string]: Component } = { SvgIcon }
   
   export default {
     install(app: App) {
       Object.keys(components).forEach((key: string) => {
         app.component(key, components[key])
       })
     },
   }
   ```
6. main.ts

   ```ts
   import globalComponent from './components/index' // 引入全局组件
   app.use(globalComponent) // 注册全局组件
   ```
7. 使用
   ```
    <SvgIcon color="pink" name="home"></SvgIcon>
   ```

# 路由搭建

1. 安装
   ```shell
   pnpm install vue-router@4
   ```
2. 创建src/router/index.ts
   ```ts
   import { createRouter, createWebHashHistory } from 'vue-router'
   import { route } from './routes'
   // 创建路由器
   const router = createRouter({
     // 路由模式
     history: createWebHashHistory(),
     // 路由地址
     routes: route,
     // 滚动行为
     scrollBehavior: () => ({
       top: 0,
       left: 0,
     }),
   })
   
   export default router
   ```
3. 创建src/router/routes.ts
   ```ts
   const mainRoutes = [
     {
       path: '',
       component: () => import('@/view/main/index.vue'),
       name: 'main',
     },
   ]
   
   const loginRoutes = [
     {
       path: '/login',
       component: () => import('@/view/login/index.vue'),
       name: 'login',
     },
   ]
   
   export const route = Array.prototype.concat(mainRoutes, loginRoutes)
   ```
4. main.ts中引入
   ```ts
   import router from './router'
   app.use(router)
   ```

# axios封装

1. 安装
   ```shell
       pnpm install axios
   ``` 
2. 创建src/util/request.ts
   ```ts
   import axios from 'axios'
   import { ElMessage } from 'element-plus'
   
   const request = axios.create({
     baseURL: '/api', // 基础路径,仅作标识符，后配置跨域代理
     timeout: 5000,
   })
   
   // 请求拦截器
   request.interceptors.request.use((config) => {
     return config
   })
   
   // 第三步：响应拦截器
   request.interceptors.response.use(
     (response) => {
       // response 响应数据
       return response.data
     },
     (error) => {
       // error 错误信息
       let message = ''
       const status = error.response.status
       switch (status) {
         case 400:
           message = '请求错误'
           break
         case 401:
           message = '未授权的访问'
           break
         case 403:
           message = '禁止访问'
           break
         case 404:
           message = '资源不存在，请求地址错误'
           break
         case 500:
           message = '服务器内部错误'
           break
         default:
           message = '网络错误'
           break
       }
       return ElMessage({
         type: 'error',
         message,
       })
       return Promise.reject(error)
     },
   )
   export default request
   ```
3. 举例使用
   > ResponseData是一个泛型接口，用于约束返回的数据类型
   ```ts
   
   import request from '@/util/request'
   import {LoginParams, User} from '@/api/user/type'
   import {ResponseData} from '@/api/type'
   
   enum Api {
   LOGIN_URL = '/user/login',
   }
   
   export const reqUserLogin = (data: LoginParams) =>
   request.post<any, ResponseData<User>>(Api.LOGIN_URL, data)
   
   ```

# 无限滚动条

1. npm安装
   ```shell
   pnpm install vue-infinite-loading -S
   ```