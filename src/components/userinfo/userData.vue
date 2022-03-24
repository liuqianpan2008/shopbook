<template>
  <div class="juzhong">
    <el-card class="box-card">
      <el-descriptions class="margin-top"
                       title="个人资料"
                       :column="1"
                       size="large"
                       border>
        <template #extra>
          <el-tag size="small">id{{userdata.id}}</el-tag>
        </template>

        <el-descriptions-item min-width="250px"
                              align="center">
          <template #label>
            <div class="cell-item">
              用户名
            </div>
          </template>
          {{userdata.userName}}
        </el-descriptions-item>
        <el-descriptions-item min-width="250px"
                              align="center">
          <template #label>
            <div class="cell-item">
              性别
            </div>
          </template>
          {{userdata.sex}}
        </el-descriptions-item>

        <el-descriptions-item min-width="250px"
                              align="center">
          <template #label>
            <div class="cell-item">
              电话号码
            </div>
          </template>
          {{userdata.phone}}
        </el-descriptions-item>

        <el-descriptions-item min-width="250px"
                              align="center">
          <template #label>
            <div class="cell-item">
              个性签名
            </div>
          </template>
          {{userdata.introduce}}
        </el-descriptions-item>

      </el-descriptions>
    </el-card>
  </div>

</template>

<script>
import { ref } from '@vue/reactivity'
const userdata = ref()
export default {
  userdata,
  setup () {
    return { userdata }
  }
}
</script>

<style>
.juzhong {
  display: flex;
  justify-self: start;
}
</style>