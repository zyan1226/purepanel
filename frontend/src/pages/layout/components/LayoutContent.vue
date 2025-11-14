<template>
  <RouterView v-motion :initial="{ opacity: 0, y: 50 }"
              :enter="{ opacity: 1, y: 0 }"
              :duration="250"/>
</template>
<script setup lang="ts">
import {RouterView} from 'vue-router'
</script>

<style scoped>

</style>