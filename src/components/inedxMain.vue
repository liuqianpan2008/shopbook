<template>
  <div class="block">
    <el-carousel :interval="4000"
                 height="300px">
      <el-carousel-item>
        <h3 class="small">1</h3>
      </el-carousel-item>
      <el-carousel-item>
        <h3 class="small">2</h3>
      </el-carousel-item>
    </el-carousel>
  </div>
  <br>
  <el-card class="zhukapian">
    <template #header>
      <div>
        <span>
          <el-icon>
            <bell />
          </el-icon>
          公告
        </span>
      </div>
    </template>
    <el-table :data="notice"
              style="width: 100%"
              @cell-click="notuicesClick">
      <el-table-column prop="title"
                       label="标题" />
      <el-table-column prop="time"
                       label="时间" />
    </el-table>
  </el-card>
  <br>
  <el-card class="zhukapian">
    <template #header>
      <div>
        <span>本周热卖</span>
      </div>
    </template>
    <el-carousel :interval="4000"
                 type="card"
                 height="300px">
      <el-carousel-item v-for="item in 6"
                        :key="item">
        <h3>{{ item }}</h3>
      </el-carousel-item>
    </el-carousel>
  </el-card>
</template>

<script>
import { Bell } from '@element-plus/icons-vue'
import axios from 'axios';
import { ref } from '@vue/reactivity';
import { useRouter } from 'vue-router'
import noticesinfoVue from './noticesinfo.vue';
export default {
  components: { Bell },
  setup () {
    // var MarkdownIt = require('markdown-it'),
    // md = new MarkdownIt();
    const Router = useRouter() //跳转
    const notice = ref([{
      title: "无",
      time: "无"
    }])
    axios.get("http://127.0.0.1:8888" + "/notices").then(e => {
      // notice.value = md.render(e.data.data);
      console.log(e.data.date);
      notice.value = e.data.date

    })
    function notuicesClick (row) {
      console.log(row);
      noticesinfoVue.row.value = row;
      Router.push({
        path: 'noticesinfo',
      })

    }
    return {
      notice, notuicesClick
    }
  }
}
</script>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
.zhukapian {
  width: inherit;
  min-height: 300px;
}
</style>