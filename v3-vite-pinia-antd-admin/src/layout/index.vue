<template>
    <a-layout has-sider>
        <LayoutSider />
        <a-layout>
            <LayoutHeader />
            <LayoutContent />
        </a-layout>
    </a-layout>
</template>

<script setup lang="ts">
import LayoutSider from './components/Sider.vue';
import LayoutHeader from './components/Header.vue';
import LayoutContent from './components/Content.vue';
</script>

<style>
.ant-layout {
    min-height: 100vh;
}
</style>
  