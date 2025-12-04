<template>
  <t-layout class="!bg-transparent">
    <t-aside class="max-w-min my-4 ml-4">
      <layout-aside-nav/>
    </t-aside>
    <t-layout class="h-screen !bg-transparent overflow-hidden">
      <layout-header/>
      <t-content class="bg-[var(--td-bg-color-container)]">
        <layout-content/>
      </t-content>
    </t-layout>
  </t-layout>
</template>
<script setup lang="ts">
import LayoutHeader from "@/pages/layout/components/LayoutHeader.vue";
import LayoutAsideNav from "@/pages/layout/components/LayoutAsideNav.vue";
import LayoutContent from "@/pages/layout/components/LayoutContent.vue";
</script>