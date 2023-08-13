//创建用户相关的小仓库
import { defineStore } from 'pinia'
import {
  CheckVerifyCodeParams,
  GetVerifyCodeByEmailParams,
  LoginParams,
  RegisterParams,
  UpdateParams,
  User,
} from '@/api/user/type'
import {
  reqCheckVerifyCode,
  reqGetVerifyCodeByEmail,
  reqUserCheckToken,
  reqUserLogin,
  reqUserRegister,
  reqUserUpdate,
} from '@/api/user/user'
import { getObj, setObj } from '@/util/localStorageObj'
import { ElMessage } from 'element-plus'
import router from '@/router'
import { Setting } from '@/view/main/setting'
import { ruleItem, rules } from '@/util/formRules.ts'

const defaultSetting: Setting = {
  fold: false,
}

export const useUserStore = defineStore('User', {
  state: () => {
    return {
      token: 'token test',
      dialogPasswordVisible: false,
      dialogEmailVisible: false,
      userInfo: getObj<User>('user') as User,
    }
  },
  actions: {
    // 用户登录(账号密码)
    async login(data: LoginParams) {
      const result = await reqUserLogin(data)
      if (result.code === 201) { // 登录成功
        // 将数据信息存储到localStorage中
        setObj<User>('user', result.data)
        // 将默认设置存储到localStorage中
        setObj<Setting>('setting', defaultSetting)
        ElMessage.success('登录成功')
        await router.push('/homePage')
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
          await router.push('/homePage')
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
    // 检查验证码
    async checkVerifyCode(data: CheckVerifyCodeParams) {
      const result = await reqCheckVerifyCode(data)
      if (result.code !== 200) {
        ElMessage.error('验证码错误！')
      }
      return result.code === 200
    },
    // 注册
    async register(data: RegisterParams) {
      const result = await reqUserRegister(data)
      if (result.code === 200) {
        ElMessage.success('注册成功')
        // 用注册信息自动调用login函数，实现自动登录（不用返回数据的原因是，password被md5加密了）
        await this.login({
          code: data.code,
          password: data.password,
        })
      } else {
        ElMessage.error(result.msg)
      }
    },
    // 更新用户信息
    async update(data: UpdateParams) {
      // @ts-ignore
      const rule: ruleItem = rules[data.target]
      // 对需要验证的数据进行验证
      if (rule !== undefined) {
        const checkData = rule.pattern.test(data.data)
        // 如果验证不通过，则提示错误信息
        if (!checkData) {
          // @ts-ignore
          ElMessage.error(rules[data.target].message)
          return
        }
      }
      const result = await reqUserUpdate(data)
      // 如果更新成功，则更新本地存储的用户信息
      if (result.code === 200) {
        setObj<User>('user', result.data)
        ElMessage.success(data.tip + '更新成功')
      } else {
        ElMessage.error(result.msg)
      }
    },
  },
})