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
          <template #header>
            <div class="card-header">
              <span>用户登录</span>
            </div>
          </template>
          <el-form ref="ruleFormRef"
                   :model="form"
                   :rules="rules"
                   label-width="80px"
                   label-position="top">
            <el-form-item label="账号"
                          prop="username">
              <el-input v-model="form.username" />
            </el-form-item>
            <el-form-item label="密码"
                          prop="password">
              <el-input show-password
                        v-model="form.password" />
            </el-form-item>
            <el-form-item label="">
              <el-checkbox-group v-model="form.type">
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


export default {
  setup () {
    const ruleFormRef = ref();
    const form = reactive({
      username: '',
      password: '',
      type: [],
    })

    const register = () => {
      window.location.href = "#/register";
    }
    const login = () => {
      console.log("点击了登录");
      ruleFormRef.value.validate((valid) => {
        if (valid) {
          axios.post(`http://127.0.0.1:8888/users/login`, {
            userName: form.username,
            userPassword: form.password
          }).then((ret) => {
            if (ret.data.date.cod == 'true') {
              ElMessage({
                message: ret.data.msg,
                type: 'success',
              })
              window.sessionStorage.setItem('token', ret.data.date.token)
              window.location.href = "#/userinfo/userData";
            } else {
              ElMessage.error(ret.data.msg)
            }
          })
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
    return { form, ArrowRight, register, login, rules, ruleFormRef }
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