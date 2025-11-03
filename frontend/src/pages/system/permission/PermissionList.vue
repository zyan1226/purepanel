<template>
  <div class="h-full flex flex-col gap-4 p-6">
    <p class="text-3xl font-bold">菜单权限</p>
    <t-form @reset="resetSearchFormData" @submit="getPermissionTreeList" layout="inline">
      <t-form-item label-width="0">
        <t-input clearable v-model="searchFormData.title" placeholder="按标题模糊查询"/>
      </t-form-item>
      <t-form-item label-width="0" name="path">
        <t-input clearable v-model="searchFormData.path" class="!w-64" placeholder="按访问路径模糊查询"/>
      </t-form-item>
      <t-form-item label-width="0" name="type">
        <t-select clearable v-model="searchFormData.type" placeholder="按类型查询">
          <t-option label="菜单" value="0"/>
          <t-option label="页面元素" value="1"/>
          <t-option label="菜单组" value="2"/>
          <t-option label="接口" value="3"/>
        </t-select>
      </t-form-item>
      <div class="flex flex-row gap-4">
        <t-button type="submit">
          查询
        </t-button>
        <t-button theme="default" type="reset">
          重置
        </t-button>
      </div>
      <t-button class="!ml-auto" @click="handelAddClick">
        新增
      </t-button>
    </t-form>
    <t-enhanced-table table-layout="auto"
                      bordered
                      hover
                      ref="tableRef"
                      size="small"
                      :data="data"
                      row-key="id"
                      :columns="columns"
                      :tree="treeConfig"/>
    <permission-form-modal :old-data="oldData" v-model:dialog-visible="dialogVisible" :all-tree-list="data"
                           @submit-success="getPermissionTreeList"/>
  </div>
</template>

<script setup lang="tsx">
import {ref, onMounted, reactive, onUpdated} from 'vue';
import PermissionFormModal from "@/pages/system/permission/components/PermissionFormModal.vue";
import {allTreeListApi, deleteApi} from "@/api/permissionApi.ts";
import type {SysPermission, SysPermissionTreeListRes} from "@/types/SysPermission.ts";
import {
  type DropdownProps,
  type EnhancedTableInstanceFunctions,
  type EnhancedTableProps,
  MessagePlugin
} from "tdesign-vue-next";

const oldData = ref<SysPermission | null>();
const dialogVisible = ref(false);
const tableRef = ref<EnhancedTableInstanceFunctions>();
const data = ref<SysPermissionTreeListRes[]>();

onMounted(() => {
  getPermissionTreeList();
})

onUpdated(() => {
  tableRef.value!.expandAll()
})

//定义查询表单数据
const searchFormData = ref<SysPermission>({})

//重置查询表单数据
function resetSearchFormData() {
  searchFormData.value = {}
  getPermissionTreeList()
}

//定义表格列
const columns: EnhancedTableProps['columns'] = [
  {
    colKey: "title",
    title: "标题",
  },
  {
    title: "图标",
    cell: (h, {row}) => (
        <t-icon name={row.icon} size="16"/>
    )
  },
  {
    title: "类型",
    cell: (h, {row}) => (
        <span>
        {
          row.type === 0 ? '菜单' : row.type === 1 ? '页面元素' : '菜单组'
        }
      </span>
    )
  },
  {
    colKey: "path",
    title: "访问路径",
  },
  {
    colKey: "orderNo",
    title: "序号",
  },
  {
    colKey: "authCode",
    title: "授权编码",
  },
  {
    colKey: "updateTime",
    title: "更新时间",
  },
  {
    title: "操作",
    align: "center",
    cell: (h, {row}) => {
      const moreOptions: DropdownProps['options'] = [
        {
          content: '添加下级',
          onClick: () => {
            oldData.value = {
              pid: row.id,
            }
            dialogVisible.value = true;
          }
        }, {
          content: () => {
            return (
                <t-popconfirm content={"如存在下级，也将一并删除。确定删除吗？"} onConfirm={() => deleteById(row.id)}>
                  <div class="text-[var(--td-error-color)]">删除</div>
                </t-popconfirm>
            )
          }
        }
      ]
      return (
          <t-space>
            <t-link theme="primary" onClick={() => {
              oldData.value = {...row, type: row.type.toString()}
              dialogVisible.value = true
            }}>编辑
            </t-link>
            <t-dropdown hideAfterItemClick={false} options={moreOptions}>
              <t-link theme="primary">
                更多
                <t-icon name="chevron-down-s"/>
              </t-link>
            </t-dropdown>
          </t-space>
      )
    },
  }
]
//表配置
const treeConfig: EnhancedTableProps['tree'] = reactive({
  childrenKey: 'children',
  treeNodeColumnIndex: 0,
  indent: 40,
  expandTreeNodeOnClick: false,
  //Tdesgin 有BUG，这个配置不生效
  defaultExpandAll: true,
});

//获取表格数据
async function getPermissionTreeList() {
  const res = await allTreeListApi(searchFormData.value)
  data.value = res.payload;
}

//添加按钮点击事件
function handelAddClick() {
  oldData.value = null
  dialogVisible.value = true;
}

//删除
function deleteById(id: string) {
  deleteApi(id).then(() => {
    MessagePlugin.success('删除成功');
    getPermissionTreeList();
  })
}
</script>