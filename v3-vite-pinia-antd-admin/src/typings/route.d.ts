declare namespace AuthRoute {
  /** 根路由路径 */
  type RootRoutePath = "/";

  /** 捕获无效路由的路由路径 */
  type NotFoundRoutePath = "/:pathMatch(.*)*";

  type RootRouteKey = PageRoute.RootRouteKey;

  type RouteKey = PageRoute.RouteKey;

  type AllRouteKey = RouteKey | RootRouteKey | NotFoundRouteKey;
}
