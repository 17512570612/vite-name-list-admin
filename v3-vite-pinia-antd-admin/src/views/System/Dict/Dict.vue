<template>
    <div class="search-box">
        <a-form labelAlign="right" :labelCol="{ span: 8 }">
            <div class="flex jc-sb">
                <a-button class="mb-10" @click="handleCreate">
                    <template #icon>
                        <PlusOutlined />
                    </template>
                    新增
                </a-button>
                <div class="flex">
                    <a-form-item label="代码">
                        <a-input placeholder="请输入代码" v-model:value="queryForm.code" allowClear />
                    </a-form-item>
                    <a-form-item label="值">
                        <a-input placeholder="请输入值" v-model:value="queryForm.value" allowClear />
                    </a-form-item>
                    <a-button class="ml-10" type="primary" @click="query(API.DICT_LIST, queryForm)">
                        <template #icon>
                            <SearchOutlined />
                        </template>
                        查询
                    </a-button>
                    <a-button class="ml-10">
                        <template #icon>
                            <ReloadOutlined />
                        </template>
                        重置
                    </a-button>
                </div>
            </div>
        </a-form>
    </div>
    <a-spin :spinning="loading">
        <a-table bordered :columns="columns" :data-source="list">
            <template #bodyCell="{ column, record }">
                <template v-if="column.dataIndex === 'action'">
                    <div class="flex">
                        <a-button class="btn-link mr-10" type="link" @click="handleEdit(record)">编辑</a-button>
                        <a-button class="btn-link" type="link" @click="handleEdit(record)">删除</a-button>
                    </div>
                </template>
            </template>
        </a-table>
    </a-spin>
    <DictModal :isOpen="isOpen" :isEdit="isEdit" :dictItems="dictItems" :formData="formData" @update:modal-status="updateModalStatus" @update:dict-item="getDictItems" />
</template>

<script setup lang="ts">
import { API, get } from "@/service"
import { useCrud } from "@/hooks"
import DictModal from "./components/DictModal.vue"

const columns: any = [
    {
        title: '代码',
        dataIndex: 'code',
        width: 300
    },
    {
        title: '值',
        dataIndex: 'value',
        width: 300
    },
    {
        title: '备注',
        dataIndex: 'description',
    },
    {
        title: '操作',
        dataIndex: 'action',
        width: 120
    }
]

const { loading, list, queryForm, query } = useCrud();

const isOpen = ref(false);
const isEdit = ref(false);
const formData = ref();
const dictItems = ref();

const handleCreate = () => {
    isOpen.value = true;
    formData.value = {};
}

const handleEdit = (record: DictState) => {
    isOpen.value = true;
    isEdit.value = true;
    formData.value = record;
    getDictItems(record);
}

const updateModalStatus = async (newVal: boolean) => {
    isOpen.value = newVal;
    isEdit.value = newVal;
    await query(API.DICT_LIST)
}

const getDictItems = async (record: any) => {
    const res: Response = await get(API.DICT_ITEM, record)
    dictItems.value = res.result;
}

onMounted(async () => {
    await query(API.DICT_LIST)
})
</script>

<style scoped>
.btn-link {
    padding: 0;
}
</style>