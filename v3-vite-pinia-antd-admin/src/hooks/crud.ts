import { get, post } from "@/service";
import { message } from "ant-design-vue";

/** 公共函数<增删改查> */
export function useCrud() {
  const list = ref();
  const loading = ref(false);
  const queryForm = ref<any>({ pageNo: 1, pageSize: 10 });
  const pagination = ref({ total: 0, current: 1, pageSize: 10 });

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

  async function query(url: string, params = { pageNo: 1, pageSize: 10 }) {
    loading.value = true;
    try {
      const res = await get<Response>(url, params);
      if (res.result.records) {
        list.value = res.result.records;
        pagination.value.current = res.result.pageNo;
        pagination.value.total = res.result.total;
        pagination.value.pageSize = res.result.pageSize;
      } else {
        list.value = res.result;
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
    pagination,
    query,
    create,
    update,
    remove,
  };
}
