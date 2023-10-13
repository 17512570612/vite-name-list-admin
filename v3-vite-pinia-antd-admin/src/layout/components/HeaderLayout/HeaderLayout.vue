<template>
    <a-layout-header>
        <div class="layout-header flex aic jc-sb">
            <div class="header-collapsed">
                <CollapseButton />
            </div>
            <div class="user-options">
                <UserDropdown />
            </div>
        </div>
    </a-layout-header>
</template>
  
<script setup lang="ts">
import CollapseButton from './CollapseButton.vue';
import UserDropdown from './UserDropdown.vue';

</script>
  
<style scoped>
.layout-header {
    width: 100%;
    padding: 0 24px;
    box-sizing: border-box;
    background-color: #fff;
}

.ant-layout-header {
    padding: 0;
}

.trigger {
    font-size: 18px;
    line-height: 64px;
    cursor: pointer;
    transition: color 0.3s;
}

.trigger:hover {
    color: #1890ff;
}
</style>
  