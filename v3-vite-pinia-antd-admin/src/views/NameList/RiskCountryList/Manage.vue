<!-- 管理 -->
<template>
    <a-form>
        <a-row :gutter="20">
            <a-col :span="6">
                <a-form-item label="国家名称">
                    <a-input placeholder="请输入国家名称" v-model:value="queryForm.countryName" allowClear />
                </a-form-item>
            </a-col>
            <a-col :span="6">
                <a-form-item label="国家类别">
                    <a-select placeholder="请选择国家类别" allowClear :options="store.$state.dict['RISK_LEVEL']" />
                </a-form-item>
            </a-col>
            <a-col :span="6" v-if="isExpand">
                <a-form-item label="国别代码">
                    <a-select placeholder="请选择国别码" :fieldNames="{ label: 'iso3countrycode', value: 'iso3countrycode' }" allowClear :options="countryAllCode"></a-select>
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
                    <a-button type="primary" @click="query(queryForm)">
                        <template #icon>
                            <SearchOutlined />
                        </template>
                        查询
                    </a-button>
                    <a-button class="ml-10" @click="reset">
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
    <div class="isExpand flex jc-sb mb-20">
        <div class="flex">
            <a-button class="mr-10" @click="create">
                <template #icon>
                    <PlusOutlined />
                </template>
                新增
            </a-button>
            <a-upload accept=".xls,.xlsx" :file-list="fileList" :before-upload="beforeUpload" @remove="handleRemove">
                <a-button type="primary">
                    <template #icon>
                        <FolderOpenOutlined />
                    </template>
                    选择文件
                </a-button>
            </a-upload>
            <a-button class="ml-10" type="primary" :disabled="fileList?.length === 0" :loading="uploading" @click="handleUpload">
                <template #icon>
                    <CloudUploadOutlined />
                </template>
                {{ uploading ? "解析中..." : "上传" }}
            </a-button>
            <a-button class="ml-10" @click="downloadFile">
                <template #icon>
                    <DownloadOutlined />
                </template>
                模版下载
            </a-button>
        </div>
        <a-popconfirm placement="bottomRight" :title="selectedRowKeys.length > 0
            ? '确定删除所选信息吗?'
            : '确定删除所有信息吗?'
            " ok-text="确定" cancel-text="取消" @confirm="remove(selectedRowKeys)">
            <a-button>
                <template #icon>
                    <DeleteOutlined />
                </template>
                {{ selectedRowKeys.length > 0 ? "批量删除" : "全部删除" }}
            </a-button>
        </a-popconfirm>
    </div>
    <a-spin :spinning="loading">
        <a-table bordered :row-selection="rowSelection" rowKey="id" :columns="columns" :data-source="list" :pagination="pagination" @change="handlePagination">
            <template #bodyCell="{ column, record }">
                <template v-if="column.dataIndex === 'riskLevel'">
                    <a-tag :color="record.riskLevel === 'SANCTION' ? 'red' : record.riskLevel === 'HIGH' ? 'orange' : 'blue'">
                        {{ record.riskLevel === 'SANCTION' ? '制裁类' : record.riskLevel === 'HIGH' ? '高风险' : '其他' }}
                    </a-tag>
                </template>
                <template v-if="column.dataIndex === 'maintenanceState'">
                    {{ status[record.maintenanceState] }}
                </template>
                <template v-if="column.dataIndex === 'delFlag'">
                    <p v-if="record.delFlag === '1'">未删除</p>
                    <p v-else>已删除</p>
                </template>
                <template v-if="column.dataIndex === 'startDate'">
                    {{ record.startDate || "-" }}
                </template>
                <template v-if="column.dataIndex === 'action'">
                    <a-button class="p-0" v-if="record.delFlag == '1'" type="link" @click="edit(record)">编辑</a-button>
                    <a-popconfirm title="确定删除该条信息吗?" v-if="record.delFlag == '1'" ok-text="确定" cancel-text="取消" @confirm="remove(record.id)">
                        <a-button class="p-0 ml-10" type="link">删除</a-button>
                    </a-popconfirm>
                </template>
            </template>
        </a-table>
    </a-spin>
    <FeedbackModal :isOpen="feedbackShow" :feedback="feedback" :code="code" @update:modal-status="updateModalStatus" />
    <CountryModal :isOpen="isOpen" :formData="formData" :countryCode="countryAllCode" @update:modal-status="updateModalStatus"></CountryModal>
