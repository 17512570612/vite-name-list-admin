<!-- 手工检索 -->
<template>
    <a-card title="反洗钱名单手工检索" :bordered="false" style="width: 100%">
        <a-spin size="large" :spinning="loading">
            <div class="search-input flex aic mb-15">
                <a-input size="large" v-model:value="formState.value" placeholder="请输入检索内容" />
                <a-button size="large" type="primary" style="margin-left: 10px" @click="queryForm()">
                    <template #icon>
                        <SearchOutlined />
                    </template>
                    查询
                </a-button>
                <a-button size="large" style="margin-left: 10px" @click="resetForm()">
                    <template #icon>
                        <ReloadOutlined />
                    </template>
                    重置
                </a-button>
            </div>
            <a-descriptions bordered size="small" :column="2" :contentStyle="{ width: '300px' }">
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="检索域" :span="1">
                    <a-select :bordered="false" v-model:value="formState.domain" style="width: 100%" :options="RETRIEVAL_DOMAIN" placeholder="请选择" />
                </a-descriptions-item>
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
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="注册日期">
                    <a-date-picker :bordered="false" style="width: 100%" v-model:value="formState.registrationDate" value-format="YYYY-MM-DD" placeholder="请选择" />
                </a-descriptions-item>
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="性别">
                    <a-radio-group v-model:value="formState.gender">
                        <a-radio value="1">男</a-radio>
                        <a-radio value="2">女</a-radio>
                        <a-radio value="3">未知</a-radio>
                    </a-radio-group>
                </a-descriptions-item>
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="出生日期">
                    <a-date-picker :bordered="false" style="width: 100%" v-model:value="formState.dateOfBirth" value-format="YYYY-MM-DD" placeholder="请选择" />
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
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="国籍信息" :span="1">
                    <a-select :bordered="false" v-model:value="formState.nationality" :maxTagCount="3" allowClear mode="multiple" style="width: 100%" placeholder="请选择" :options="NATIONALITY" />
                </a-descriptions-item>
                <a-descriptions-item :labelStyle="{ width: '120px' }" label="制裁来源">
                    <a-select :bordered="false" v-model:value="formState.sourceOfSanctions" :maxTagCount="3" allowClear mode="multiple" style="width: 100%" placeholder="请选择" :options="SOURCE_OF_SANCTION" />
                </a-descriptions-item>
            </a-descriptions>
        </a-spin>
    </a-card>
</template>
  
<script setup lang="ts">
import { SOURCE_OF_SANCTION, RETRIEVAL_DOMAIN, NATIONALITY, NAME_LIST_TYPES } from '@/config'

const router = useRouter();

const formState = reactive({
    value: '',
    domain: undefined,
    rule: 3,
    listType: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
    type: [1, 2],
    gender: '',
    registrationDate: '',
    dateOfBirth: '',
    nationality: [],
    sourceOfSanctions: [],
});

const resetForm = () => {
    formState.domain = undefined;
    formState.rule = 0;
    formState.listType = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
    formState.type = [];
    formState.gender = '';
    formState.registrationDate = '';
    formState.dateOfBirth = '';
    formState.nationality = [];
    formState.sourceOfSanctions = [];
};

const loading = ref<boolean>(false);

const queryForm = () => {
    loading.value = true
    setTimeout(() => {
        loading.value = false
        router.push('/ScanDetails')
    }, 1000)
}
</script>
  
<style scoped>
.advanced-search {
    border-radius: 2px;
}
</style>
  