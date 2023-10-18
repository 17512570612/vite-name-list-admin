<template>
  <a-watermark :content="content">
    <RouterView />
  </a-watermark>
</template>

<script lang="ts" setup>
const content = ref([`西力科技 Senior-Tech`]);
</script>

<style scoped></style>