import axios from "axios";
import { createRouter, createWebHashHistory } from "vue-router";
import inedxMain from "./components/inedxMain.vue";
import login from "./components/login.vue";
import register from "./components/register.vue";
import userInfo from "./components/userinfo/userinfo.vue";
import userData from "./components/userinfo/userData.vue";
import passworldinfo from "./components/userinfo/passworldinfo.vue";
import mailcod from "./components/mailcod.vue";
import noticesinfo from "./components/noticesinfo.vue";
import allbook from "./components/book/allbook.vue";
import bookinfo from "./components/book/bookinfo.vue";
import bookshop from "./components/shopbook.vue";
import order from "./components/order.vue";
import myorder from "./components/userinfo/myorder.vue";
const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/",
      name: "home",
      component: inedxMain,
    },
    {
      path: "/order/:bookid",
      name: "order",
      component: order,
    },
    {
      path: "/bookinfo",
      name: "bookinfo",
      component: bookinfo,
    },
    {
      path: "/bookshop",
      name: "bookshop",
      component: bookshop,
      beforeEnter(to, form, next) {
        if (window.sessionStorage.getItem("token") == null) {
          next("/");
        }
        axios({
          url: "http://127.0.0.1:8888" + "/users/shopInfo",
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
          bookshop.shopData.value = e.data.date;
          next();
        });
      },
    },
    {
      path: "/allbook/:tag/:sketch",
      name: "allbook",
      component: allbook,
    },
    {
      path: "/noticesinfo",
      name: "noticesinfo",
      component: noticesinfo,
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
        {
          path: "passworldinfo",
          name: "passworldinfo",
          component: passworldinfo,
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
              passworldinfo.userdata.value = e.data.date;
              next();
            });
          },
        },
        {
          path: "myorder",
          name: "myorder",
          component: myorder,
          beforeEnter(to, form, next) {
            if (window.sessionStorage.getItem("token") == null) {
              next("/");
            }
            axios({
              url: "http://127.0.0.1:8888" + "/users/orderuser",
              method: "POST",
              headers: {
                satoken: window.sessionStorage.getItem("token"),
              },
            }).then((e) => {
              console.log(e.data.date);
              if (e.data.flag == false) {
                window.sessionStorage.removeItem("token");
                next("/login");
              }
              myorder.datas.value = e.data.date;
              next();
            });
          },
        },
      ],
    },
    {
      path: "/mailcod/:userName/:cod",
      name: "mailcod",
      component: mailcod,
    },
  ],
});
export default router;
