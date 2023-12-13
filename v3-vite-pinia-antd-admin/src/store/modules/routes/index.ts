import { defineStore } from "pinia";
import { get } from "@/service";
import type { RouteRecordRaw } from "vue-router";
import Layout from "@/layout/index.vue";
import { API } from "@/service";

const modules = import.meta.glob("../../../views/**/**.vue");

interface RouteState {
  menuTree: RouteRecordRaw[];
}

export const useRouteStore = defineStore("route-store", {
  state: (): RouteState => ({
    menuTree: [],
  }),
  actions: {
    /** 初始化动态路由 */
    initDynamicRoute(): Promise<any> {
      return new Promise((resolve) => {
        get(API.MENU_LIST).then((res: any) => {
          this.menuTree = res.result;
          resolve(filterAsyncRouter(res.result));
        });
      });
    },
  },
});

const filterAsyncRouter = (asyncRouterMap: any) => {
  return asyncRouterMap.filter((route: any) => {
    if (route.status === 0) {
      return false;
    }
    route.meta = {
      title: route.name,
      icon: route.icon,
      parent: route.parent,
      sort: route.sort,
    };
    if (route.component) {
      if (route.component === "Layout") {
        route.component = Layout;
      } else {
        route.component = modules[`../../../views${route.component}.vue`];
      }
    }
    if (route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children);
    }
    return true;
  });
};
