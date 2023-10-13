<template>
    <a-modal v-model:open="open" width="320px" :mask="false" :closable="false" :footer="null" @cancel="handleModalClose">
        <a-radio-group v-model:value="iconStyle" @change="selectIconStyle">
            <a-radio-button :value="0">线框风格</a-radio-button>
            <a-radio-button :value="1">实底风格</a-radio-button>
            <a-radio-button :value="2">双色风格</a-radio-button>
        </a-radio-group>
        <ul class="iconbox flex aic">
            <li class="icon" v-for="item in filteredIcons" :key="item" @click="selectIcon(item)">
                <component :is="item"></component>
            </li>
        </ul>
    </a-modal>
</template>

<script lang="ts" setup>
import icons from '@/assets/icons.json';
const props = defineProps({
    open: Boolean
});

const emit = defineEmits(["update:modalShow", "update:iconValue"]);

const open = ref<boolean>(false);

watch(() => props.open, (val) => {
    open.value = val;
});

const handleModalClose = () => {
    emit('update:modalShow', false);
};

const iconStyle = ref<number>(0);
const iconStyles = {
    0: 'Outlined',
    1: 'Filled',
    2: 'TwoTone',
};

const filteredIcons = computed(() => {
    const style = iconStyles[iconStyle.value as keyof typeof iconStyles];
    return icons.filter((item: string) => item.includes(style));
});

const selectIconStyle = (val: any) => {
    iconStyle.value = val.target.value;
}

const selectIcon = (val: string) => {
    emit('update:iconValue', val);
    handleModalClose();
}
</script>

<style lang="scss" scoped>
.iconbox {
    height: 300px;
    flex-wrap: wrap;
    overflow: hidden;
    overflow-y: auto;
    margin-top: 10px;
    padding: 10px;
    box-sizing: border-box;

    .icon {
        width: 40px;
        height: 40px;
        font-size: 15px;
        line-height: 40px;
        text-align: center;
    }

    .icon:hover {
        box-shadow: 0px 0px 10px #ccc;
    }
}

.iconbox::-webkit-scrollbar {
    width: 0;
}
</style>