<template>
  <el-breadcrumb :separator-icon="ArrowRight">
    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>用户信息</el-breadcrumb-item>
  </el-breadcrumb>
  <br>

  <div style="min-height: 500px;">
    <el-container>
      <el-aside style="min-height: 500px;">
        <el-menu style="min-height: 500px;"
                 default-active="2"
                 class="el-menu-vertical-demo"
                 @select="headerSelect">
          <el-menu-item index="0">
            <span>个人信息</span>
          </el-menu-item>
          <el-menu-item index="1">
            <span>安全中心</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>

</template>

<script>
import { ArrowRight } from '@element-plus/icons-vue'
export default {
  setup () {
    const headerSelect = (index) => {
      if (index == 0) {
        window.location.href = "#/userinfo/userData";
      }
    }
    return { headerSelect, ArrowRight }
  }
}
</script>

<style>
</style>