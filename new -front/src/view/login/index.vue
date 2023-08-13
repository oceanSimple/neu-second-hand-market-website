<!--button样式的网站：https://www.lingdaima.com/css/#/-->
<template>
  <div id="background">
    <el-row>
      <el-col :span="12" :xs="0"></el-col>
      <el-col :span="12" :xs="24">
        <div id="form">
          <div id="title">用户登录</div>
          <div id="pic">
            <img
              src="@/assets/pluginPic/loginLogo.png"
              style="width: 75%; height: 75%"
            />
          </div>
          <div id="regisTip">
            新用户？
            <a @click="toRegister">点击注册!</a>
          </div>
          <div id="info">
            <div id="tip">
              <div>{{ formTip.left }}</div>
              <div>{{ formTip.right }}</div>
            </div>
            <div id="infoData">
              <div>
                <el-input
                  v-model="loginParams.code"
                  placeholder="账号"
                  @blur="checkCode"
                ></el-input>
              </div>
              <div>
                <el-input
                  v-model="loginParams.password"
                  placeholder="密码"
                  show-password
                ></el-input>
              </div>
            </div>
            <div class="findBackPsw" @click="toFindBackPsw">忘记密码？</div>
            <button @click="login">登录</button>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive } from 'vue'
import { useUserStore } from '@/store/userStore'
import router from '@/router'

let userStore = useUserStore()
// 登录参数
const loginParams = reactive({
  code: '',
  password: '',
})
// 表单校验tip
const formTip = reactive({
  left: '',
  right: '',
})

// 页面加载时检查token，通过token自动登录
onMounted(async () => {
  await userStore.checkToken()
})
// 登录事件
const login = async () => {
  if (checkCode()) {
    await userStore.login(loginParams)
  }
}
// 路由跳转：注册页面
const toRegister = () => {
  router.push('/register')
}
// 路由跳转：忘记密码页面
const toFindBackPsw = () => {
  router.push('/findBackPsw')
}
// 检查code
const checkCode = () => {
  console.log('checkCode')
  const rule = /^[0-9]{8}$/
  const flag = rule.test(loginParams.code)
  if (flag) {
    formTip.left = ''
  } else {
    formTip.left = '账号应为8位数字'
  }
  return flag
}
</script>

<style lang="scss" scoped>
#background {
  background: url('@/assets/background/loginBackground.png') no-repeat center;
  height: 100vh;
  width: 100%;
  background-size: cover;
  position: fixed;
}

#form {
  height: 80vh;
  width: 60%;
  margin-left: 20%;
  margin-top: 10vh;
  background: rgba(250, 240, 250, 0.5);
}

#title {
  text-align: center;
  font-family: '华文行楷';
  font-size: 50px;
}

#pic {
  padding-left: 20%;
}

#regisTip {
  text-align: center;
}

#regisTip > a {
  font-size: 20px;
  text-decoration: underline;
}

#info {
  height: 75px;
  width: 80%;
  margin-left: 10%;
  margin-top: 15%;
}

#tip {
  display: flex;
}

#tip > div {
  height: 25px;
  width: 50%;
  color: red;
  font-size: 10px;
  line-height: 25px;
  margin-left: 5px;
}

#infoData {
  display: flex;
}

#infoData > div {
  height: 50px;
  width: 50%;
}

#submit {
  height: 30px;
  width: 30%;
  margin-left: 35%;
  text-align: center;
  background-color: bisque;
  line-height: 30px;
}

.findBackPsw {
  font-size: 10px;
  color: rgb(35, 42, 238);
  text-align: right;
  text-decoration: underline;
}

button {
  width: 30%;
  height: 3em;
  margin-left: 35%;
  margin-top: 3%;
  border-radius: 30em;
  font-size: 15px;
  font-family: inherit;
  border: none;
  position: relative;
  overflow: hidden;
  z-index: 1;
  box-shadow:
    6px 6px 12px #c5c5c5,
    -6px -6px 12px #ffffff;
}

button::before {
  content: '';
  width: 0;
  height: 3em;
  border-radius: 30em;
  position: absolute;
  top: 0;
  left: 0;
  background-image: linear-gradient(
    to right,
    rgb(232, 237, 238) 0%,
    #10f9da 100%
  );
  transition: 0.5s ease;
  display: block;
  z-index: -1;
}

button:hover::before {
  width: 9em;
}
</style>