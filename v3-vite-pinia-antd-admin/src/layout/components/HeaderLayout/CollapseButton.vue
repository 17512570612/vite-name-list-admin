<template>
    <div class="flex aic">
        <menu-unfold-outlined v-if="appStore.collapsed" class="trigger f20" @click="appStore.toggleCollapsed()" />
        <menu-fold-outlined v-else class="trigger f20" @click="appStore.toggleCollapsed()" />
        <Breadcrumb />
    </div>
</template>
  
<script lang="ts" setup>
import { useAppStore } from "@/store"
import { MenuUnfoldOutlined, MenuFoldOutlined } from '@ant-design/icons-vue';
import Breadcrumb from '../Breadcrumb.vue';

const appStore = useAppStore();
</script>

<style scoped></style>
  