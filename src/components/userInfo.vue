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
          <el-form :model="form"
                   label-width="80px"
                   label-position="top">
            <el-form-item label="账号">
              <el-input v-model="form.username" />
            </el-form-item>
            <el-form-item label="密码">
              <el-input show-password
                        v-model="form.password" />
            </el-form-item>
            <el-form-item label="">
              <el-checkbox-group v-model="form.type">
                <el-checkbox label="记住用户名"
                             name="user" />
                <el-checkbox label="两周内自动登录"
                             name="autologin" />
              </el-checkbox-group>
            </el-form-item>
            <el-form-item>
              <el-button type="primary"
                         @click="onSubmit">登录</el-button>
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
          <el-button type="success">马上注册</el-button>
        </el-card>
      </el-space>
    </el-card>
  </div>

</template>

<script>
import { ArrowRight } from '@element-plus/icons-vue'
import { reactive } from 'vue'
export default {
  setup () {
    const form = reactive({
      username: '',
      password: '',
      type: [],
    })
    return { form, ArrowRight }
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