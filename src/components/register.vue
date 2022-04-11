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
               label-width="80px"
               ref="ruleFormRef"
               :rules="rules">
        <el-form-item label="邮箱"
                      prop="mail">
          <el-input v-model="form.mail" />
        </el-form-item>
        <el-form-item label="用户名"
                      prop="username">
          <el-input v-model="form.username" />
        </el-form-item>

        <el-form-item label="密码"
                      prop="userpassword">
          <el-input v-model="form.userpassword"
                    show-password />
        </el-form-item>
        <el-form-item label="确认密码"
                      prop="userpassword1">
          <el-input v-model="form.userpassword1"
                    show-password />
        </el-form-item>
        <el-form-item label="性别"
                      prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio label="0">男</el-radio>
            <el-radio label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系电话"
                      prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="个人介绍"
                      prop="introduce">
          <el-input v-model="form.introduce"
                    :autosize="{ minRows: 2, maxRows: 4 }"
                    type="textarea"
                    placeholder="个人介绍" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary"
                     @click="onSubmit"
                     :loading="registering">注册</el-button>
        </el-form-item>
      </el-form>

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
    const ruleFormRef = ref()
    const form = reactive({
      mail: '',
      username: '',
      userpassword: '',
      userpassword1: '',
      sex: '',
      phone: '',
      introduce: '',
    })
    const registering = ref(false)

    const validmail = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('邮箱不能为空'))
      } else {
        var pattern = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
        if (pattern.test(value)) {
          callback()
        } else {
          callback(new Error('邮箱格式不正确'))
        }
      }
    }

    const validpassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== form.userpassword) {
        callback(new Error("两次密码不一致"))
      } else {
        callback()
      }
    }
    const rules = reactive({
      mail: [
        { validator: validmail, trigger: 'blur' }
      ],
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 3, max: 5, message: '用户名长度应该在3~5之间', trigger: 'blur' }
      ],
      userpassword: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 3, max: 10, message: '密码长度应该在3~10之间', trigger: 'blur' }
      ],
      userpassword1: [
        { validator: validpassword, trigger: 'blur' }
      ],
      sex: [
        { required: true, message: '请选择你的性别', trigger: 'change', }
      ],
      phone: [
        { min: 11, max: 11, message: '电话号码应该在11位', trigger: 'blur' }
      ],
      introduce: [
        { min: 0, max: 50, message: '个人标签只支持50字符', trigger: 'blur' }
      ]
    })
    const onSubmit = () => {
      registering.value = true
      ruleFormRef.value.validate((valid) => {
        if (valid) {
          axios.post("http://127.0.0.1:8888" + "/users/register", {
            userName: form.username,
            userPassword: form.userpassword,
            mail: form.mail,
            sex: form.sex,
            phone: form.phone,
            introduce: form.introduce
          }).then((res) => {
            console.log(res.data);
            registering.value = false;
            ElMessage({
              message: res.data.msg,
              type: 'success',
            })
          })
        }
      })
    }
    return {
      form, ArrowRight, rules, ruleFormRef,
      onSubmit, registering
    }
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