import type { App } from "vue";
import { constantRoutes } from "./routes";
import { createRouter, createWebHashHistory } from "vue-router";
import { createRouterGuard } from "./guard";

export const router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes,
});

/** 安装vue路由 */
export async function setupRouter(app: App) {
  app.use(router);
  createRouterGuard(router);
  await router.isReady();
}

/** 路由名称 */
export const routeName = (key: AuthRoute.AllRouteKey) => key;

export * from "./routes";
