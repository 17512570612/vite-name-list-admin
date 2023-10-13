<template>
    <a-menu theme="dark" mode="inline" v-model:selectedKeys="state.selectedKeys" :open-keys="state.openKeys" @openChange="onOpenChange">
        <sub-menu v-for="route of routes" :key="route.name" :route="route" />
    </a-menu>
</template>
  
<script setup lang="ts">
import SubMenu from './SubMenu.vue'

const router = useRouter();
const routes = router.getRoutes().filter(item => item.meta.parent)

type Key = string | number

const state = reactive({
    rootSubmenuKeys: routes.map(item => item.name),
    openKeys: <any>[],
    selectedKeys: [],
});

const onOpenChange = (Keys: Key[]) => {
    const latestOpenKey = Keys.find(key => state.openKeys.indexOf(key) === -1);
    if (state.rootSubmenuKeys.indexOf(latestOpenKey as string) === -1) {
        state.openKeys = Keys;
    } else {
        state.openKeys = latestOpenKey ? [latestOpenKey] : [];
    }
};
</script>
<style scoped></style>
  