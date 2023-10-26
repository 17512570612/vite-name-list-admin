<template>
    <a-drawer width="30%" v-model:open="open" :closable="false" :footer-style="{ textAlign: 'right' }" @close="handleModalClose">
        <template #title>新增</template>
        <a-form :model="formData" :label-col="{ style: { width: '70px' } }" ref="formRef">
            <a-form-item label="菜单类型" name="type">
                <a-select v-model:value="(formData.type as number)" style="width: 100%" placeholder="请选择" :options="menuOptions" allowClear />
            </a-form-item>
            <a-form-item label="上级菜单" name="parentId">
                <a-tree-select v-model:value="formData.parentId" show-search style="width: 100%" :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }" placeholder="请选择上级菜单" allow-clear tree-default-expand-all :tree-data="treeMenu" :field-names="{
                    children: 'children',
                    label: 'name',
                    value: 'id',
                }" tree-node-filter-prop="name"></a-tree-select>
            </a-form-item>
            <a-form-item label="菜单名称" name="name">
                <a-input v-model:value="formData.name" placeholder="请输入名称" allowClear />
            </a-form-item>
            <a-form-item label="菜单图标" name="icon">
                <a-input :disabled="true" v-model:value="formData.icon" placeholder="请选择图标">
                    <template #addonAfter>
                        <setting-outlined @click="selectIcon" />
                    </template>
                </a-input>
            </a-form-item>
            <a-form-item label="排序" name="sort">
                <a-input-number style="width: 100%;" v-model:value="formData.sort" placeholder="请输入排序" allowClear />
            </a-form-item>
            <a-form-item label="菜单状态" name="status">
                <a-radio-group v-model:value="formData.status" :options="radioOptions" />
            </a-form-item>
            <a-form-item label="菜单组件" name="component">
                <a-input v-model:value="formData.component" placeholder="请输入菜单组件" allowClear />
            </a-form-item>
            <a-form-item label="菜单地址" name="path">
                <a-input v-model:value="formData.path" placeholder="请输入菜单地址" allowClear />
            </a-form-item>
        </a-form>
        <template #footer>
            <a-button style="margin-right: 8px" @click="handleModalClose">取消</a-button>
            <a-button type="primary" @click="handleOk">提交</a-button>
        </template>
    </a-drawer>
    <IconModal :open.sync="iconModalShow" @update:modal-show="updateModalShow" @update:icon-value="updateIconValue" />
</template>

<script lang="ts" setup>
import { API } from '@/service'
import { useCrud } from '@/hooks'
import IconModal from './IconModal.vue';
import { SettingOutlined } from '@ant-design/icons-vue'

const props = defineProps({
    open: Boolean,
    formData: Object,
    treeMenu: Array<FormData>,
});

const emit = defineEmits(["update:modalShow"]);

const { url, create } = useCrud();
url.value.create = API.MENU_ADD;

interface FormData {
    parentId: string | null;
    parent: number;
    name: string;
    icon: string;
    type: number | null;
    sort: string;
    status: string;
    component: string;
    path: string;
    children: FormData[];
}

const formData = ref<FormData>({
    parentId: null,
    parent: 0,
    name: '',
    icon: '',
    type: null,
    sort: '',
    status: '',
    component: '',
    path: '',
    children: []
})

const radioOptions = ref([
    { label: '启用', value: 1 },
    { label: '停用', value: 2 },
]);

const menuOptions = [
    { label: '目录', value: 0 },
    { label: '菜单', value: 1 },
    { label: '按钮', value: 2 }
];

const open = ref<boolean>(false);
const treeMenu = ref<FormData[] | undefined>([]);

watch(props, (newProps) => {
    open.value = newProps.open;
    treeMenu.value = newProps.treeMenu;
    if (props.formData != undefined) {
        formData.value = Object.assign({}, newProps.formData as FormData);
    }
}, { deep: true });

const handleModalClose = () => {
    emit("update:modalShow", false);
    resetForm();
}

const iconModalShow = ref<boolean>(false);

// #打开icon弹窗
const selectIcon = () => {
    iconModalShow.value = true;
}

// #更新icon弹窗状态
const updateModalShow = (newOpen: boolean) => {
    iconModalShow.value = newOpen;
}

// #更新icon值
const updateIconValue = (newVal: string) => {
    formData.value.icon = newVal;
}

// #重置表单字段
const resetForm = () => {
    formData.value.parentId = null;
    formData.value.parent = 0;
    formData.value.name = '';
    formData.value.icon = '';
    formData.value.type = null;
    formData.value.sort = '';
    formData.value.status = '';
    formData.value.component = '';
    formData.value.path = '';
    formData.value.children = [];
}

// #提交
const handleOk = async () => {
    try {
        formData.value.parentId ? formData.value.parent = 0 : formData.value.parent = 1;
        await create(formData.value);
        resetForm();
        // #延迟关闭窗口 防止窗口抖动
        await new Promise((resolve) => setTimeout(resolve, 100));
        handleModalClose();
    } catch (error) {
        console.log(error);
    }
}
</script>

<style lang="scss" scoped></style>