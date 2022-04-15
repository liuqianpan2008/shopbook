<template>
  <el-breadcrumb :separator-icon="ArrowRight">
    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>{{tag==""?"全部":tag}}</el-breadcrumb-item>
  </el-breadcrumb>
  <br>
  <el-result v-if="books.records.length==0"
             icon="error"
             title="未找到书籍"
             sub-title="我会下次一定会努力的" />
  <el-card class="allbook"
           v-if="books.records.length!=0">
    <div>
      <el-card v-for="(v,i) in books.records"
               :key="i"
               class="book">
        <template #header>
          <div class="bookh">
            <!-- str = str.replace('abc', ''); -->
            <b>{{v.name}}</b>
            <el-tag> {{v.tag}}</el-tag>
          </div>
        </template>
        <div class="bookimg">
          <el-image style="width: 200px; height: 200px"
                    :src="v.img"
                    fit="contain" />
        </div>
        <br />
        <div class="bookinfo">
          <span style="padding-left:10px ;">{{v.sketch}}</span><br>
          <div class="bookbt">
            <el-button type="success"
                       @click="tolook(v)">去看看</el-button>
            <el-button type="primary"
                       @click="addshop(v)">加入购物车</el-button>
          </div>
        </div>
      </el-card>
    </div>
    <div class="page">
      <!-- books.total -->
      <el-pagination background
                     layout="prev, pager, next"
                     :hide-on-single-page="books.pages==1"
                     :page-count="books.pages"
                     @current-change="cpage" />
    </div>

  </el-card>

</template>

<script>
import { ArrowRight } from '@element-plus/icons-vue'
import axios from 'axios'
import { ref } from '@vue/reactivity'
import { useRoute } from 'vue-router'
import bookinfo from './bookinfo.vue'
import { ElMessage } from 'element-plus'
export default {

  setup () {
    var tag = useRoute().params.tag
    var sketch = useRoute().params.sketch
    const value = ref(false)
    if (tag == "全部") {
      tag = ""
    }
    if (sketch == "all") {
      sketch = ""
    }
    const books = ref({
      records: [{}]
    })
    axios.post("http://127.0.0.1:8888" + "/book/allbook", {
      current: 1,
      size: 3,
      tag: tag,
      search: sketch
    }).then(res => {
      books.value = res.data.date;
      if (books.value.pages == 1) {
        value.value = true
      }
    })
    const cpage = (i) => {
      axios.post("http://127.0.0.1:8888" + "/book/allbook", {
        current: i,
        size: 3,
        tag: tag
      }).then(res => {
        books.value = res.data.date;
      })
    }
    const tolook = (Item) => {
      bookinfo.bookData.value = Item
      window.location.href = "#/bookinfo"
    }
    const addshop = (Item) => {
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
          name: Item.name,
          unitprice: Item.price,
          quantity: "1"
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
    return { books, cpage, tag, ArrowRight, sketch, tolook, addshop }
  }
}
</script>

<style>
.allbook {
  min-height: 1000px;
  position: relative;
}
.page {
  width: 100%;
  display: flex;
  justify-content: center;
  position: absolute;
  bottom: 0px;
}
.el-card__body {
  overflow: hidden;
}
.sketch {
  height: 130px;
}
.bookimg {
  float: left;
}
.bookinfo {
  float: left;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 80%;
  height: 180px;
}
.bookh {
  display: flex;
  justify-content: space-between;
}
.bookbt {
  width: 107%;
  display: flex;
  justify-content: flex-end;
}
</style>