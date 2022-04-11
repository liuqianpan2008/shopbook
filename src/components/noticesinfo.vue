<template>
  <el-card class="noticesinfo">
    <template #header>
      <div class="card-header">
        <h1>{{row.title}}</h1>
      </div>
    </template>
    <div v-html="row.content" />
  </el-card>

</template>

<script>
import { ref } from '@vue/reactivity'
const row = ref({
  title: "",
  content: ""
})
export default {
  row,
  setup () {
    var MarkdownIt = require('markdown-it'),
      md = new MarkdownIt();
    row.value.content = md.render(row.value.content);
    return { row }
  }
}
</script>

<style>
.noticesinfo {
  min-height: 1000px;
}
</style>