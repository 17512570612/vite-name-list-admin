<template>
    <a-menu theme="dark" mode="inline" v-model:selectedKeys="state.selectedKeys" :open-keys="state.openKeys" @openChange="onOpenChange" @select="onSelect">
        <sub-menu v-for="route of routes" :key="route.name" :route="route" />
    </a-menu>
</template>
  
<script setup lang="ts">
import SubMenu from './SubMenu.vue'

const router = useRouter();
const routes = router.getRoutes().filter(item => item.meta.parent);

type Key = string | number

const state = reactive({
    rootSubmenuKeys: routes.map(item => item.name),
    openKeys: [] as Key[],
    selectedKeys: [] as string[],
});

const onOpenChange = (Keys: Key[]) => {
    const latestOpenKey = Keys.find(key => !state.openKeys.includes(key));
    if (latestOpenKey !== undefined && state.rootSubmenuKeys.includes(latestOpenKey as string)) {
        state.openKeys = [latestOpenKey];
    } else {
        state.openKeys = Keys;
    }
    saveMenuState();
};

const onSelect = ({ key }: { key: Key }) => {
    if (typeof key === 'string') {
        state.selectedKeys = [key];
        saveMenuState();
    }
};

const saveMenuState = () => {
    sessionStorage.setItem('MENU_STATE', JSON.stringify({ openKeys: state.openKeys, selectedKeys: state.selectedKeys }));
};

onMounted(() => {
    const storedMenuState = sessionStorage.getItem('MENU_STATE');
    if (storedMenuState) {
        const { openKeys, selectedKeys } = JSON.parse(storedMenuState);
        state.openKeys = openKeys;
        state.selectedKeys = selectedKeys;
    }
});
</script>
<style scoped></style>
  