<template>
  <div class="container">
    <el-card>
      <template #header>
        <el-icon @click="toLogin">
          <component is="Back"></component>
        </el-icon>
        <span style="font-size: 15px" @click="toLogin">返回</span>
        <span style="margin-left: 35%; font-size: 20px">注册</span>
      </template>
      <el-form
        ref="canCommit"
        :model="registerParams"
        :rules="rules"
        label-width="25%"
      >
        <el-form-item label="账号" prop="code">
          <el-input
            v-model="registerParams.code"
            placeholder="code"
            prefix-icon="User"
          ></el-input>
        </el-form-item>

        <el-form-item label="昵称" prop="nickname">
          <el-input
            v-model="registerParams.nickname"
            placeholder="nickname"
            prefix-icon="Brush"
          ></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
            v-model="registerParams.password"
            placeholder="password"
            prefix-icon="Lock"
            show-password
            type="password"
          ></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="password2">
          <el-input
            v-model="registerParams.password2"
            placeholder="confirm password"
            prefix-icon="Lock"
            show-password
            type="password"
          ></el-input>
        </el-form-item>

        <el-form-item label="校区">
          <el-select v-model="campus" size="default">
            <el-option
              v-for="item in campusArray"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="宿舍楼">
          <el-select v-model="dormitory" size="default">
            <el-option
              v-for="item in dormitoryArray"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input
            v-model="registerParams.email"
            placeholder="email"
            prefix-icon="Message"
          ></el-input>
        </el-form-item>

        <el-button
          plain
          size="small"
          style="float: right"
          type="info"
          @click="getVerificationCode"
        >
          发送验证码
        </el-button>

        <div style="height: 5vh"></div>

        <el-form-item label="验证码" prop="verificationCode">
          <el-input
            v-model="registerParams.verificationCode"
            placeholder="verification code"
          ></el-input>
        </el-form-item>

        <button class="submitButton" @click="submit">提交</button>
      </el-form>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { useUserStore } from '@/store/userStore'
import { ElNotification } from 'element-plus'
import { campusArray, dormitoryArray } from '@/view/register/static'
import { RegisterParams } from '@/api/user/type'
import router from '@/router'

let userStore = useUserStore()
const canCommit = ref() // 表单校验
const campus = ref(campusArray[0].value) // 校区
const dormitory = ref(dormitoryArray[0].value) // 宿舍楼

// 注册参数
const registerParams = reactive({
  code: '',
  password: '',
  password2: '',
  nickname: '',
  email: '',
  verificationCode: '',
})

// 确认密码校验
// @ts-ignore
const checkConfirmPassword = (rule, value, callback) => {
  if (value === registerParams.password) {
    return callback()
  } else {
    return callback(new Error('两次输入密码不一致'))
  }
}
// 表单校验规则
const rules = reactive({
  code: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    {
      pattern: /^[0-9]{8}$/,
      message: '账号必须是8位数字',
      trigger: 'blur',
    },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      pattern: /^[a-zA-Z0-9]{6,16}$/,
      message: '密码必须是6-16位字母或数字',
      trigger: 'blur',
    },
  ],
  password2: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    {
      validator: checkConfirmPassword,
      trigger: 'blur',
    },
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    {
      pattern: /^[a-zA-Z0-9]{1,20}$/,
      message: '昵称必须是1-20个字母或数字',
      trigger: 'blur',
    },
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    {
      pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
      message: '邮箱格式错误',
      trigger: 'blur',
    },
  ],
})

// 检查邮箱格式
const checkEmailPattern = () => {
  console.log(registerParams.email)
  const emailPattern = new RegExp(
    '^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$',
  )
  return emailPattern.test(registerParams.email)
}
// 获取验证码
const getVerificationCode = () => {
  if (checkEmailPattern()) {
    userStore.getVerifyCodeByEmail({ email: registerParams.email })
    ElNotification({
      title: '成功',
      message: '验证码已发送',
      type: 'success',
    })
  } else {
    ElNotification({
      title: '错误',
      message: '邮箱格式错误',
      type: 'error',
    })
  }
}
// 提交注册
const submit = async () => {
  // 检查表单
  await canCommit.value.validate()
  // 检查验证码
  const result = await userStore.checkVerifyCode({
    email: registerParams.email,
    code: registerParams.verificationCode,
  })
  // 提交注册
  if (result) {
    const registerData: RegisterParams = {
      code: registerParams.code,
      password: registerParams.password,
      nickname: registerParams.nickname,
      email: registerParams.email,
      campus: campus.value,
      dormitory: dormitory.value,
    }
    await userStore.register(registerData)
  }
}
const toLogin = () => {
  router.push('/login')
}
</script>

<style lang="scss" scoped>
.container {
  width: 40vw;
  margin-left: 30%;
  margin-top: 10vh;
}

.submitButton {
  margin-left: 40%;
  color: #090909;
  padding: 0.7em 1.7em;
  font-size: 12px;
  border-radius: 0.5em;
  background: #e8e8e8;
  border: 1px solid #e8e8e8;
  transition: all 0.3s;
  box-shadow:
    6px 6px 12px #c5c5c5,
    -6px -6px 12px #ffffff;
}

.submitButton:hover {
  border: 1px solid white;
}

.submitButton:active {
  box-shadow:
    4px 4px 12px #c5c5c5,
    -4px -4px 12px #ffffff;
}
</style>