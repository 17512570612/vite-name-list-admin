<!-- 菜单管理 -->
<template>
    <div class="search-box">
        <a-form labelAlign="right" :labelCol="{ span: 8 }">
            <div class="flex jc-sb">
                <a-button class="mb-10" @click="handleAdd">
                    <template #icon>
                        <PlusOutlined />
                    </template>
                    新增
                </a-button>
                <div class="flex">
                    <a-form-item label="菜单名称">
                        <a-input placeholder="请输入菜单名称" allowClear />
                    </a-form-item>
                    <a-button class="ml-10" type="primary" @click="">
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
        <a-table bordered rowKey="id" :columns="columns" :data-source="list">
            <template #bodyCell="{ record, column }">
                <template v-if="column.dataIndex === 'icon'">
                    <component v-if="record.icon != ''" :is='record.icon'></component>
                    <p v-else>-</p>
                </template>
                <template v-if="column.dataIndex === 'type'">
                    {{ record.type === 0 ? '目录' : record.type === 1 ? '菜单' : '按钮' }}
                </template>
                <template v-if="column.dataIndex === 'status'">
                    <a-tag :color="record.status === 1 ? 'green' : 'red'">{{ record.status === 1 ? '启用' : '停用' }}</a-tag>
                </template>
                <template v-if="column.dataIndex === 'action'">
                    <a-button class="p-0 mr-10" type="link" @click="edit(record)">修改</a-button>
                    <a-popconfirm v-if="!record.children" title="是否确认删除?" ok-text="确定" cancel-text="取消" @confirm="remove(record.id)">
                        <a-button class="p-0" type="link">删除</a-button>
                    </a-popconfirm>
                </template>
            </template>
        </a-table>
        <MenuDrawer :open.sync="isModalShow" :formData="rowData" :treeMenu="list" @update:modal-show="updateModalShow" />
    </a-spin>
</template>

<script setup lang="ts">
import { API } from "@/service"
import { useCrud } from "@/hooks"
import MenuDrawer from './components/MenuDrawer.vue';
import type { TableColumnsType } from 'ant-design-vue';

const columns: TableColumnsType = [
    { title: '名称', dataIndex: 'name', ellipsis: true, width: 200 },
    { title: '图标', align: 'center', dataIndex: 'icon', },
    { title: '类型', align: 'center', dataIndex: 'type', },
    { title: '排序', align: 'center', dataIndex: 'sort', },
    { title: '状态', align: 'center', dataIndex: 'status', },
    { title: '组件', align: 'center', dataIndex: 'component', ellipsis: true, width: 180 },
    { title: '路径', align: 'center', dataIndex: 'path', ellipsis: true, width: 180 },
    { title: '操作', align: 'center', dataIndex: 'action', width: 120 },
];

const { loading, list, url, query, remove } = useCrud();
url.value.query = API.MENU_LIST;
url.value.remove = API.MENU_DELETE;

const isModalShow = ref<boolean>(false)

const updateModalShow = (newOpen: boolean) => {
    isModalShow.value = newOpen
    getMenuTree()
}

const handleAdd = () => {
    isModalShow.value = true
}

const getMenuTree = () => {
    query()
}

const rowData = ref();

const edit = (row: any) => {
    rowData.value = row;
    isModalShow.value = true;
}

onMounted(() => getMenuTree())
</script>

<style scoped>
.isExpand {
    width: 100%;
}

:deep(.ant-tag) {
    margin: 0;
}

:deep(.ant-upload-list-item-name) {
    width: 30px;
}
</style>
