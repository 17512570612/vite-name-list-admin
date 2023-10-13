import { API, get } from "@/service";
import { useAppStore } from "@/store";

export function useCommon() {
  async function getDictArray() {
    const res: any = await get(API.DICT_ARRAY, {});
    useAppStore().setDict(res.result);
  }

  return {
    getDictArray,
  };
}
