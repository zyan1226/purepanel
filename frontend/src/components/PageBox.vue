<template>
  <div v-motion :initial="{ opacity: 0, y: 60 }"
       :enter="{ opacity: 1, y: 0 }"
       :duration="250"
       class="h-full flex flex-col gap-4 p-6">
    <p v-if="title" class="text-3xl font-bold mt-14">{{ title }}</p>
    <slot/>
  </div>
</template>
<script setup lang="ts">
defineProps<{
  title?: string
}>()
</script>