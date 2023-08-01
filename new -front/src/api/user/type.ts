// 用户类数据类型
export interface User {
  id: string
  code: string
  password: string
  nickname: string
  address: string
  isDelete: string
  gmtCreate: string
  gmtModified: string
  email: string
  phone: string
  token: string
}

// 提交：用户登录
export interface LoginParams {
  code: string
  password: string
}

// 提交：token自动登录
export interface CheckTokenParams {
  code: string
  token: string
}

// 提交：通过邮箱获取验证码
export interface GetVerifyCodeByEmailParams {
  email: string
}