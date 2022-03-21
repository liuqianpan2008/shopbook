<template>
  <el-breadcrumb :separator-icon="ArrowRight">
    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>注册</el-breadcrumb-item>
  </el-breadcrumb>
  <div id="juzhong">
    <el-card class="box-card1">
      <template #header>
        <span>注册新用户</span>
      </template>
      <el-form :model="form"
               label-width="80px">
        <el-form-item label="邮箱">
          <el-input v-model="form.mail" />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.userpassword"
                    type="password" />
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input v-model="form.userpassword1"
                    type="password" />
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="男" />
            <el-radio label="女" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="个人介绍">
          <el-input v-model="form.introduce"
                    :autosize="{ minRows: 2, maxRows: 4 }"
                    type="textarea"
                    placeholder="个人介绍" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary"
                     @click="onSubmit">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>

</template>

<script>
import { ArrowRight } from '@element-plus/icons-vue'
import { reactive } from 'vue'
export default {
  setup () {
    const form = reactive({
      mail: '',
      username: '',
      userpassword: '',
      userpassword1: '',
      sex: [],
      phone: '',
      introduce: ''
    })
    return { form, ArrowRight }
  }
}
</script>

<style>
.juzhong {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 600px;
}
.box-card1 {
  width: 50%;
}
</style>