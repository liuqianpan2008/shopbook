import { createRouter, createWebHashHistory } from "vue-router";
import inedxMain from "./components/inedxMain.vue";
import userinfo from "./components/userInfo.vue";
import register from "./components/register.vue";
const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/",
      name: "home",
      component: inedxMain,
    },
    {
      path: "/userinfo",
      name: "userinfo",
      component: userinfo,
    },
    {
      path: "/register",
      name: "register",
      component: register,
    },
  ],
});
export default router;
