import axios, {
  InternalAxiosRequestConfig,
  AxiosResponse,
  AxiosError,
} from "axios";
import { useUserStore } from "@/store";
import { message } from "ant-design-vue";
import { router } from "@/router";

const axiosInstance = axios.create({
  baseURL: "http://127.0.0.1:9002/",
  timeout: 10000,
});

axiosInstance.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const { getToken } = useUserStore();
    config.headers["Authorization"] = `Bearer ${getToken()}`;
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  (response: AxiosResponse) => {
    return response.data;
  },
  async (error: AxiosError) => {
    if (error.response?.status === 401) {
      const { removeToken } = useUserStore();
      removeToken();
      message.open({
        type: "error",
        content: "token验证失败，请重新登录",
      });
      router.push("/login");
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
