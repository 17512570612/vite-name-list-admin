<template>
  <a-watermark :font="{ color: 'rgba(0,0,0,0.1)' }" :content="content">
    <RouterView />
  </a-watermark>
</template>

<script lang="ts" setup>
const content = `西力科技 Senior-Tech`;
</script>

<style scoped></style>