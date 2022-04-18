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
      <el-table :data="payinfo"
                style="width: 100%">
        <el-table-column prop="name"
                         label="书籍名称" />
        <el-table-column prop="unitprice"
                         label="单价" />
        <el-table-column prop="quantity"
                         label="购买数量" />
        <el-table-column prop="totalprice"
                         label="操作">
          <template #default="scope">
            <el-button size="small"
                       type="primary"
                       @click="handleDelete(scope.row)">去看看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <br>
      <div style="float:right">
        价格:{{zj}}
      </div>
    </el-card>
    <el-divider />
    <el-card>
      <template #header>
        <span>快递填写</span>
      </template>
      <el-form :model="formData"
               label-position="top"
               :rules="rules"
               ref="formref">
        <el-form-item label="姓名"
                      prop="name">
          <el-input v-model="formData.name" />
        </el-form-item>
        <el-form-item label="手机号"
                      prop="phone">
          <el-input v-model="formData.phone" />
        </el-form-item>
        <el-form-item label="地址"
                      prop="addr">
          <el-input v-model="formData.addr" />
        </el-form-item>
      </el-form>
    </el-card>
    <el-divider />
    <el-button @click="pay">去付款</el-button>
  </el-card>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { useRoute } from 'vue-router'
import bookinfo from './book/bookinfo.vue'
import axios from 'axios'
export default {
  setup () {
    const bookid = useRoute().params.bookid
    const formref = ref()
    const zj = ref(0)
    axios({
      url: "http://127.0.0.1:8888" + "/users/orderinfo",
      method: "POST",
      headers: {
        satoken: window.sessionStorage.getItem("token"),
      },
      data: {
        bookid: bookid
      }
    }).then(e => {
      console.log(e.data);
      const data = e.data.date;
      data.forEach(element => {
        let datas = {
          name: element.book.name,
          unitprice: element.book.price,
          quantity: element.num,
          zhongjia: element.book.price * element.num,
          book: element.book
        }
        formData.value.name = element.name
        formData.value.phone = element.phone
        formData.value.addr = element.addr
        payinfo.value.push(datas)
      });
      payinfo.value.forEach(e => {
        zj.value += e.zhongjia
      })
    })
    const payinfo = ref([])
    const formData = ref({
      name: "",
      phone: "",
      addr: "",
    })
    const handleDelete = (v) => {
      bookinfo.bookData.value = v.book
      window.location.href = "#/bookinfo"
    }
    const pay = () => {
      formref.value.validate((valid) => {
        if (valid) {
          axios({
            url: "http://127.0.0.1:8888" + "/users/orderput",
            method: "POST",
            headers: {
              satoken: window.sessionStorage.getItem("token"),
            },
            data: {
              bookid: bookid,
              name: formData.value.name,
              phone: formData.value.phone,
              addr: formData.value.addr
            }
          }).then(e => {
            console.log(e.data);
          })


          console.log("验证通过");
        }
      })
    }

    const rules = reactive({
      name: [
        { required: true, message: '请填写姓名', trigger: 'blur' },
        { min: 2, max: 4, message: '名字长度填写错误', trigger: 'blur' },
      ],
      phone: [
        { required: true, message: '请填写电话', trigger: 'blur' },
        { min: 6, max: 11, message: '电话长度填写错误', trigger: 'blur' },
      ],
      addr: [
        { required: true, message: '请填写地址', trigger: 'blur' },
        { min: 10, max: 50, message: '地址长度填写错误', trigger: 'blur' },
      ],
    })
    return {
      payinfo, formData, handleDelete, pay, formref,
      rules, zj
    }
  }

}
</script>

<style>
</style>