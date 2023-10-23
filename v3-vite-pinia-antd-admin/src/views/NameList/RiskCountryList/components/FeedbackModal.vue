<template>
    <a-modal :open="isOpen" :closable="false" :maskClosable="true" width="60%" @cancel="handleClose">

    </a-modal>
</template>

<script setup lang="ts">
const isOpen = ref();

const handleClose = () => { }
</script>

<style scoped></style>