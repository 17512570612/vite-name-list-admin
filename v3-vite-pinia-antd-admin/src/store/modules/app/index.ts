import { defineStore } from "pinia";

interface AppState {
  collapsed: boolean;
  dict: any;
}

export const useAppStore = defineStore("app-store", {
  state: (): AppState => ({
    collapsed: false,
    dict: [],
  }),
  actions: {
    toggleCollapsed() {
      this.collapsed = !this.collapsed;
    },

    setDict(dict: any) {
      this.dict = dict;
    },
  },
});
