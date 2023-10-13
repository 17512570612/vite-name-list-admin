import { Router } from "vue-router";
import { createDynamicRouteGuard } from "./dynamic";

export function createRouterGuard(router: Router) {
  router.beforeEach(async (to, from, next) => {
    await createDynamicRouteGuard(to, from, next);
  });
}
