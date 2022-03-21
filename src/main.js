import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import routes from "./routes.ts";

createApp(App)
.use(routes)
.use(ElementPlus)
.mount('#app')
