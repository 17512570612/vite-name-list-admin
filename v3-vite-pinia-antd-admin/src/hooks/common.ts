import { API, get, post } from "@/service";
import { useAppStore } from "@/store";
import { useCrud } from "@/hooks";
import { message } from "ant-design-vue";

const { loading } = useCrud();

export function useCommon() {
  async function getDictArray() {
    const res: any = await get(API.DICT_ARRAY, {});
    useAppStore().setDict(res.result);
  }
  type Key = string | number;
  const selectedRowKeys = ref<Key[]>([]);

  function onSelectChange(keys: Key[]) {
    selectedRowKeys.value = keys;
  }

  async function approved(url: string, keys: Key[]) {
    loading.value = true;
    try {
      const res: Response = await post(url, { ids: keys });
      if (res.code === 200) {
        message.success("审批成功");
        selectedRowKeys.value = [];
      }
    } catch (error) {
      console.log(error);
    } finally {
      loading.value = false;
    }
  }

  async function rejected(url: string, keys: Key[]) {
    loading.value = true;
    try {
      const res: Response = await post(url, { ids: keys });
      if (res.code === 200) {
        message.success("驳回成功");
        selectedRowKeys.value = [];
      }
    } catch (error) {
      console.log(error);
    } finally {
      loading.value = false;
    }
  }

  return {
    selectedRowKeys,
    approved,
    rejected,
    getDictArray,
    onSelectChange,
  };
}
