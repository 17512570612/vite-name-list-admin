import type { NavigationGuardNext, RouteLocationNormalized } from "vue-router";
import { useUserStore, useRouteStore } from "@/store";
import { router } from "@/router";

const whiteList = ["/login"];

export async function createDynamicRouteGuard(
  to: RouteLocationNormalized,
  _from: RouteLocationNormalized,
  next: NavigationGuardNext
) {
  const { getToken } = useUserStore();
  const routeStore = useRouteStore();

  if (getToken()) {
    if (to.path === "/login") {
      next({ path: "/" });
    } else {
      if (!routeStore.menuTree.length) {
        const asyncRoutes = await routeStore.initDynamicRoute();
        asyncRoutes.forEach((route: any) => router.addRoute(route));
        next({ ...to, replace: true });
      } else {
        next();
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next();
    } else {
      next({ path: "/login", replace: true });
    }
  }
}
