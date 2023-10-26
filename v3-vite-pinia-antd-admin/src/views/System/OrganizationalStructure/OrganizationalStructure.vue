<template>
    <a-spin :spinning="loading">
        <a-table bordered rowKey="id" :columns="columns" :data-source="list">
            <template #bodyCell="{ record, column }">
                <template v-if="column.dataIndex === 'status'">
                    <a-tag :color="record.status === 1 ? 'green' : 'red'">{{ record.status === 1 ? '启用' : '停用' }}</a-tag>
                </template>
                <template v-if="column.dataIndex === 'action'">
                    <a-button class="btn-link mr-10" type="link">新增</a-button>
                    <a-button class="btn-link" type="link">修改</a-button>
                    <a-popconfirm v-if="!record.children" title="是否确认删除?" ok-text="确定" cancel-text="取消">
                        <a-button class="btn-link ml-10" type="link">删除</a-button>
                    </a-popconfirm>
                </template>
            </template>
        </a-table>
    </a-spin>
</template>

<script setup lang="ts">
import { useCrud } from "@/hooks"
import type { TableColumnsType } from 'ant-design-vue';

const { loading } = useCrud();

const columns: TableColumnsType = [
    { title: '名称', dataIndex: 'name' },
    { title: '排序', dataIndex: 'sort', align: 'center' },
    { title: '状态', dataIndex: 'status', align: 'center' },
    { title: '创建时间', dataIndex: 'createTime', align: 'center' },
    { title: '操作', dataIndex: 'action', width: '150px', align: 'center' },
]

const list = [
    {
        name: '西力科技',
        sort: 1,
        status: 1,
        createTime: '2021-01-01 12:00:00',
        children: [
            {
                name: '研发部',
                sort: 1,
                status: 1,
                createTime: '2021-01-01 12:00:00',
            }
        ]
    }
]
</script>

<style scoped>
.btn-link {
    padding: 0;
}
</style>