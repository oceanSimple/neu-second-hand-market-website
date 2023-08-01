import request from '@/util/request'
import {
  CheckTokenParams,
  GetVerifyCodeByEmailParams,
  LoginParams,
  User,
} from '@/api/user/type'
import { ResponseData } from '@/api/type'

enum Api {
  LOGIN_URL = '/user/login',
  CHECK_TOKEN_URL = '/user/checkToken',
  GET_VERIFY_CODE_BY_EMAIL_URL = '/mqProducer/emailVerificationCode',
}

export const reqUserLogin = (data: LoginParams) =>
  request.post<any, ResponseData<User>>(Api.LOGIN_URL, data)
export const reqUserCheckToken = (data: CheckTokenParams) =>
  request.post<any, ResponseData<User>>(Api.CHECK_TOKEN_URL, data)
export const reqGetVerifyCodeByEmail = (data: GetVerifyCodeByEmailParams) =>
  request.post<any, number>(Api.GET_VERIFY_CODE_BY_EMAIL_URL, data)