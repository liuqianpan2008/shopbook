import axios from "axios";
import { createRouter, createWebHashHistory } from "vue-router";
import inedxMain from "./components/inedxMain.vue";
import login from "./components/login.vue";
import register from "./components/register.vue";
import userInfo from "./components/userinfo/userinfo.vue";
import userData from "./components/userinfo/userData.vue";
const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/",
      name: "home",
      component: inedxMain,
    },
    {
      path: "/login",
      name: "login",
      component: login,
      beforeEnter(to, form, next) {
        if (window.sessionStorage.getItem("token") != null) {
          next("/");
        }
        next();
      },
    },
    {
      path: "/register",
      name: "register",
      component: register,
      beforeEnter(to, form, next) {
        if (window.sessionStorage.getItem("token") != null) {
          next("/");
        }
        next();
      },
    },
    {
      path: "/userinfo",
      name: "userinfo",
      component: userInfo,
      beforeEnter(to, form, next) {
        if (window.sessionStorage.getItem("token") == null) {
          next("/");
        }
        next();
      },
      children: [
        {
          path: "userData",
          name: "userData",
          component: userData,
          beforeEnter(to, form, next) {
            if (window.sessionStorage.getItem("token") == null) {
              next("/");
            }
            axios({
              url: "http://127.0.0.1:8888" + "/users/userInfo",
              method: "GET",
              headers: {
                satoken: window.sessionStorage.getItem("token"),
              },
            }).then((e) => {
              console.log(e.data.date);
              if (e.data.flag == false) {
                window.sessionStorage.removeItem("token");
                next("/login");
              }
              userData.userdata.value = e.data.date;
              next();
            });
          },
        },
      ],
    },
  ],
});
export default router;
