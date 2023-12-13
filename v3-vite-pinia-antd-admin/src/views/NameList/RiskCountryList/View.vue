<!-- 查看 -->
<template>
    <a-form>
        <a-row :gutter="20">
            <a-col :span="6">
                <a-form-item label="国家名称">
                    <a-input placeholder="请输入国家名称" allowClear />
                </a-form-item>
            </a-col>
            <a-col :span="6">
                <a-form-item label="国家类别">
                    <a-select placeholder="请选择国家类别" allowClear />
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
    </a-form>
    <a-spin :spinning="loading">
        <a-table bordered :columns="columns" :data-source="list" :pagination="pagination" @change="handlePagination">
            <template #bodyCell="{ column, record }">
                <template v-if="column.dataIndex === 'riskLevel'">
                    <a-tag :color="record.riskLevel === 'SANCTION' ? 'red' : record.riskLevel === 'HIGH' ? 'orange' : 'blue'">
                        {{ record.riskLevel === 'SANCTION' ? '制裁类' : record.riskLevel === 'HIGH' ? '高风险' : '其他' }}
                    </a-tag>
                </template>
                <template v-if="column.dataIndex === 'action'">
                    <a-button class="p-0" type="link" @click="handleView(record)">查看</a-button>
                </template>
            </template>
        </a-table>
    </a-spin>
    <ViewModal :isOpen="isOpen" :formData="formData" @update:modal-status="updateModalStatus" />
</template>

<script setup lang="ts">
import { API } from '@/service'
import { useCrud } from '@/hooks'
import type { TableColumnsType } from 'ant-design-vue';
import ViewModal from './components/ViewModal.vue'

const { url, list, loading, queryForm, pagination, query } = useCrud();
url.value.query = API.COUNTRY_LIST;

const columns: TableColumnsType = [
    { title: '英文名称', dataIndex: 'enName', ellipsis: true },
    { title: '英文简称', dataIndex: 'enAbbreviation', ellipsis: true },
    { title: '中文名', dataIndex: 'cnName', ellipsis: true },
    { title: '风险国家类别', dataIndex: 'riskLevel', align: 'center', width: 150 },
    { title: '二位国别码', dataIndex: 'iso2countryCode', align: 'center', width: 120 },
    { title: '三位国别码', dataIndex: 'iso3countryCode', align: 'center', width: 120 },
    { title: '操作', dataIndex: 'action', align: 'center', width: 100 },
]

const isExpand = ref(false);
const handleExpand = () => {
    isExpand.value = !isExpand.value;
}

const isOpen = ref(false);
const formData = ref();
const handleView = (record: any) => {
    isOpen.value = true;
    formData.value = record;
}

const updateModalStatus = (val: boolean) => {
    isOpen.value = val;
}

const handlePagination = (page: any) => {
    queryForm.value.pageNo = page.current;
    queryForm.value.pageSize = page.pageSize;
    query(queryForm.value);
};

onMounted(() => query())
</script>

<style scoped></style>