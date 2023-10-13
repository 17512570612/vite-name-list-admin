import { defineStore } from "pinia";

interface UserState {
  token: string;
}

export const useUserStore = defineStore("user-store", {
  state: (): UserState => ({
    token: "",
  }),
  actions: {
    setToken(token: string) {
      this.token = token;
      localStorage.setItem("token", token);
    },
    getToken() {
      return localStorage.getItem("token");
    },
    removeToken() {
      this.token = "";
      localStorage.removeItem("token");
    },
  },
});
