<template>
    <a-modal width="50%" :closable="false" v-model:open="isOpen" ok-text="确定" cancel-text="取消" @cancel="handleCancel" @ok="handleOk">
        <template #title>
            <p class="mb-20">用户新增</p>
        </template>
        <a-form ref="formRef" :model="formState" :rules="rules" :label-col="{ span: 7 }">
            <a-row>
                <a-col span="12">
                    <a-form-item label="所属机构" name="institution">
                        <a-select v-model:value="formState.institution" placeholder="请选择机构">
                            <a-select-option value="shanghai">Zone one</a-select-option>
                            <a-select-option value="beijing">Zone two</a-select-option>
                        </a-select>
                    </a-form-item>
                </a-col>
                <a-col span="12">
                    <a-form-item label="账号" name="username">
                        <a-input v-model:value="formState.username" placeholder="请输入账号" />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row>
                <a-col span="12">
                    <a-form-item label="邮箱地址" name="email">
                        <a-input v-model:value="formState.email" placeholder="请输入邮箱地址" />
                    </a-form-item>
                </a-col>
                <a-col span="12">
                    <a-form-item label="密码" name="password">
                        <a-input v-model:value="formState.password" placeholder="请输入密码" />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row>
                <a-col span="12">
                    <a-form-item label="手机号码" name="phone">
                        <a-input v-model:value="formState.phone" placeholder="请输入手机号" />
                    </a-form-item>
                </a-col>
                <a-col span="12">
                    <a-form-item label="姓名" name="name">
                        <a-input v-model:value="formState.name" placeholder="请输入姓名" />
                    </a-form-item>
                </a-col>
            </a-row>
            <a-row>
                <a-col span="12">
                    <a-form-item label="电话号码" name="tel">
                        <a-input v-model:value="formState.tel" placeholder="请输入电话号码" />
                    </a-form-item>
                </a-col>
                <a-col span="12">
                    <a-form-item label="状态" name="status">
                        <a-radio-group v-model:value="formState.status">
                            <a-radio :value="1">启用</a-radio>
                            <a-radio :value="2">停用</a-radio>
                        </a-radio-group>
                    </a-form-item>
                </a-col>
            </a-row>
        </a-form>
    </a-modal>
</template>

<script setup lang="ts">
import type { Rule } from 'ant-design-vue/es/form';

const props = defineProps(['isOpen'])
const emit = defineEmits(['update:modalStatus'])

const isOpen = ref(false)

watch(props, (newProps) => {
    isOpen.value = newProps.isOpen
})

const formRef = ref();

const formState = reactive({
    username: '',
    institution: undefined,
    email: '',
    password: '',
    phone: '',
    name: '',
    tel: '',
    status: null
});

const rules: Record<string, Rule[]> = {
    institution: [{ required: true, message: '请选择所属机构', trigger: 'change' }],
    username: [{ required: true, message: '请输入账号', trigger: 'change' }],
    password: [{ required: true, message: '请输入密码', trigger: 'change' }],
    name: [{ required: true, message: '请输入姓名', trigger: 'change' }],
    status: [{ required: true, message: '请选择状态', trigger: 'change' }],
};

const resetForm = () => {
    formRef.value.resetFields();
};

const handleCancel = () => {
    resetForm();
    emit('update:modalStatus', false)
}

const handleOk = () => {
    formRef.value.validate().then(() => {
        console.log('values', formState, toRaw(formState));
    }).catch((error: any) => {
        console.log('error', error);
    });
}
</script>

<style scoped></style>