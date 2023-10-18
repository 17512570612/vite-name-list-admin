<template>
    <div class="search-box">
        <a-form :labelCol="{ span: 7 }" :model="queryForm">
            <a-row :gutter="24">
                <a-col :span="6">
                    <a-form-item label="所属机构">
                        <a-select placeholder="请选择所属机构" />
                    </a-form-item>
                </a-col>
                <a-col :span="6">
                    <a-form-item label="工号">
                        <a-input placeholder="请输入工号" />
                    </a-form-item>
                </a-col>
                <a-col :span="6">
                    <a-form-item label="姓名">
                        <a-input placeholder="请输入姓名" />
                    </a-form-item>
                </a-col>
                <a-col :span="6">
                    <a-form-item label="状态">
                        <a-select placeholder="请选择状态" />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="24" v-if="isExpand">
                <a-col :span="6">
                    <a-form-item label="用户角色">
                        <a-select placeholder="请选择角色" />
                    </a-form-item>
                </a-col>
                <a-col :span="6">
                    <a-form-item label="登录日期">
                        <a-select placeholder="请选择角色" />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row>
                <a-col :span="24" class="flex aic jc-sb" style="text-align: right;margin-bottom: 20px;">
                    <a-button @click="handleCreate">
                        <template #icon>
                            <PlusOutlined />
                        </template>
                        新增
                    </a-button>
                    <div>
                        <a-button type="primary">
                            <template #icon>
                                <SearchOutlined />
                            </template>
                            查询
                        </a-button>
                        <a-button style="margin: 0 8px">
                            <template #icon>
                                <ReloadOutlined />
                            </template>
                            重置
                        </a-button>
                        <a style="font-size: 12px" @click="isExpand = !isExpand">
                            <template v-if="isExpand">
                                <UpOutlined />
                            </template>
                            <template v-else>
                                <DownOutlined />
                            </template>
                            {{ isExpand ? "收起" : "展开" }}
                        </a>
                    </div>
                </a-col>
            </a-row>
        </a-form>
    </div>
    <a-spin :spinning="loading">
        <a-table bordered :columns="columns" :data-source="list">
            <template #bodyCell="{ record, column }">
                <div v-if="column.dataIndex === 'jobNumber'">
                    {{ record.jobNumber ? record.jobNumber : "-" }}
                </div>
                <div v-if="column.dataIndex === 'institution'">
                    {{ record.institution ? record.institution : "-" }}
                </div>
                <div v-if="column.dataIndex === 'status'">
                    {{ record.status ? record.status : "-" }}
                </div>
                <div class="flex jc-c" v-if="column.dataIndex === 'action'">
                    <a class="mr-10">编辑</a>
                    <a>删除</a>
                </div>
            </template>
        </a-table>
    </a-spin>
    <UserModal :isOpen="isOpen" @update:modal-status="updateModalStatus" />
</template>

<script setup lang="ts">
import { API } from "@/service"
import { useCrud } from "@/hooks"
import UserModal from "./components/UserModal.vue"
import { ColumnGroupType, ColumnType } from "ant-design-vue/es/table";

const { loading, list, queryForm, query } = useCrud();

const columns: (ColumnGroupType<any> | ColumnType<any>)[] = [
    {
        title: '序号',
        dataIndex: 'id',
        width: 200
    },
    {
        title: '工号',
        align: 'center',
        dataIndex: 'jobNumber',
    },
    {
        title: '姓名',
        align: 'center',
        dataIndex: 'name',
    },
    {
        title: '角色',
        align: 'center',
        dataIndex: 'sort',
    },
    {
        title: '机构',
        align: 'center',
        dataIndex: 'institution',
    },
    {
        title: '状态',
        align: 'center',
        dataIndex: 'status',
        width: 180
    },
    {
        title: '操作',
        dataIndex: 'action',
        align: 'center',
        width: 120
    },
];

const isExpand = ref(false);
const isOpen = ref(false);

const handleCreate = () => {
    isOpen.value = true;
}

const updateModalStatus = (newVal: boolean) => {
    isOpen.value = newVal;
}

onMounted(async () => {
    await query(API.USER_LIST);
})
</script>

<style scoped>
:deep(.ant-descriptions-item-label) {
    width: 80px;
    display: flex !important;
    align-items: center !important;
}
</style>