<template>
  <RouterView/>
</template>

<script setup lang="ts">
import {RouterView} from 'vue-router'
import {setThemeColor} from "@/utils/dynamicThemes.ts";

const themeMode = localStorage.getItem('theme-mode')
if (themeMode === 'dark') {
  document.documentElement.setAttribute("theme-mode", "dark");
}
//默认主题
setThemeColor('blue')
</script>

<style scoped>

</style>
