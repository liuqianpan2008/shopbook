<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>邮箱验证结果页</span>
      </div>
    </template>
    <el-result :icon="icon==0?'error':'success'"
               :title="icon==0?'错误':'成功'"
               :sub-title="title" />
  </el-card>
</template>

<script>
import axios from 'axios'
import { useRoute } from 'vue-router'
import { ref } from '@vue/reactivity'
export default {

  setup () {
    const cod = useRoute().params.cod
    const title = ref("")
    const icon = ref(0)
    axios.post(
      "http://127.0.0.1:8888" + "/users/MailCod",
      {
        username: useRoute().params.userName,
        cod: useRoute().params.cod
      }

    ).then((e) => {
      console.log(e.data.date);
      if (e.data.date) {
        title.value = e.data.msg
        icon.value = 1
      } else {
        icon.value = 0
      }
    });
    return {
      cod, title, icon
    }
  }
}
</script>

<style>
</style>