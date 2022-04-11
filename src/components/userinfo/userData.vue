<template>
  <div class="juzhong">
    <el-card class="box-card">
      <el-descriptions class="margin-top"
                       title="个人资料"
                       :column="1"
                       size="large"
                       border>
        <template #extra>
          <el-button type="danger"
                     @click="loginout">注销</el-button>
        </template>

        <el-descriptions-item min-width="250px"
                              align="center">
          <template #label>
            <div class="cell-item">
              用户名
            </div>
          </template>
          {{userdata.userName}}
        </el-descriptions-item>
        <el-descriptions-item min-width="250px"
                              align="center">
          <template #label>
            <div class="cell-item">
              性别
            </div>
          </template>
          {{userdata.sex}}
        </el-descriptions-item>

        <el-descriptions-item min-width="250px"
                              align="center">
          <template #label>
            <div class="cell-item">
              电话号码
            </div>
          </template>
          {{userdata.phone}}
        </el-descriptions-item>
        <el-descriptions-item min-width="250px"
                              align="center">
          <template #label>
            <div class="cell-item">
              邮箱
            </div>
          </template>
          {{userdata.mail}}<el-tag :type="userdata.mailcod=='验证'?'success':'warning'">{{userdata.mailcod}}</el-tag>
        </el-descriptions-item>

        <el-descriptions-item min-width="250px"
                              align="center">
          <template #label>
            <div class="cell-item">
              个性签名
            </div>
          </template>
          {{userdata.introduce}}
        </el-descriptions-item>

      </el-descriptions>
    </el-card>

  </div>

</template>

<script>
import { ref } from '@vue/reactivity'
import axios from 'axios'
import { ElMessage } from 'element-plus'
const userdata = ref()
export default {
  userdata,
  setup () {

    return {
      userdata, loginout: () => {
        console.log("注销");

        axios({
          url: "http://127.0.0.1:8888" + "/users/loginout",
          method: "POST",
          headers: {
            satoken: window.sessionStorage.getItem("token"),
          },
        }).then((e) => {
          console.log(e.data.date);
          if (e.data.date) {
            window.sessionStorage.removeItem("token");
            window.location.href = "#/";
            ElMessage({
              message: e.data.msg,
              type: 'success',
            })
          }

        });
      }
    }
  }
}
</script>

<style>
.juzhong {
  display: flex;
  justify-self: start;
}
</style>