import request from '@/util/request.ts'
import {
  AddFriendParams,
  DeleteFriendParams,
  Friend,
  GetMessageParams,
  Message,
} from '@/api/message/type.ts'
import { ResponseData } from '@/api/type.ts'

enum Api {
  GET_FRIEND_lIST_URL = '/friend/',
  FRIEND_URL = '/friend',
  DELETE_FRIEND_URL = '/friend/delete',
  GET_MESSAGE_LIST_URL = '/message/getMessage',
}

export const reqGetFriendList = (code: string) =>
  request.get<any, ResponseData<Friend[]>>(Api.GET_FRIEND_lIST_URL + code)
export const reqAddFriend = (params: AddFriendParams) =>
  request.post<any, ResponseData<string>>(Api.FRIEND_URL, params)
export const reqDeleteFriend = (params: DeleteFriendParams) =>
  request.post<any, ResponseData<string>>(Api.DELETE_FRIEND_URL, params)
export const reqGetMessageList = (params: GetMessageParams) =>
  request.post<any, ResponseData<Message[]>>(Api.GET_MESSAGE_LIST_URL, params)