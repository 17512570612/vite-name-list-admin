<template>
    <template v-if="route.children && route.path !== '/'">
        <a-sub-menu :key="route.name" :title="route.meta?.title">
            <template #icon>
                <component v-if="route.meta?.icon" :is="getIconComponent(route.meta?.icon)" />
            </template>
            <template v-for="children of route.children">
                <sub-menu :route="children" />
            </template>
        </a-sub-menu>
    </template>
    <template v-else>
        <a-menu-item :key="route.path" @click="handleMenuClick(route)">
            <component v-if="route.meta?.icon" :is="getIconComponent(route.meta?.icon)" />
            <span class="nav-text"> {{ route.meta?.title }} </span>
        </a-menu-item>
    </template>
</template>

<script lang="ts" setup>
import type { RouteRecordRaw } from "vue-router";
import * as AntDesignIcons from '@ant-design/icons-vue';

const { route } = defineProps<{
    route: RouteRecordRaw,
}>();

const router = useRouter();

const handleMenuClick = (route: RouteRecordRaw) => {
    router.push(route.path);
}

// 引入图标库
const allIconNames: string[] = Object.keys(AntDesignIcons);
const getIconComponent = (iconName: any) => {
    if (typeof iconName === 'string') {
        if (allIconNames.includes(iconName)) {
            return (AntDesignIcons as { [key: string]: any })[iconName];
        } else {
            return '';
        }
    } else {
        return iconName
    }
}

</script>

