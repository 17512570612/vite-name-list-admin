<template>
    <a-modal v-model:open="isOpen" :closable="false" :maskClosable="true" width="55%" cancel-text="取消" ok-text="确定" @cancel="handleClose" @ok="handleClose">
        <a-descriptions size="small" title="查看明细" bordered>
            <a-descriptions-item label="中文名" :span="2">{{ formData.cnName }}</a-descriptions-item>
            <a-descriptions-item label="英文名">{{ formData.enName }}</a-descriptions-item>
            <a-descriptions-item label="二位国别代码" :span="2">{{ formData.iso2countryCode }}</a-descriptions-item>
            <a-descriptions-item label="三位国别代码">{{ formData.iso3countryCode }}</a-descriptions-item>
            <a-descriptions-item label="原名" :span="2">{{ formData.oldName || '-' }}</a-descriptions-item>
            <a-descriptions-item label="别称1">{{ formData.aka1 || '-' }}</a-descriptions-item>
            <a-descriptions-item label="别称2" :span="2">{{ formData.aka2 || '-' }}</a-descriptions-item>
            <a-descriptions-item label="别称3">{{ formData.aka3 || '-' }}</a-descriptions-item>
            <a-descriptions-item label="所属洲" :span="2">{{ formData.continent || '-' }}</a-descriptions-item>
            <a-descriptions-item label="所属地域">{{ formData.region || '-' }}</a-descriptions-item>
            <a-descriptions-item label="风险国家类别" :span="2">
                <a-tag :color="formData.riskLevel === 'SANCTION' ? 'red' : formData.riskLevel === 'HIGH' ? 'orange' : 'blue'">
                    {{ formData.riskLevel === 'SANCTION' ? '制裁类' : formData.riskLevel === 'HIGH' ? '高风险' : '其他' }}
                </a-tag>
            </a-descriptions-item>
            <a-descriptions-item label="维护原因">{{ formData.maintenanceReasons || '-' }}</a-descriptions-item>
            <a-descriptions-item label="备注">{{ formData.remark || '-' }}</a-descriptions-item>
        </a-descriptions>
    </a-modal>
</template>

<script setup lang="ts">
const props = defineProps({
    isOpen: {
        type: Boolean,
        default: false
    },
    formData: {
        type: Object,
    }
})

const emit = defineEmits(['update:modal-status'])

const isOpen = ref(false);
const formData = ref();

watch(props, (newVal) => {
    isOpen.value = newVal.isOpen
    formData.value = newVal.formData
})

const handleClose = () => {
    emit('update:modal-status', false)
}

</script>

<style scoped></style>