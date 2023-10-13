<template>
    <a-layout-sider width="250" v-model:collapsed="appStore.collapsed" :trigger="null" collapsible>
        <div class="logo"><img class="logo-icon" :src="!appStore.collapsed ? BigLogo : SmallLogo" alt="" /></div>
        <Menu />
    </a-layout-sider>
</template>

<script setup lang="ts">
import { useAppStore } from "@/store"
import Menu from "./Menu.vue"
import BigLogo from '../../assets/image/logo.svg';
import SmallLogo from '../../assets/image/logo.jpg';
const appStore = useAppStore();
</script>

<style scoped>
.logo {
    height: 64px;
    display: flex;
    align-items: center;
}

.logo-icon {
    height: 25px;
    padding-left: 24px;

}
</style>