<template>
  <div class="h-14 absolute top-0 w-full backdrop-blur-3xl
    flex flex-row items-center px-6">
    <img class="w-24" src="@/assets/logo-h.png" alt="logo">
    <div class="ml-auto">
      <a target="_blank" href="https://github.com/purepanel-projects">
        <t-button variant="text">
          <template #icon>
            <t-icon name="logo-github"/>
          </template>
        </t-button>
      </a>
      <a target="_blank" href="https://github.com/purepanel-projects">
        <t-button variant="text">
          <template #icon>
            <t-icon name="help-circle"/>
          </template>
        </t-button>
      </a>
    </div>
  </div>
</template>
<script setup lang="ts">
</script>