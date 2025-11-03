<template>
  <t-drawer destroy-on-close :visible="drawerVisible" @close="closeDrawer"
            header="选择权限"
            size="medium"
            :close-btn="true">
    <t-tree
        ref="tree"
        :data="permissionTree"
        :keys="{value:'id',label:'title'}"
        v-model="checkedPermission"
        check-strictly
        line
        checkable
        hover
        expand-all
        value-mode="all"
        @change="()=>{console.log(checkedPermission)}"
    />
    <template #footer>
      <t-button @click="bindRolePermission">保存</t-button>
      <t-button theme="default" @click="closeDrawer">取消</t-button>
    </template>
  </t-drawer>
</template>
<script lang="ts" setup>
import {getLoginUserPermissionApi} from "@/api/permissionApi.ts";
import {onMounted, onUpdated, ref} from "vue";
import type {SysPermissionTreeListRes} from "@/types/SysPermission.ts";
import {bindRolePermissionApi, getRoleHavePermissionApi} from "@/api/roleApi.ts";
import {MessagePlugin} from "tdesign-vue-next";

//定义接收的参数
const props = defineProps<{
  drawerVisible: Boolean;
  roleId: string;
}>()

//定义事件
const emit = defineEmits<{
  (e: 'update:drawerVisible', value: Boolean): void;
}>()

onMounted(() => {
  getPermissionTree()
})

onUpdated(() => {
  getRoleHavePermission()
})

//权限树
const permissionTree = ref<SysPermissionTreeListRes[]>()
//已选择的权限树
const checkedPermission = ref<string[]>([]);

//获取权限树
function getPermissionTree() {
  getLoginUserPermissionApi().then(res => {
    permissionTree.value = res.payload.permissionTree
  })
}

//关闭抽屉
function closeDrawer() {
  emit('update:drawerVisible', false)
}

//获取角色已拥有的权限
function getRoleHavePermission() {
  getRoleHavePermissionApi(props.roleId).then(res => {
    checkedPermission.value = res.payload
  })
}

//保存权限
function bindRolePermission() {
  bindRolePermissionApi({
    roleId: props.roleId,
    permissionIdSet: checkedPermission.value
  }).then(() => {
    MessagePlugin.success('保存成功')
    emit('update:drawerVisible', false)
  })
}

</script>