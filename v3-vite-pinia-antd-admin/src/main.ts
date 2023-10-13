import { createApp } from "vue";
import "@/style.scss";
import "@/style/common.scss";
import App from "@/App.vue";
import { setupStore } from "@/store";
import { setupRouter } from "@/router";
import * as icons from "@ant-design/icons-vue";
import { useCommon } from "@/hooks";

const { getDictArray } = useCommon();

async function setupApp() {
  const app = createApp(App);
  /** Pinia */
  setupStore(app);
  /** Vue-Router@4 */
  await setupRouter(app);
  /** 全局注册ant design icon */
  const iconsPlugin = {
    install(app: any) {
      for (const [key, value] of Object.entries(icons)) {
        app.component(key, value);
      }
    },
  };

  app.use(iconsPlugin);

  app.mount("#app");
}

setupApp();
getDictArray();
