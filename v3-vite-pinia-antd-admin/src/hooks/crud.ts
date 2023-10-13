import { get, post } from "@/service";
import { message } from "ant-design-vue";

/** 公共函数<增删改查> */
export function useCrud() {
  const loading = ref(false);
  const list = ref();
  const queryForm = ref<any>({ pageNo: 1, pageSize: 10 });

  async function query(url: string, params = { pageNo: 1, pageSize: 10 }) {
    loading.value = true;
    try {
      const res = await get<Response>(url, params);
      list.value = res.result.records ? res.result.records : res.result;
    } catch (error) {
      console.log(error);
    } finally {
      loading.value = false;
    }
  }

  async function create(url: string, params: any) {
    loading.value = true;
    try {
      const res = await post<Response>(url, params);
      if (res.code === 200) {
        message.open({ type: "success", content: res.message });
      }
    } catch (error) {
      console.log(error);
    } finally {
      loading.value = false;
    }
  }

  async function update(url: string, params: any) {
    loading.value = true;
    try {
      const res = await post<Response>(url, params);
      if (res.code === 200) {
        message.open({ type: "success", content: res.message });
      }
    } catch (error) {
      console.log(error);
    } finally {
      loading.value = false;
    }
  }

  async function remove(url: string, id: string | string[]) {
    loading.value = true;
    try {
      const res = await get<Response>(url, { id });
      if (res.code === 200) {
        message.open({ type: "success", content: res.message });
      }
    } catch (error) {
      console.log(error);
    } finally {
      loading.value = false;
    }
  }

  return {
    loading,
    list,
    queryForm,
    query,
    create,
    update,
    remove,
  };
}
