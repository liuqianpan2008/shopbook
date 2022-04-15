<template>
  <el-card>
    <template #header>
      <span>订单详情</span>
      <div style="float:right">
        <el-tag>支付中</el-tag>
      </div>
    </template>
    <el-card>
      <template #header>
        <span>购买详情</span>
      </template>
      <el-table :data="tableData"
                style="width: 100%">
        <el-table-column prop="date"
                         label="书名" />
        <el-table-column prop="name"
                         label="数量" />
        <el-table-column prop="Address"
                         label="操作" />
      </el-table>
      <br>
      <div style="float:right">
        价格：0
      </div>
    </el-card>
    <el-divider />
    <el-card>
      <template #header>
        <span>快递填写</span>
      </template>
      <el-form :model="formData"
               label-position="top">
        <el-form-item label="姓名">
          <el-input v-model="formData.name" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="formData.phone" />
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="formData.addr" />
        </el-form-item>
      </el-form>
    </el-card>
    <el-divider />
    <el-button>去付款</el-button>
  </el-card>
</template>

<script>
import { ref } from '@vue/reactivity'
export default {
  setup () {
    const formData = ref({
      name: "",
      phone: "",
      addr: "",
    })
    return { formData }
  }

}
</script>

<style>
</style>