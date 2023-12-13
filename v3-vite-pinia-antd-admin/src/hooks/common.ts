import { API, get, post } from "@/service";
import { useAppStore } from "@/store";
import { useCrud } from "@/hooks";
import { message } from "ant-design-vue";
import type { TableProps } from "ant-design-vue";

const { loading } = useCrud();

export function useCommon() {
  // #数据字典
  async function getDictArray() {
    const res: any = await get(API.DICT_ARRAY, {});
    useAppStore().setDict(res.result);
  }

  type Key = string | number;
  const selectedRowKeys = ref<Key[]>([]);

  function onSelectChange(keys: Key[]) {
    selectedRowKeys.value = keys;
  }

  const rowSelection: TableProps["rowSelection"] = {
    onChange: (keys: Key[]) => {
      selectedRowKeys.value = keys;
      console.log(selectedRowKeys.value);
    },
    getCheckboxProps: (record: any) => ({
      disabled: record.delFlag !== "1",
    }),
  };

  // #审批
  async function approved(url: string, keys: Key[]) {
    loading.value = true;
    try {
      const res: response = await post(url, { ids: keys });
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
  // #驳回
  async function rejected(url: string, keys: Key[]) {
    loading.value = true;
    try {
      const res: response = await post(url, { ids: keys });
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
  // #获取国家地区code
  const countryAllCode = ref();
  async function getCountryCode() {
    const res: response = await get(API.COUNTRY_CODE_LIST);
    countryAllCode.value = res.result;
  }

  return {
    countryAllCode,
    selectedRowKeys,
    rowSelection,
    approved,
    rejected,
    getDictArray,
    onSelectChange,
    getCountryCode,
  };
}
