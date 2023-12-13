import { get, post } from "@/service";
import { message } from "ant-design-vue";

type Key = string | number;

/** 公共函数<增删改查> */
export function useCrud() {
  const list = ref();
  const loading = ref(false);
  const queryForm = ref<any>({ pageNo: 1, pageSize: 10 });
  const pagination = ref({ total: 0, current: 1, pageSize: 10 });
  const url = ref({
    query: "",
    create: "",
    update: "",
    remove: "",
  });

  async function create<T>(params: T) {
    loading.value = true;
    try {
      const res = await post<response>(url.value.create, params);
      if (res.code === 200) {
        message.open({ type: "success", content: res.message });
        query();
      }
    } catch (error) {
      console.log(error);
    } finally {
      loading.value = false;
    }
  }

  async function remove(ids: Key | Key[]) {
    loading.value = true;
    try {
      const res = await get<response>(url.value.remove, {
        ids: ids.toString(),
      });
      if (res.code === 200) {
        message.open({ type: "success", content: res.message });
        query();
      }
    } catch (error) {
      console.log(error);
    } finally {
      loading.value = false;
    }
  }

  async function update<T>(params: T) {
    loading.value = true;
    try {
      const res = await post<response>(url.value.update, params);
      if (res.code === 200) {
        message.open({ type: "success", content: res.message });
      }
    } catch (error) {
      console.log(error);
    } finally {
      loading.value = false;
    }
  }

  async function query(params = { pageNo: 1, pageSize: 10 }) {
    loading.value = true;
    try {
      const res = await get<response>(url.value.query, params);
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
    url,
    list,
    loading,
    queryForm,
    pagination,
    query,
    create,
    update,
    remove,
  };
}
