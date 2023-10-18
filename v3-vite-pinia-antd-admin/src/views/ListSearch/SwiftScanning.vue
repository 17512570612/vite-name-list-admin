<!-- Swift报文检索 -->
<template>
    <a-card title="反洗钱名单SWIFT报文检索" :bordered="false" style="width: 100%">
        <a-spin size="large" :spinning="loading">
            <div class="flex ais mb-20">
                <a-upload accept=".txt,.xml" :file-list="fileList" :before-upload="beforeUpload" @remove="handleRemove">
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
                <a-descriptions-item :labelStyle="{ width: '120px' }" :span="1">
                    <template #label>
                        <span class="red">* </span>报文类型
                    </template>
                    <a-select allowClear :bordered="false" v-model:value="swiftState.swiftType" style="width: 100%" :options="SWIFT_TYPES" placeholder="请选择报文类型" @change="handleChange" />
                </a-descriptions-item>
                <a-descriptions-item :labelStyle="{ width: '120px' }" :span="1">
                    <template #label>
                        <span class="red" v-if="!disabled">* </span>报文文件码
                    </template>
                    <a-select allowClear :bordered="false" :disabled="disabled" v-model:value="swiftState.swiftCode" style="width: 100%" :options="swiftCodeOptions" placeholder="请选择报文文件码" />
                </a-descriptions-item>
            </a-descriptions>
        </a-spin>
    </a-card>
</template>

<script setup lang="ts">
import { message } from 'ant-design-vue'
import type { UploadProps } from "ant-design-vue";
import { SWIFT_TYPES, CNAPS_SWIFT_CODE, CIPS_SWIFT_CODE } from "@/config";

const router = useRouter();

const loading = ref<boolean>(false);
const fileList = ref<UploadProps["fileList"]>([]);
const uploading = ref<boolean>(false);

interface SwiftState {
    swiftType: string | undefined,
    swiftCode: string | undefined
}

const swiftState = <SwiftState>reactive({
    swiftType: undefined,
    swiftCode: undefined
});

const beforeUpload: UploadProps["beforeUpload"] = (file) => {
    fileList.value = [file];
    return false;
};

const handleRemove: UploadProps['onRemove'] = () => {
    fileList.value = []
};

const handleChange = () => {
    swiftState.swiftCode = undefined
}

const disabled = computed(() => {
    return !['1', '2', '5'].includes(swiftState.swiftType!);
});

const swiftCodeOptions = computed(() => {
    return swiftState.swiftType === '1' || swiftState.swiftType === '5' ? CNAPS_SWIFT_CODE : CIPS_SWIFT_CODE;
});

const handleUpload = () => {
    if (!swiftState.swiftType) { return message.warning("请选择报文类型"); }
    if (['1', '2', '5'].includes(swiftState.swiftType) && !swiftState.swiftCode) { return message.warning("请选择报文文件码"); }
    loading.value = true; uploading.value = true;
    setTimeout(() => {
        loading.value = false;
        uploading.value = false;
        router.push("/ScanDetails")
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

.red {
    color: red;
}
</style>
