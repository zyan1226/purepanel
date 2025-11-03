<template>
  <page-box title="角色管理">
    <t-form @reset="resetSearchFormData" @submit="getRolePageList" layout="inline">
      <t-form-item label-width="0">
        <t-input clearable v-model="searchFormData.name" placeholder="按名称模糊查询"/>
      </t-form-item>
      <div class="flex flex-row gap-4">
        <t-button type="submit">
          查询
        </t-button>
        <t-button theme="default" type="reset">
          重置
        </t-button>
      </div>
      <div class="flex flex-row gap-4 ml-auto">
        <t-button @click="handleAdd">
          新增
        </t-button>
      </div>
    </t-form>
    <t-enhanced-table table-layout="auto"
                      bordered
                      hover
                      :pagination="pagination"
                      @page-change="onPageChange"
                      ref="tableRef"
                      size="small"
                      :data="data"
                      row-key="id"
                      :columns="columns"/>
  </page-box>
  <!--角色表单-->
  <t-form :rules="formRules" :data="formData" @submit="handleFormSubmit">
    <t-dialog destroy-on-close :visible="formVisible" :close-btn="true" :on-close="closeFormDialog">
      <template #header>填写角色信息</template>
      <template #body>
        <t-form-item label="名称" name="name">
          <t-input v-model="formData.name" placeholder="请输入名称"/>
        </t-form-item>
      </template>
      <template #footer>
        <t-button theme="primary" type="submit">保存</t-button>
      </template>
    </t-dialog>
  </t-form>
  <!--授权抽屉-->
  <role-authorize-drawer v-model:drawer-visible="authorizeDrawerVisible" :role-id="currentAuthorizeRoleId"/>
</template>
<script setup lang="tsx">
import PageBox from "@/components/PageBox.vue";
import {onMounted, reactive, ref} from "vue";
import {roleDeleteApi, rolePageListApi, roleSaveApi} from "@/api/roleApi.ts";
import type {SysRole} from "@/types/SysRole.ts";
import {
  type DialogProps,
  type EnhancedTableProps,
  type FormProps,
  MessagePlugin,
  type TableProps,
} from "tdesign-vue-next";
import RoleAuthorizeDrawer from "@/pages/system/role/components/RoleAuthorizeDrawer.vue";

onMounted(() => {
  getRolePageList()
})

//查询表单数据
const searchFormData = ref<{
  name?: string,
}>({})

//重置查询表单数据
function resetSearchFormData() {
  searchFormData.value = {}
  getRolePageList()
}

//表格数据定义
const data = ref<SysRole[]>();
//定义表格列
const columns: EnhancedTableProps<SysRole>['columns'] = [
  {
    colKey: "name",
    title: "名称",
  },
  {
    colKey: "createTime",
    title: "创建时间",
  }
  ,
  {
    colKey: "updateTime",
    title: "更新时间",
  }, {
    colKey: "link",
    title: "操作",
    align: "center",
    cell: (h, {row}) => {
      return (<t-space>
        <t-link theme="primary" onClick={() => {
          authorizeDrawerVisible.value = true
          currentAuthorizeRoleId.value = row.id!
        }}>授权
        </t-link>
        <t-link theme="primary" onClick={() => {
          formData.id = row.id
          formData.name = row.name
          formVisible.value = true
        }}>编辑
        </t-link>
        <t-popconfirm content={"确定删除吗？"} onConfirm={() => handleDelete(row.id!)}>
          <t-link theme="danger">删除</t-link>
        </t-popconfirm>
      </t-space>)
    }
  }
]

//分页对象
const pagination = ref<TableProps['pagination']>({
  pageSize: 20,
  total: 0,
  current: 1,
  showJumper: true,
});

//分页变化事件
const onPageChange: TableProps['onPageChange'] = (pageInfo) => {
  pagination.value!.current = pageInfo.current;
  pagination.value!.pageSize = pageInfo.pageSize;
  getRolePageList();
};

//获取表格数据
function getRolePageList() {
  rolePageListApi({
    name: searchFormData.value.name,
    current: pagination.value!.current!,
    size: pagination.value!.pageSize!,
  }).then(res => {
    data.value = res.payload.records
    pagination.value!.total = res.payload.total
  })
}

//新增按钮点击事件
function handleAdd() {
  formData.id = undefined
  formData.name = undefined
  formVisible.value = true
}

//新增/编辑表单显隐
const formVisible = ref(false)
//定义表单数据
const formData = reactive<SysRole>({});
//定义表单校验规则
const formRules: FormProps['rules'] = {
  name: [
    {
      required: true,
      message: '请输入名称',
    },
  ],
}
//定义表单提交事件
const handleFormSubmit: FormProps['onSubmit'] = ({validateResult}) => {
  if (validateResult !== true) {
    return;
  }
  roleSaveApi(formData).then(() => {
    MessagePlugin.success('保存成功')
    getRolePageList()
    formVisible.value = false
  })
};
//关闭弹窗
const closeFormDialog: DialogProps['onClose'] = () => {
  formVisible.value = false
};

//删除按钮点击事件
function handleDelete(id: string) {
  roleDeleteApi(id).then(() => {
    MessagePlugin.success('删除成功')
    getRolePageList()
  })
}

//授权抽屉显隐
const authorizeDrawerVisible = ref(false)
//当前操作授权的角色id
const currentAuthorizeRoleId = ref<string>('')
</script>