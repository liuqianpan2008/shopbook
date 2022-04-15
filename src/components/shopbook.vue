<template>
  <el-table :data="shopData"
            style="width: 100%"
            @selection-change="handleSelectionChange">
    <el-table-column type="selection"
                     width="55" />
    <el-table-column prop="name"
                     label="书籍名称" />
    <el-table-column prop="unitprice"
                     label="单价" />
    <el-table-column prop="quantity"
                     label="购买数量" />

    <el-table-column prop="totalprice"
                     label="总价" />
  </el-table>
  <div class="Totalprice">总价:{{Totalprice}}元</div><br>
  <el-button v-show="delbt"
             @click="dalshop">删除</el-button>
  <el-button>去结算</el-button>

</template>

<script>
import { ref } from '@vue/reactivity'
import axios from 'axios'
import { ElMessage } from 'element-plus'
const shopData = ref([{
  name: "无",
  unitprice: 1,
  quantity: 1,
  Totalprice: 1
}])
export default {
  shopData,
  setup () {
    const delbt = ref(false)
    const Totalprice = ref(0)
    const shops = ref()
    const handleSelectionChange = (val) => {
      if (val.length == 0) { delbt.value = false } else { delbt.value = true }
      Totalprice.value = 0
      val.forEach(element => {
        Totalprice.value += element.totalprice
      });
      shops.value = val
    }
    const dalshop = () => {
      shops.value.forEach(v => {
        axios({
          url: "http://127.0.0.1:8888" + "/users/dalshop",
          method: "POST",
          headers: {
            satoken: window.sessionStorage.getItem("token"),
          },
          data: {
            name: v.name,
          }
        }).then((e) => {
          console.log(e.data.date);
          if (e.data.flag == false) {
            window.sessionStorage.removeItem("token");
            window.location.href = "#/login"
          }
          if (!e.data.date) {
            ElMessage.error(e.data.msg)
          }
        })
        console.log(v.name);
      })
      location.reload();
      ElMessage.success("删除成功")
    }
    return { shopData, handleSelectionChange, Totalprice, delbt, dalshop }
  }
}
</script>

<style>
.Totalprice {
  float: right;
  font-size: 30px;
}
</style>