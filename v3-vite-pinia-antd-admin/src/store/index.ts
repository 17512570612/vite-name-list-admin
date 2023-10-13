import type { App } from "vue";
import { createPinia } from "pinia";

export function setupStore(app: App) {
  const store = createPinia();
  app.use(store);
}

export * from "./modules/app";
export * from "./modules/user";
export * from "./modules/routes";
