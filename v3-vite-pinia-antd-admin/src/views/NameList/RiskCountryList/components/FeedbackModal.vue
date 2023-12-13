<template>
    <a-modal :open="isOpen" :closable="false" :maskClosable="true" width="60%" @cancel="handleClose">
        <template #title>
            <p class="mb-15" v-if="code === 200">上传成功</p>
            <div class="flex aic jc-sb mb-15" v-else>
                <p>上传失败</p>
                <a-button @click="exportToExcel">
                    <CloudDownloadOutlined />
                    导出
                </a-button>
            </div>
        </template>
        <a-table :pagination="{ position: ['bottomCenter'] }" bordered size="small" :dataSource="feedback" :columns="code === 200 ? columns : errorColumns">
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
                <template v-if="column.dataIndex === 'status'">
                    <a-tag style="margin: 0;" color="green">上传成功</a-tag>
                </template>
                <template v-if="column.dataIndex === 'errorMessage'">
                    <CloseCircleOutlined style="color: red;margin-right: 5px;" />{{ record.errorMessage }}
                </template>
            </template>
        </a-table>
        <template #footer>
            <a-button @click="handleClose">取消</a-button>
            <a-button type="primary" @click="handleClose">确定</a-button>
        </template>
    </a-modal>
</template>

<script setup lang="ts">
import * as XLSX from 'xlsx';
import { formatDate } from '@/utils'
import type { TableColumnsType } from 'ant-design-vue';

const props = defineProps(['isOpen', 'feedback', 'code']);
const emit = defineEmits(['update:modalStatus']);

const columns: TableColumnsType = [
    { title: "中文名", dataIndex: "cnName", align: "left", ellipsis: true },
    { title: "英文名", dataIndex: "enName", align: "left", ellipsis: true },
    { title: "英文简称", dataIndex: "enAbbreviation", align: "left", ellipsis: true },
    { title: "风险等级", dataIndex: "riskLevel", align: "center", width: 100 },
    { title: "二位国别代码", dataIndex: "iso2countryCode", align: "center" },
    { title: "三位国别代码", dataIndex: "iso3countryCode", align: "center" },
    { title: "状态", dataIndex: "status", align: "center", width: 100 }
];

const errorColumns: TableColumnsType = [
    { title: "行数", dataIndex: "rowNum", align: "center" },
    { title: "列数", dataIndex: "colNum", align: "center" },
    { title: "字段名", dataIndex: "fieldName", align: "center" },
    { title: "错误信息", dataIndex: "errorMessage", align: "center" }
];

const isOpen = ref();
const feedback = ref();
const code = ref();

watch(props, (newValue) => {
    isOpen.value = newValue.isOpen;
    feedback.value = newValue.feedback;
    code.value = newValue.code;
})

const handleClose = () => {
    emit('update:modalStatus', false);
}

// #导出为EXCEL
const exportToExcel = () => {
    const wb = XLSX.utils.book_new();
    const header = ['行数', '列数', '字段名称', '错误信息'];
    const wsData = [header, ...feedback.value.map((item: any) => Object.values(item))];
    const ws = XLSX.utils.aoa_to_sheet(wsData);
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
    const date = formatDate(new Date());
    saveAsExcelFile(excelBuffer, `${date}_错误明细`);
}

// #保存EXCEL
const saveAsExcelFile = (buffer: any, fileName: string) => {
    const blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = `${fileName}.xlsx`;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}
</script>

<style scoped></style>