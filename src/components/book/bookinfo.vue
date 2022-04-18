<template>
  <div v-if="bookData==null">
    <el-result icon="error"
               title="发生了亿点点错误"
               sub-title="请联系管理员" />
  </div>
  <div v-else>
    <el-card class="bookzs">
      <div class="bookzs-img">
        <el-image style="  width: 200px;height: 200px;"
                  :src="bookData.img" />
      </div>
      <div>
        <span class="book-t">{{bookData.name}}</span>
        <div class="book-js">{{bookData.sketch}}</div>

        <div>
          <el-space spacer="|">
            <span class="book-jg">￥{{bookData.price}}</span>
            <span class="book-kc">库存{{bookData.stock}}</span>
          </el-space>
        </div>
        <el-input-number v-model="num"
                         :min="1"
                         :max="bookData.stock"
                         @change="handleChange" />
        <br />
        <br />
        <el-button @click="paybook">下单购买</el-button>
        <el-button @click="addshop">加入购物车</el-button>
      </div>

    </el-card>
    <el-divider />
    <el-card style="">
      <template #header>详细展示</template>
      <div v-if="bookData.info==''">
        <el-result icon="info"
                   title="这本图书好像没有介绍哦"
                   sub-title="请联系管理员" />
      </div>
      <div v-else
           style="min-height: 1000px;"
           v-html="bookData.info" />

    </el-card>

    <el-divider />
    <el-card>
      评论（施工中going...）
    </el-card>
  </div>

</template>

<script>
import { ref } from '@vue/reactivity';
import axios from 'axios';
import { ElMessage } from 'element-plus'
const bookData = ref()
export default {
  bookData,
  setup () {
    console.log(bookData.value);
    var MarkdownIt = require('markdown-it'),
      md = new MarkdownIt();
    bookData.value.info = md.render(bookData.value.info)
    const num = ref(0)
    const addshop = () => {
      if (window.sessionStorage.getItem("token") == null) {
        ElMessage.error("登录才能添加购物车！")
        window.location.href = "#/login"
        return
      }
      axios({
        url: "http://127.0.0.1:8888" + "/users/addshop",
        method: "POST",
        headers: {
          satoken: window.sessionStorage.getItem("token"),
        },
        data: {
          name: bookData.value.name,
          unitprice: bookData.value.price,
          quantity: num.value
        }
      }).then((e) => {
        console.log(e.data.date);
        if (e.data.flag == false) {
          window.sessionStorage.removeItem("token");
          window.location.href = "#/login"
        }
        if (e.data.date) {
          ElMessage({
            message: e.data.msg,
            type: 'success',
          })
        } else {
          ElMessage.error(e.data.msg)
        }
      });
    }
    const paybook = () => {

    }
    return { bookData, addshop, num, paybook }
  }
}
</script>

<style>
.bookzs {
  height: 300px;
  /* display: flex; */
}
.bookzs-img {
  padding: 20px;
  float: left;
}
.book-t {
  font: 2.5em sans-serif;
  padding-bottom: 15px;
}
.book-js {
  height: 80px;
}
.book-jg {
  color: red;
  font: 1.5em sans-serif;
  font-weight: 400;
  padding-bottom: 15px;
}
.book-kc {
  color: #73767a;
  font: 0.8em sans-serif;
  font-weight: 400;
}
</style>