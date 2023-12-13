<template>
    <a-modal v-model:open="isOpen" :closable="false" :maskClosable="true" width="55%" cancel-text="取消" ok-text="确定" @cancel="handleClose" @ok="handleCreate">
        <template #title>新增</template>
        <a-form :model="formData" :label-col="{ style: { width: '120px' } }" ref="formRef" :rules="rules">
            <a-row :gutter="20">
                <a-col :span="12">
                    <a-form-item label="中文名" name="cnName">
                        <a-input v-model:value="formData.cnName" placeholder="请输入中文名称" allowClear />
                    </a-form-item>
                </a-col>
                <a-col :span="12">
                    <a-form-item label="英文名" name="enName">
                        <a-input v-model:value="formData.enName" placeholder="请输入英文名称" allowClear />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="20">
                <a-col :span="12">
                    <a-form-item label="二位国别代码" name="iso2countryCode">
                        <a-select :fieldNames="{ label: 'iso2countrycode', value: 'iso2countrycode' }" :options="countryAllCode" v-model:value="formData.iso2countryCode" style="width: 100%" placeholder="请选择" allowClear />
                    </a-form-item>
                </a-col>
                <a-col :span="12">
                    <a-form-item label="三位国别代码" name="iso3countryCode">
                        <a-select :fieldNames="{ label: 'iso3countrycode', value: 'iso3countrycode' }" :options="countryAllCode" v-model:value="formData.iso3countryCode" style="width: 100%" placeholder="请选择" allowClear />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="20">
                <a-col :span="12">
                    <a-form-item label="风险国家类别" name="riskLevel">
                        <a-select v-model:value="formData.riskLevel" :options="store.$state.dict['RISK_LEVEL']" style="width: 100%" placeholder="请选择" allowClear />
                    </a-form-item>
                </a-col>
                <a-col :span="12">
                    <a-form-item label="名单来源" name="source">
                        <a-select v-model:value="formData.source" :options="store.$state.dict['SOURCE_OF_SANCTIONS']" style="width: 100%" placeholder="请选择" allowClear />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="20">
                <a-col :span="12">
                    <a-form-item label="原名">
                        <a-input v-model:value="formData.oldName" placeholder="请输入原名" allowClear />
                    </a-form-item>
                </a-col>
                <a-col :span="12">
                    <a-form-item label="别称1">
                        <a-input v-model:value="formData.aka1" placeholder="请输入别名1" allowClear />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="20">
                <a-col :span="12">
                    <a-form-item label="别称2">
                        <a-input v-model:value="formData.aka2" placeholder="请输入别名2" allowClear />
                    </a-form-item>
                </a-col>
                <a-col :span="12">
                    <a-form-item label="别称3">
                        <a-input v-model:value="formData.aka3" placeholder="请输入别名3" allowClear />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="20">
                <a-col :span="12">
                    <a-form-item label="所属洲">
                        <a-input v-model:value="formData.continent" placeholder="请输入所属洲" allowClear />
                    </a-form-item>
                </a-col>
                <a-col :span="12">
                    <a-form-item label="所属地域">
                        <a-input v-model:value="formData.region" placeholder="请输入所属地域" allowClear />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row :gutter="20">
                <a-col :span="24">
                    <a-form-item label="备注">
                        <a-textarea style="width:100%" v-model:value="formData.remark" placeholder="请输入备注" :auto-size="{ minRows: 5, maxRows: 10 }" />
                    </a-form-item>
                </a-col>
            </a-row>
        </a-form>
    </a-modal>
</template>

<script setup lang="ts">
import { API } from '@/service'
import { useAppStore } from "@/store";
import { useCrud } from '@/hooks'
import type { Rule } from 'ant-design-vue/es/form';

const props = defineProps(['isOpen', 'formData', 'countryCode']);

const emit = defineEmits(["update:modalStatus"]);

const store = useAppStore();

const { url, create } = useCrud();
url.value.create = API.COUNTRY_ADD;

const isOpen = ref(false);
const formData = ref();
const countryAllCode = ref();
watch(props, (newVal: any) => {
    isOpen.value = newVal.isOpen;
    countryAllCode.value = newVal.countryCode;
    formData.value = Object.assign({}, newVal.formData);
})

const rules: Record<string, Rule[]> = {
    cnName: [{ required: true, message: '请输入中文名称', trigger: 'change' }],
    enName: [{ required: true, message: '请输入英文名称', trigger: 'change' }],
    iso2countryCode: [{ required: true, message: '请选择二位国别码', trigger: 'change' }],
    iso3countryCode: [{ required: true, message: '请选择三位国别码', trigger: 'change' }],
    riskLevel: [{ required: true, message: '请选择风险等级', trigger: 'change' }],
    source: [{ required: true, message: '请选择名单来源', trigger: 'change' }]
}

const reset = () => {
    formData.value = {};
}

const formRef = ref();
const handleCreate = async () => {
    await formRef.value.validate();
    create(formData.value);
    handleClose();
    reset();
}

const handleClose = () => {
    emit("update:modalStatus", false)
}
</script>

<style lang="scss" scoped></style>