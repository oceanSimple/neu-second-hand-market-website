//创建用户相关的小仓库
import { defineStore } from 'pinia'
import { GetVerifyCodeByEmailParams, LoginParams, User } from '@/api/user/type'
import {
  reqGetVerifyCodeByEmail,
  reqUserCheckToken,
  reqUserLogin,
} from '@/api/user/user'
import { getObj, setObj } from '@/util/localStorageObj'
import { ElMessage } from 'element-plus'

export const useUserStore = defineStore('User', {
  state: () => {
    return {
      token: 'token test',
    }
  },
  actions: {
    // 用户登录(账号密码)
    async login(data: LoginParams) {
      const result = await reqUserLogin(data)
      if (result.code === 201) { // 登录成功
        // 将数据信息存储到localStorage中
        setObj<User>('user', result.data)
        ElMessage.success('登录成功')
      } else {
        ElMessage.error(result.msg)
      }
    },
    // 用户登录(token自动登录)
    async checkToken() {
      const user = getObj<User>('user')
      if (user) {
        const data = {
          code: user.code,
          token: user.token,
        }
        const result = await reqUserCheckToken(data)
        if (result.code === 200) { // 登录成功
          ElMessage.success('登录成功')
        }
      }
    },
    // 邮箱获取验证码
    async getVerifyCodeByEmail(data: GetVerifyCodeByEmailParams) {
      const result = await reqGetVerifyCodeByEmail(data)
      if (result === 200) {
        ElMessage.success('验证码已发送')
      } else {
        ElMessage.error('验证码发送失败')
      }
    },
  },
})