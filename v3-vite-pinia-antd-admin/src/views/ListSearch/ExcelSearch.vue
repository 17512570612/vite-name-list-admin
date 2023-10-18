<!-- EXCEL扫描 -->
<template>
    <a-card title="反洗钱名单EXCEL检索" :bordered="false" style="width: 100%">
        <a-spin size="large" :spinning="loading">
            <div class="flex ais mb-20">
                <a-upload accept=".xls,.xlsx" :file-list="fileList" :before-upload="beforeUpload" @remove="handleRemove">
                    <a-button type="primary">
                        <template #icon>
                            <FolderOpenOutlined />
                        </template>
                        选择文件
                    </a-button>
                </a-upload>
                <a-button class="ml-10">
                    <template #icon>
                        <DownloadOutlined />
                    </template>
                    模版下载
                </a-button>
                <a-button class="ml-10" type="primary" :disabled="fileList?.length === 0" :loading="uploading" @click="handleUpload">
                    <template #icon>
                        <SearchOutlined />
                    </template>
                    {{ uploading ? "解析中..." : "查询" }}
                </a-button>
            </div>
            <a-descriptions bordered size="small" :column="2" :contentStyle="{ width: '300px' }">
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="匹配规则">
                    <a-radio-group v-model:value="formState.rule">
                        <a-radio :value="1">相近</a-radio>
                        <a-radio :value="2">完全</a-radio>
                        <a-radio :value="3">包含</a-radio>
                    </a-radio-group>
                </a-descriptions-item>
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="名单属性">
                    <a-checkbox-group v-model:value="formState.type">
                        <a-checkbox :value="1" name="type">个人</a-checkbox>
                        <a-checkbox :value="2" name="type">实体</a-checkbox>
                    </a-checkbox-group>
                </a-descriptions-item>
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="性别">
                    <a-radio-group v-model:value="formState.gender">
                        <a-radio value="1">男</a-radio>
                        <a-radio value="2">女</a-radio>
                        <a-radio value="3">未知</a-radio>
                    </a-radio-group>
                </a-descriptions-item>
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="制裁来源" :span="1">
                    <a-select :bordered="false" v-model:value="formState.sourceOfSanctions" :maxTagCount="3" allowClear mode="multiple" style="width: 100%" placeholder="请选择" :options="SOURCE_OF_SANCTION" />
                </a-descriptions-item>
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="国籍信息" :span="2">
                    <a-select :bordered="false" v-model:value="formState.nationality" :maxTagCount="3" allowClear mode="multiple" style="width: 100%" placeholder="请选择" :options="NATIONALITY" />
                </a-descriptions-item>
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="名单类型" :span="2">
                    <a-checkbox-group v-model:value="formState.listType" style="width: 100%">
                        <a-row>
                            <a-col :span="6" v-for="item of NAME_LIST_TYPES">
                                <a-checkbox :value="item.value">{{ item.label }}</a-checkbox>
                            </a-col>
                        </a-row>
                    </a-checkbox-group>
                </a-descriptions-item>
            </a-descriptions>
            <div class="flex aic jc-c mt-20">
                <a-button type="link" @click="resetForm()">
                    <template #icon>
                        <ReloadOutlined />
                    </template>
                    重置
                </a-button>
            </div>
        </a-spin>
    </a-card>
</template>

<script setup lang="ts">
import type { UploadProps } from "ant-design-vue";
import { SOURCE_OF_SANCTION, NATIONALITY, NAME_LIST_TYPES } from "@/config"

const router = useRouter();

const loading = ref<boolean>(false);
const fileList = ref<UploadProps["fileList"]>([]);
const uploading = ref<boolean>(false);

const formState = reactive({
    value: "",
    domain: undefined,
    rule: 3,
    listType: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
    type: [1, 2],
    gender: "",
    registrationDate: "",
    dateOfBirth: "",
    nationality: [],
    sourceOfSanctions: [],
});

const resetForm = () => {
    formState.domain = undefined;
    formState.rule = 0;
    formState.listType = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
    formState.type = [];
    formState.gender = "";
    formState.registrationDate = "";
    formState.dateOfBirth = "";
    formState.nationality = [];
    formState.sourceOfSanctions = [];
};

const beforeUpload: UploadProps["beforeUpload"] = (file) => {
    fileList.value = [file];
    return false;
};

const handleRemove: UploadProps['onRemove'] = () => {
    fileList.value = []
};

const handleUpload = () => {
    loading.value = true;
    uploading.value = true;
    setTimeout(() => {
        loading.value = false;
        uploading.value = false;
        router.push("/ScanDetails");
    }, 3000)
};

</script>

<style scoped>
.advanced-search {
    border-radius: 2px;
}

:deep(.ant-upload-list-item-name) {
    width: 30px;
}
</style>
