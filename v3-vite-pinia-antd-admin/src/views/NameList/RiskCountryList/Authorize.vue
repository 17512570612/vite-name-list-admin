<!-- 授权 -->
<template>
    <a-form>
        <a-row :gutter="20">
            <a-col :span="6">
                <a-form-item label="国家名称">
                    <a-input placeholder="请输入国家名称" allowClear />
                </a-form-item>
            </a-col>
            <a-col :span="6">
                <a-form-item label="风险国家类别">
                    <a-select placeholder="请选择风险国家类别" allowClear />
                </a-form-item>
            </a-col>
            <a-col :span="6" v-if="isExpand">
                <a-form-item label="国别代码">
                    <a-select placeholder="请选择国别码" allowClear></a-select>
                </a-form-item>
            </a-col>
            <a-col :span="6" v-if="isExpand">
                <a-form-item label="所属洲">
                    <a-input placeholder="请输入所属洲" allowClear />
                </a-form-item>
            </a-col>
            <a-col :span="6" v-if="isExpand">
                <a-form-item label="名单状态">
                    <a-select placeholder="请选择名单状态" allowClear></a-select>
                </a-form-item>
            </a-col>
            <a-col :span="6" v-if="isExpand">
                <a-form-item label="生效日期">
                    <a-range-picker format="YYYY/MM/DD" :placeholder="['开始', '结束']" allowClear />
                </a-form-item>
            </a-col>
            <a-col :span="6" v-if="isExpand">
                <a-form-item label="失效日期">
                    <a-range-picker format="YYYY/MM/DD" :placeholder="['开始', '结束']" allowClear />
                </a-form-item>
            </a-col>
            <a-col :span="6">
                <a-form-item>
                    <a-button type="primary" @click="">
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
                    <a-button type="link" @click="handleExpand()">
                        <template v-if="isExpand">
                            收起
                            <UpOutlined />
                        </template>
                        <template v-else>
                            展开
                            <DownOutlined />
                        </template>
                    </a-button>
                </a-form-item>
            </a-col>
        </a-row>
        <div class="mb-20">
            <a-popconfirm placement="bottomLeft" :title="selectedRowKeys.length > 0 ? '确定通过所选信息吗?' : '确定通过所有信息吗?'" ok-text="确定" cancel-text="取消" @confirm="handleApproved()">
                <a-button type="primary">
                    <template #icon>
                        <CheckOutlined />
                    </template>
                    {{ selectedRowKeys.length > 0 ? '批量通过' : '全部通过' }}
                </a-button>
            </a-popconfirm>
            <a-popconfirm placement="bottomLeft" :title="selectedRowKeys.length > 0 ? '确定驳回所选信息吗?' : '确定驳回所有信息吗?'" ok-text="确定" cancel-text="取消" @confirm="handleRejected()">
                <a-button class="ml-10">
                    <template #icon>
                        <CloseOutlined />
                    </template>
                    {{ selectedRowKeys.length > 0 ? '批量驳回' : '全部驳回' }}
                </a-button>
            </a-popconfirm>
        </div>
    </a-form>
    <a-spin :spinning="loading">
        <a-table bordered :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }" rowKey="id" :columns="columns" :data-source="list" :pagination="pagination" @change="handlePagination">
            <template #bodyCell="{ column, record }">
                <template v-if="column.dataIndex === 'riskLevel'">
                    <a-tag :color="record.riskLevel === 'SANCTION'
                        ? 'red'
                        : record.riskLevel === 'HIGH'
                            ? 'orange'
                            : 'blue'
                        ">
                        {{
                            record.riskLevel === "SANCTION"
                            ? "制裁类"
                            : record.riskLevel === "HIGH"
                                ? "高风险"
                                : "其他"
                        }}
                    </a-tag>
                </template>
                <template v-if="column.dataIndex === 'maintenanceState'">
                    {{ status[record.maintenanceState] }}
                </template>
                <template v-if="column.dataIndex === 'delFlag'">
                    {{ record.delFlag === "1" ? "未删除" : "已删除" }}
                </template>
                <template v-if="column.dataIndex === 'startDate'">
                    {{ record.startDate || "-" }}
                </template>
                <template v-if="column.dataIndex === 'action'">
                    <a-button class="p-0" type="link">查看</a-button>
                </template>
            </template>
        </a-table>
    </a-spin>
</template>

<script setup lang="ts">
import { API } from '@/service'
import { useCrud, useCommon } from '@/hooks';
import type { TableColumnsType } from 'ant-design-vue';

const columns: TableColumnsType = [
    { title: "英文名称", dataIndex: "enName", ellipsis: true, width: 200 },
    { title: "中文名", dataIndex: "cnName", ellipsis: true, width: 200 },
    { title: "风险国家类别", dataIndex: "riskLevel", align: "center", width: 150 },
    { title: "维护状态", dataIndex: "maintenanceState", align: "center", ellipsis: true, width: 100 },
    { title: "生效日期", dataIndex: "startDate", align: "center", ellipsis: true },
    { title: "上传日期", dataIndex: "uploadTime", align: "center", ellipsis: true },
    { title: "操作", dataIndex: "action", align: "center", width: 100 },
];

const status: { [key: number]: string } = {
    1: "正式项目",
    2: "编辑中",
    3: "待审批",
    4: "审批拒绝",
    5: "已删除",
};

const { url, loading, queryForm, list, pagination, query } = useCrud();
const { selectedRowKeys, onSelectChange, approved, rejected } = useCommon();

url.value.query = API.COUNTRY_AUTHORIZED_LIST;

const isExpand = ref(false);

const handleExpand = () => {
    isExpand.value = !isExpand.value;
}

const handlePagination = (page: any) => {
    queryForm.value.pageNo = page.current;
    queryForm.value.pageSize = page.pageSize;
    query(queryForm.value);
};

// #审批通过
const handleApproved = async () => {
    await approved(
        API.COUNTRY_APPROCED_PASS,
        selectedRowKeys.value
    ).finally(() => query())
}

// #审批驳回
const handleRejected = async () => {
    await rejected(
        API.COUNTRY_APPROCED_REJECTED,
        selectedRowKeys.value
    ).finally(() => query())
}

onMounted(() => query())
</script>

<style scoped></style>