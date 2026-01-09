<template>
  <div class="flex flex-col gap-4 m-4 p-6 bg-(--td-bg-color-container) rounded-xl">
    <p v-if="title" class="text-3xl font-bold">{{ title }}</p>
    <slot/>
  </div>
</template>
<script setup lang="ts">
defineProps<{
  title?: string
}>()
</script>