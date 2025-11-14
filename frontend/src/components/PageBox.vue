<template>
  <motion.div :initial="{ y: 100 }"
              :animate="{ y: 0 }"
              :transition="{ duration: 0.3 }"
              class="h-full flex flex-col gap-4 p-6">
    <p v-if="title" class="text-3xl font-bold mt-14">{{ title }}</p>
    <slot/>
  </motion.div>
</template>
<script setup lang="ts">
defineProps<{
  title?: string
}>()
</script>