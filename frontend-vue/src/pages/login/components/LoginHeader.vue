<template>
  <div class="h-14 absolute top-0 w-full backdrop-blur-3xl
    flex flex-row items-center px-6">
    <img class="w-10" src="@/assets/logo.png" alt="cloud"/>
    <p class="ml-2 text-2xl font-serif text-(--td-text-color-brand)">
      PurePanel
    </p>
    <div class="ml-auto">
      <a target="_blank" href="https://github.com/zyan1226/purepanel">
        <t-button variant="text">
          <template #icon>
            <t-icon name="logo-github"/>
          </template>
        </t-button>
      </a>
      <a target="_blank" href="https://github.com/zyan1226/purepanel">
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