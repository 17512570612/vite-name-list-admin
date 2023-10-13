<template>
    <a-breadcrumb class="breadcrumb">
        <a-breadcrumb-item v-for="(item, index) of routes" :key="index">
            <span class="menu-name" v-if="item.path === '/'" @click="useGoHome()">
                {{ item.meta?.title }}
            </span>
            <span v-else>{{ item.meta?.title }}</span>
        </a-breadcrumb-item>
    </a-breadcrumb>
</template>

<script lang="ts" setup>
import type { RouteRecordRaw } from 'vue-router';

const route = useRoute();
const router = useRouter();

const homeRoute: RouteRecordRaw = {
    path: '/',
    meta: { title: '首页' },
    children: []
};
const routes = ref<RouteRecordRaw[]>([]);

const updateRoutes = (newRoutes: RouteRecordRaw[]): void => {
    const hasQueryListRoute = newRoutes.some(v => v.name === 'ScanDetails');
    if (newRoutes.length > 0 && newRoutes[0].path !== '/') {
        routes.value = [homeRoute, ...newRoutes];
    } else {
        routes.value = [homeRoute, ...(hasQueryListRoute ? newRoutes.filter(v => v.name === 'ScanDetails') : [])];
    }
};

const useGoHome = () => {
    router.push('/');
}

watch(() => route.matched, updateRoutes);

onMounted(() => {
    updateRoutes(route.matched);
});
</script>

<style lang="scss" scoped>
.breadcrumb {
    height: 40px;
    line-height: 40px;
    padding: 0 24px;
    font-size: 14px;
}

.menu-name {
    cursor: pointer;
    color: rgba(0, 0, 0, 0.45);
}

.menu-name:hover {
    color: #1890ff;
}
</style>
