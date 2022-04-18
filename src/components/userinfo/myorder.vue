<template>
  <el-card>
    <el-table :data="datas"
              style="width: 100%">
      <el-table-column prop="orderid"
                       label="订单号" />
      <el-table-column prop="book.name"
                       label="书名" />
      <el-table-column prop="book.price"
                       label="单价" />
      <el-table-column prop="num"
                       label="数量" />
      <el-table-column prop="cod"
                       label="状态" />
      <el-table-column prop="address"
                       label="操作">
        <template #default="scope">
          <el-button @click="look(scope.row.book)"
                     size="small">去看看</el-button>
          <el-button @click="pay(scope.row.orderid)"
                     size="small">去支付</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import { ref } from '@vue/reactivity'
import bookinfo from '.././book/bookinfo.vue'
const datas = ref([{
  orderid: "空",
  book: {
    name: "",
    price: ""
  },
  num: 0,
  cod: ""
}])
export default {
  datas,
  setup () {
    const look = (v) => {
      bookinfo.bookData.value = v
      console.log(v);
      window.location.href = "#/bookinfo"
    }
    const pay = (id) => {
      window.location.href = "#/order/" + id
    }
    return { datas, look, pay }
  }
}
</script>

<style>
</style>