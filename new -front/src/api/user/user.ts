import request from '@/util/request'
import {
  CheckTokenParams,
  CheckVerifyCodeParams,
  GetVerifyCodeByEmailParams,
  LoginParams,
  RegisterParams,
  UpdateParams,
  User,
} from '@/api/user/type'
import { ResponseData } from '@/api/type'

enum Api {
  LOGIN_URL = '/user/login',
  CHECK_TOKEN_URL = '/user/checkToken',
  GET_VERIFY_CODE_BY_EMAIL_URL = '/mqProducer/emailVerificationCode',
  REGISTER_URL = '/user/register',
  CHECK_VERIFY_CODE_URL = '/user/checkVerificationCode',
  UPDATE_URL = '/user/update',
}

export const reqUserLogin = (data: LoginParams) =>
  request.post<any, ResponseData<User>>(Api.LOGIN_URL, data)
export const reqUserCheckToken = (data: CheckTokenParams) =>
  request.post<any, ResponseData<User>>(Api.CHECK_TOKEN_URL, data)
export const reqGetVerifyCodeByEmail = (data: GetVerifyCodeByEmailParams) =>
  request.post<any, number>(Api.GET_VERIFY_CODE_BY_EMAIL_URL, data)
export const reqUserRegister = (data: RegisterParams) =>
  request.post<any, ResponseData<User>>(Api.REGISTER_URL, data)
export const reqCheckVerifyCode = (data: CheckVerifyCodeParams) =>
  request.post<any, ResponseData<string>>(Api.CHECK_VERIFY_CODE_URL, data)
export const reqUserUpdate = (data: UpdateParams) =>
  request.post<any, ResponseData<User>>(Api.UPDATE_URL, data)