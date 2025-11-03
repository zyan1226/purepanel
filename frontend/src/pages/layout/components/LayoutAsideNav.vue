<template>
  <t-menu class="!bg-[var(--td-bg-color-container-hover)] shadow-xl rounded-xl border
  dark:border-[var(--td-brand-color-light)] border-[var(--td-brand-color-focus)]" :value="activeMenu"
          :collapsed="useAsideCollapsedStore().asideCollapsed"
          :expandMutex="true">
    <div class="w-full flex flex-row items-center justify-center mb-4">
      <p class="text-4xl">💧</p>
      <p v-if="!useAsideCollapsedStore().asideCollapsed" class="text-2xl font-serif mr-6">PurePanel</p>
    </div>
    <!--遍历菜单树-->
    <template v-for="menu in menuTree" :key="menu.id">
      <!--类型为菜单组-->
      <t-menu-group v-if="menu.type == '2'" :title="menu.title">
        <template v-for="menuSub in menu.children" :key="menuSub.id">
          <t-menu-item v-if="menuSub.children.length === 0" :value="menuSub.path" :router-link="true"
                       :to="menuSub.path">
            <template #icon>
              <t-icon :name="menuSub.icon"/>
              <template v-if="useAsideCollapsedStore().asideCollapsed">
                &nbsp;
              </template>
            </template>
            {{ menuSub.title }}
          </t-menu-item>
          <t-submenu v-else :title="menuSub.title">
            <template #icon>
              <t-icon :name="menuSub.icon"/>
              <template v-if="useAsideCollapsedStore().asideCollapsed">
                &nbsp;
              </template>
            </template>
            <t-menu-item v-for="menuItem in menuSub.children" :key="menuItem.id" :router-link="true"
                         :value="menuItem.path"
                         :to="menuItem.path">
              <template #icon>
                <t-icon :name="menuItem.icon"/>
              </template>
              <template v-if="useAsideCollapsedStore().asideCollapsed">
                &nbsp;
              </template>
              {{ menuItem.title }}
            </t-menu-item>
          </t-submenu>
        </template>
      </t-menu-group>
      <!--菜单子节点不为空-->
      <t-submenu v-else-if="menu.children.length > 0" :title="menu.title">
        <template #icon>
          <t-icon :name="menu.icon"/>
        </template>
        <t-menu-item v-for="menuItem in menu.children" :key="menuItem.id" :router-link="true" :value="menuItem.path"
                     :to="menuItem.path">
          <template #icon>
            <t-icon :name="menuItem.icon"/>
            <template v-if="useAsideCollapsedStore().asideCollapsed">
              &nbsp;
            </template>
          </template>
          {{ menuItem.title }}
        </t-menu-item>
      </t-submenu>
      <!--菜单子节点为空-->
      <t-menu-item v-else :value="menu.path" :router-link="true"
                   :to="menu.path">
        <template #icon>
          <t-icon :name="menu.icon"/>
          <template v-if="useAsideCollapsedStore().asideCollapsed">
            &nbsp;
          </template>
        </template>
        {{ menu.title }}
      </t-menu-item>
    </template>
    <template #operations>

    </template>
  </t-menu>
</template>
<script setup lang="ts">
import {useAsideCollapsedStore} from "@/stores/asideCollapsedStore.ts";
import {computed, onMounted, ref} from "vue";
import {getLoginUserPermissionApi} from "@/api/permissionApi.ts";
import type {SysPermissionTreeListRes} from "@/types/SysPermission.ts";
import {usePageElementPermissionStore} from "@/stores/pageElementPermissionStore.ts";
import {useRoute} from "vue-router";

const route = useRoute()
const activeMenu = computed(() => route.path)
const menuTree = ref<SysPermissionTreeListRes[]>([])

onMounted(() => {
  getLoginUserPermission()
})

function getLoginUserPermission() {
  getLoginUserPermissionApi().then((res) => {
    menuTree.value = res.payload.menuTree;
    usePageElementPermissionStore().update(res.payload.pageElementList)
  })
}
</script>