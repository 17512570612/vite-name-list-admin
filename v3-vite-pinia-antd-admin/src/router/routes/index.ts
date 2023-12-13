import { RouteRecordRaw } from "vue-router";
import { HomeOutlined } from "@ant-design/icons-vue";
import Layout from "@/layout/index.vue";

export const constantRoutes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    component: Layout,
    redirect: "/index",
    meta: {
      parent: true,
      title: "首页",
      icon: HomeOutlined,
    },
    children: [
      {
        path: "/index",
        name: "Home",
        component: () => import("@/views/Home/index.vue"),
      },
    ],
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/Login/index.vue"),
    meta: {
      title: "登录",
      hidden: true,
    },
  },
  {
    path: "/Scan",
    name: "Scan",
    component: () => Layout,
    meta: {
      title: "扫描明细",
      hidden: true,
    },
    children: [
      {
        path: "/ScanDetails",
        name: "ScanDetails",
        component: () => import("@/views/ScanDetails/index.vue"),
      },
    ],
  },
  {
    path: "/404",
    name: "404",
    component: () => import("@/views/error/404.vue"),
  },
  // {
  //   path: "/:pathMatch(.*)*",
  //   name: "404",
  //   component: () => import("@/views/error/404.vue"),
  // },
];
