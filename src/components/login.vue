<template>
  <el-breadcrumb :separator-icon="ArrowRight">
    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>登录</el-breadcrumb-item>
  </el-breadcrumb>
  <br>
  <br>
  <div id="juzhong">
    <el-card class="box-card">
      <el-space wrap>
        <el-card>

          <el-tabs v-model="activeName"
                   class="demo-tabs"
                   @tab-click="handleClick">
            <el-tab-pane label="密码登录"
                         name="first">
              <el-form ref="ruleFormRef"
                       :model="formdata"
                       :rules="rules"
                       label-width="80px"
                       label-position="top">
                <el-form-item label="账号"
                              prop="username">
                  <el-input v-model="formdata.username" />
                </el-form-item>
                <el-form-item label="密码"
                              prop="password">
                  <el-input show-password
                            v-model="formdata.password" />
                </el-form-item>
                <el-form-item label="">
                  <el-checkbox-group v-model="formdata.type">
                    <el-checkbox label="user"
                                 name="user">记住用户名</el-checkbox>
                    <el-checkbox label="autologin"
                                 name="autologin">两周内自动登录</el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary"
                             @click="login">登录</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="扫码登录"
                         name="second">
              <qrcode-vue :value="dataQC"
                          :size="200"
                          level="H" /><br />
              <span> {{restext.msg}}</span>
            </el-tab-pane>
          </el-tabs>
        </el-card>
        <el-card style="height: 365px; ">
          <template #header>
            <div class="card-header">
              <span>没有注册？</span>
            </div>
          </template>
          <p>注册新用户，享受更低的价格</p>
          <p>千种图书，供你挑选!注册即享受丰富折扣和优惠，便宜有好货!超过万本图书任您选。</p>
          <p>超人气社区!精彩活动每一天。买卖更安心!支付宝交易超安全。</p>
          <el-button type="success"
                     @click="register">马上注册</el-button>
        </el-card>
      </el-space>
    </el-card>
  </div>

</template>

<script>
import { ArrowRight } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import QrcodeVue from 'qrcode.vue'

export default {
  components: {
    QrcodeVue
  },
  setup () {
    const activeName = ref('first')
    const ruleFormRef = ref();
    const formdata = ref({
      username: '',
      password: '',
      type: [],
    })
    const dataQC = ref()
    const restext = ref({
      "flag": true,
      "date": { "cod": false, "data": "1" },
      "msg": ""
    })
    var myVar
    const handleClick = (tab, event) => {
      console.log(tab)
      console.log(event)
    }
    //读取用户名和密码
    if (window.sessionStorage.getItem('user') != null) {
      formdata.value.username = window.sessionStorage.getItem('user');
      formdata.value.type.push("user")
    }
    if (window.sessionStorage.getItem('paw') != null) {
      formdata.value.password = window.sessionStorage.getItem('paw');
      formdata.value.type.push("autologin")
    }
    const register = () => {
      window.location.href = "#/register";
    }
    const login = () => {
      console.log("点击了登录");
      console.log(formdata.value.type);
      ruleFormRef.value.validate((valid) => {
        if (valid) {
          axios.post(`http://127.0.0.1:8888/users/login`, {
            userName: formdata.value.username,
            userPassword: formdata.value.password
          }).then((ret) => {
            if (ret.data.date.cod == 'true') {
              ElMessage({
                message: ret.data.msg,
                type: 'success',
              })
              window.sessionStorage.setItem('token', ret.data.date.token)
              window.location.href = "#/userinfo/userData";
              //判断写入
              for (let index = 0; formdata.value.type != null && index < formdata.value.type.length; index++) {
                if (formdata.value.type[index] == "user") {
                  window.sessionStorage.setItem('paw', formdata.value.username)
                  if (window.sessionStorage.getItem('paw') != null) {
                    window.sessionStorage.removeItem("paw")

                  }
                }
                if (formdata.value.type[index] == "autologin") {
                  window.sessionStorage.setItem('user', formdata.value.username)
                  window.sessionStorage.setItem('paw', formdata.value.password)
                }

              }

            } else {
              ElMessage.error(ret.data.msg)
            }
          })
        }
      })
    }

    // 扫码登录
    axios.get("http://127.0.0.1:8888/users/logincod").then(res => {
      dataQC.value = res.data.date;
      myVar = setInterval(function () { myTimer() }, 3000);
    })
    function myTimer () {
      console.log("发送心跳包");
      axios.post("http://127.0.0.1:8888/users/verificationCod", {
        cod: dataQC.value
      }).then(res => {
        console.log(res.data);
        restext.value = res.data
        if (restext.value.date.cod == true) {
          //     const restext = ref({
          //   "flag": true,
          //   "date": { "cod": false, "data": "1" },
          //   "msg": ""
          // })
          window.sessionStorage.setItem('token', restext.value.date.data)
          window.location.href = "#/userinfo/userData";
          clearInterval(myVar);
        }
      })
    }
    const rules = reactive({
      username: [
        {
          required: true,
          message: '请输入用户名',
          trigger: 'blur'
        }, {
          min: 3,
          max: 5,
          message: '用户名长度应该在3~5之间',
          trigger: 'blur'
        },
      ],
      password: [
        {
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }, {
          min: 3,
          max: 10,
          message: '密码长度应该在3~10之间',
          trigger: 'blur'
        },
      ]
    })
    return {
      formdata,
      ArrowRight,
      register,
      login,
      rules,
      ruleFormRef,
      activeName,
      handleClick,
      dataQC,
      restext
    }
  }
}

</script>

<style>
#juzhong {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 600px;
}
</style>