</template>

<script setup lang="ts">
import { useAppStore } from "@/store";
import { useCrud, useCommon } from "@/hooks";
import { API, post, download } from "@/service";
import type { TableColumnsType } from 'ant-design-vue';
import CountryModal from "./components/CountryModal.vue";
import FeedbackModal from "./components/FeedbackModal.vue";

const store = useAppStore();

const columns: TableColumnsType = [
    { title: "英文名称", dataIndex: "enName", ellipsis: true },
    { title: "中文名", dataIndex: "cnName", ellipsis: true },
    { title: "风险国家类别", dataIndex: "riskLevel", align: "center", width: 150 },
    { title: "维护状态", dataIndex: "maintenanceState", align: "center", ellipsis: true, width: 150 },
    { title: "生效日期", dataIndex: "startDate", align: "center", ellipsis: true },
    { title: "上传日期", dataIndex: "uploadTime", align: "center", ellipsis: true },
    { title: "删除状态", dataIndex: "delFlag", ellipsis: true, align: "center", width: 150 },
    { title: "操作", dataIndex: "action", align: "center" },
];

const status: { [key: number]: string } = {
    1: "正式项目",
    2: "编辑中",
    3: "待审批",
    4: "审批拒绝",
    5: "已删除",
};

const { url, list, loading, queryForm, pagination, query, remove } = useCrud();
const { countryAllCode, selectedRowKeys, rowSelection, getCountryCode } = useCommon();

url.value.query = API.COUNTRY_LIST;
url.value.remove = API.COUNTRY_DELETE;

const isExpand = ref(false);
const handleExpand = () => {
    isExpand.value = !isExpand.value;
};

const reset = () => {
    queryForm.value = { pageNo: 1, pageSize: 10 };
}

// #上传文件前
const fileList: any = ref([]);
const uploading = ref(false);
const beforeUpload = (file: any) => {
    fileList.value = [file];
    return false;
};

// #上传文件
const code = ref(200);
const feedback = ref([]);
const feedbackShow = ref(false);
const handleUpload = async () => {
    loading.value = true;
    try {
        const file = new FormData();
        fileList.value.forEach((f: any) => file.append("file", f));
        const res: response = await post(API.COUNTRY_UPLOAD, file);
        fileList.value = [];
        feedbackShow.value = true;
        code.value = res.code;
        feedback.value = res.result;
    } catch (error: any) {
        console.error('上传文件失败：', error);
    } finally {
        loading.value = false;
    }
};

// #删除文件
const handleRemove = () => {
    fileList.value = [];
};

// #下载模版
const downloadFile = async () => {
    await download(API.COUNTRY_FILE).then((response: any) => {
        let link = document.createElement("a");
        const blob = new Blob([response, { type: "application/vnd.ms-excel" }]);
        link.href = URL.createObjectURL(blob);
        link.download = "国家地区名单模版.xls";
        document.body.appendChild(link);
        link.click();
        URL.revokeObjectURL(link.href);
    });
};

const isOpen = ref(false);
const formData = ref();

const create = () => {
    formData.value = {};
    isOpen.value = true;
}

const edit = (item: any) => {
    isOpen.value = true;
    formData.value = item;
}

// #关闭反馈窗口
const updateModalStatus = (newVal: boolean) => {
    feedbackShow.value = newVal;
    isOpen.value = newVal;
};

const handlePagination = (page: any) => {
    queryForm.value.pageNo = page.current;
    queryForm.value.pageSize = page.pageSize;
    query(queryForm.value);
};

onMounted(() => {
    query();
    getCountryCode();
});
</script>

<style scoped>
:deep(.ant-upload-list-item-name) {
    width: 30px;
}
</style>
