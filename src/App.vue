<template>
  <el-backtop />
  <el-container>
    <el-header height="230px">
      <el-image style="width: 100px; height: 100px"
                src="https://s1.ax1x.com/2022/03/20/qVzwJH.jpg"
                fit="contain" />
      <span id="login_t">创世书城</span>
      <div id="you">
        <el-space :size="5"
                  :spacer="spacer">
          <el-badge :value="1"
                    class="item">
            <el-button type="text"
                       :icon="ShoppingCart"
                       @click="shop"> 购物车</el-button>
          </el-badge>
          <el-button type="text"
                     :icon="Help"> 帮助中心</el-button>
          <el-button type="text"
                     @click="userinfo"
                     :icon="User"> 我的账户</el-button>
          <el-button type="text"
                     @click="register"> 新用户注册</el-button>
        </el-space>
      </div>
      <div id="daohang">
        <el-menu class="el-menu-demo"
                 mode="horizontal"
                 style="width:100%"
                 @select="headerSelect">
          <el-menu-item index="1">文学</el-menu-item>
          <el-menu-item index="2">生活</el-menu-item>
          <el-menu-item index="3">计算机</el-menu-item>
          <el-menu-item index="4">外语</el-menu-item>
          <el-menu-item index="5">经营</el-menu-item>
          <el-menu-item index="6">励志</el-menu-item>
          <el-menu-item index="7">社科</el-menu-item>
          <el-menu-item index="8">文学</el-menu-item>
          <el-menu-item index="9">少儿</el-menu-item>
          <el-menu-item index="10">艺术</el-menu-item>
          <el-menu-item index="11">原版</el-menu-item>
          <el-menu-item index="12">科技</el-menu-item>
          <el-menu-item index="13">考试</el-menu-item>
          <el-menu-item index="14">生活百科</el-menu-item>
          <el-menu-item index="15">全部商品类目</el-menu-item>
        </el-menu>
      </div>
      <br>
      <div id="shousuo">
        <el-input v-model="Tsketch"
                  placeholder="请输入"
                  class="input-with-select">
          <template #append>
            <el-button :icon="Search"
                       @click="sketch" />
          </template>
        </el-input>
      </div>

    </el-header>
    <el-main style="">
      <router-view></router-view>
      <!-- <inedxMain /> -->
    </el-main>
    <el-footer>
      <el-space wrap
                :size="50">
        <div style="display: inline-block;">
          <el-image style="width: 100px; height: 100px"
                    src="https://s1.ax1x.com/2022/03/20/qVzwJH.jpg"
                    fit="contain" />
        </div>
        <div style="display: inline-block;">
          CONTACT US
          <el-divider />
          COPYRLGHT 2015©BOOKStop ALLRights RESERVED
        </div>
      </el-space>
    </el-footer>
  </el-container>
</template>

<script>
import { ShoppingCart, Search, Help, User } from '@element-plus/icons-vue'
import { h, ref } from 'vue'
import { ElDivider } from 'element-plus'
export default {
  //allbook
  name: 'App',
  components: {},
  setup () {

    const Tsketch = ref("")
    const spacer = h(ElDivider, { direction: 'vertical' })
    const userinfo = () => {
      console.log(window.sessionStorage.getItem('token'));
      if (window.sessionStorage.getItem('token') == null) {
        window.location.href = "#/login";
      } else {
        window.location.href = "#/userinfo/userData";
      }

    }
    const register = () => {
      window.location.href = "#/register";
    }

    const tag = ref("全部")
    const headerSelect = (index) => {
      console.log(index);
      switch (index) {
        case "1": tag.value = "文学"; break;
        case "2": tag.value = "生活"; break;
        case "3": tag.value = "计算机"; break;
        case "4": tag.value = "外语"; break;
        case "5": tag.value = "经营"; break;
        case "6": tag.value = "励志"; break;
        case "7": tag.value = "社科"; break;
        case "8": tag.value = "文学"; break;
        case "9": tag.value = "少儿"; break;
        case "10": tag.value = "艺术"; break;
        case "11": tag.value = "原版"; break;
        case "12": tag.value = "科技"; break;
        case "13": tag.value = "考试"; break;
        case "14": tag.value = "生活百科"; break;
        case "15": tag.value = "全部"; break;
      }

      window.location.href = "#/allbook/" + tag.value + "/all"
      location.reload();
    }
    return {
      ShoppingCart, spacer, Search, userinfo, register, Help, User, headerSelect, Tsketch,
      shop: () => {
        window.location.href = "#/bookshop"

      }
      , sketch: () => {
        console.log(Tsketch.value);
        window.location.href = "#/allbook/" + tag.value + "/" + Tsketch.value
        location.reload();
      }
    }
  }
}
</script>

<style>
#you {
  float: right;
}
#daohang {
  display: flex;
  justify-content: center;
}
#shousuo {
  float: right;
  width: 20%;
}
#login_t {
  font-size: 30px;
  color: brown;
}
</style>
