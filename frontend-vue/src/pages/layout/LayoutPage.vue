<template>
  <t-layout class="bg-transparent!">
    <t-aside class="max-w-min">
      <layout-aside-nav/>
    </t-aside>
    <t-layout class="h-screen overflow-hidden">
      <layout-header/>
      <t-content>
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