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
    <div>这里是公告内容 <br></div>
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
export default {
  components: { Bell },
  setup () {
    return {

      // bell
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