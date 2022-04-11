<template>
  <el-card shadow="hover">
    <template #header>我的邮箱</template>
    <el-form :model="formdate">
      <el-form-item label="邮箱:">
        <el-input v-model="formdate.mail"
                  :disabled="userdata.mailcod=='验证'" />
      </el-form-item>
    </el-form>
    <div style="float: right;">
      <el-button v-show="!userdata.mailcod=='验证'"
                 @click="agcod">验证邮箱</el-button>
      <el-button v-show="userdata.mailcod=='验证'">发送密钥</el-button>
    </div>
    <br>
  </el-card>
  <br>
  <el-card v-show="!userdata.mailcod=='验证'">
    <el-result icon="error"
               title="无法改密"
               sub-title="未验证邮箱，无法进行改密操作" />

  </el-card>
  <el-card shadow="hover"
           v-show="userdata.mailcod=='验证'">
    <template #header>密码安全</template>
    <el-form :model="formdate">
      <el-popover placement="top"
                  title="说明"
                  :width="200"
                  trigger="hover"
                  content="需要发送邮件进行密钥验证才能进行修改密码！">
        <template #reference>
          <el-form-item label="验证密钥:">
            <el-input v-model="formdate.cod" />
          </el-form-item>
        </template>
      </el-popover>

      <el-form-item label="新密码:">
        <el-input v-model="formdate.npassword" />
      </el-form-item>
      <el-form-item label="确认密码:">
        <el-input v-model="formdate.npassword1" />
      </el-form-item>
    </el-form>
    <div style="float: right;">
      <el-button>更改</el-button>
    </div>
    <br>
  </el-card>

</template>

<script>
import { reactive, ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
const userdata = ref({
  username: "",
  mail: "",
  mailcod: "验证"
})
export default {
  userdata,
  setup () {
    const formdate = reactive({
      mail: userdata.value.mail,
      cod: "",
      npassword: "",
      npassword1: ""
    })
    const agcod = () => {
      axios.post("http://127.0.0.1:8888" + "/users/agSeedCodMail", {
        username: userdata.value.username,
        mail: formdate.mail
      }).then(res => {
        console.log(res.data);
        ElMessage({
          message: res.data.msg,
          type: 'success',
        })
      })
    }
    return {
      formdate, userdata, agcod
    }
  }
}
</script>

<style>
button {
  float: left;
}
</style>