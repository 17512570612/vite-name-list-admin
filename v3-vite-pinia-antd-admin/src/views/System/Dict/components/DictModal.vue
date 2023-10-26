<template>
    <a-modal :width="isEdit ? '60%' : '50%'" :closable="false" v-model:open="isOpen" ok-text="确定" cancel-text="取消" @cancel="handleCancel" @ok="handleOk">
        <a-descriptions size="small" :title="isEdit ? '编辑字典' : '新增字典'" bordered :column="4" :labelStyle="{ width: '100px' }">
            <a-descriptions-item label="代码：" :span="2">
                <a-input :bordered="false" v-model:value="formData.code" placeholder="请输入代码" allowClear />
            </a-descriptions-item>
            <a-descriptions-item label="值：" :span="2">
                <a-input :bordered="false" v-model:value="formData.value" placeholder="请输入值" allowClear />
            </a-descriptions-item>
            <a-descriptions-item label="备注：">
                <a-textarea :bordered="false" v-model:value="formData.description" placeholder="请输入备注" :rows="1" allowClear />
            </a-descriptions-item>
        </a-descriptions>
        <!-- 字典明细 -->
        <div class="edit-show" v-if="isEdit">
            <div class="header flex aic jc-sb mt-20 mb-20">
                <h3 class="title">字典明细</h3>
                <a-button @click="handleAddRow">新增</a-button>
            </div>
            <a-table size="small" bordered :columns="columns" :data-source="dictItems">
                <template #headerCell="{ column }">
                    <template v-if="column.dataIndex === 'code'">
                        代码 <i style="color:red">*</i>
                    </template>
                    <template v-if="column.dataIndex === 'value'">
                        值 <i style="color:red">*</i>
                    </template>
                    <template v-if="column.dataIndex === 'label'">
                        描述 <i style="color:red">*</i>
                    </template>
                </template>
                <template #bodyCell="{ column, record }">
                    <a-input v-if="column.dataIndex === 'code'" v-model:value="record.code" placeholder="请输入代码" allowClear />
                    <a-input v-if="column.dataIndex === 'value'" v-model:value="record.value" placeholder="请输入值" allowClear />
                    <a-input v-if="column.dataIndex === 'label'" v-model:value="record.label" placeholder="请输入备注" allowClear />
                    <a-input-number v-if="column.dataIndex === 'sort'" style="width: 100%;" min="0" v-model:value="record.sort" placeholder="排序" />
                    <div v-if="column.dataIndex === 'action'">
                        <a-popconfirm title="是否确认删除?" ok-text="确定" cancel-text="取消" @confirm="handleRemove(record)">
                            <a-button type="link">删除</a-button>
                        </a-popconfirm>
                    </div>
                </template>
            </a-table>
        </div>
    </a-modal>
</template>

<script setup lang="ts">
import { API } from '@/service'
import { useCrud } from '@/hooks';
import { message } from 'ant-design-vue';
import { generateRandomString } from "@/utils"
import { ColumnGroupType, ColumnType } from 'ant-design-vue/es/table';

const props = defineProps(['isOpen', 'isEdit', 'formData', 'dictItems']);

const emit = defineEmits(["update:modalStatus", "update:dictItem"]);

const isOpen = ref();
const isEdit = ref();
const formData = ref();
const dictItems = ref();

watch(props, (newValue: any) => {
    isOpen.value = newValue.isOpen;
    isEdit.value = newValue.isEdit;
    formData.value = Object.assign({}, newValue.formData);
    dictItems.value = newValue.dictItems;
})

const columns: (ColumnGroupType<any> | ColumnType<any>)[] = [
    { title: '代码', dataIndex: 'code', width: 180 },
    { title: '值', dataIndex: 'value', width: 180 },
    { title: '描述', dataIndex: 'label', width: 180 },
    { title: '排序', dataIndex: 'sort', width: 80 },
    { title: '操作', dataIndex: 'action', align: 'center', width: 80 }
]

const { url, create, remove } = useCrud();

url.value.create = API.DICT_ADD;
url.value.remove = API.DICT_ITEM_DELETE;

const handleOk = async () => {
    // if (isEdit && !validateForm()) {
    //     return
    // }
    formData.value.query = dictItems.value;
    await create(formData.value);
    emit("update:modalStatus", false);
}

const validateForm = () => {
    for (const item of dictItems.value) {
        if (!item.code || !item.value || !item.label) {
            message.open({ type: "error", content: "错误：代码、值、描述为必填项" });
            return false;
        }
    }
    return true;
}

const handleCancel = () => {
    emit("update:modalStatus", false);
}

const handleAddRow = () => {
    // #uniqueId 该字段仅在删除行没有id属性时用于定位索引
    const uniqueId = generateRandomString(32);
    dictItems.value.push({ uniqueId: uniqueId, code: '', value: '', label: '' });
}

const handleRemove = async (record: DictItemState) => {
    if (record.id) {
        // #通过接口删除字典项并刷新列表
        await remove(record.id);
        emit("update:dictItem", formData.value);
    } else {
        const dataIndex = dictItems.value.findIndex((item: DictItemState) => item.uniqueId === record.uniqueId);
        dictItems.value.splice(dataIndex, 1);
    }
}
</script>

<style scoped>
.title {
    color: rgba(0, 0, 0, 0.88);
    font-weight: 600;
    font-size: 16px;
}

:deep(.ant-descriptions-item-content) {
    padding: 0 !important;
}
</style>