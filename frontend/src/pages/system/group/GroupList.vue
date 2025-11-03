<template>
  <div class="h-full flex flex-col gap-4 p-6">
    <p class="text-3xl font-bold">群组管理</p>
    <t-form @reset="resetSearchFormData" @submit="getGroupTreeList" layout="inline">
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
                      ref="tableRef"
                      size="small"
                      :data="data"
                      row-key="id"
                      :columns="columns"
                      :tree="treeConfig"/>
    <group-form-modal v-model:dialog-visible="formModalVisible"
                      :all-tree-list="data"
                      :old-data="oldData"
                      @submit-success="getGroupTreeList"/>
  </div>
</template>

<script setup lang="tsx">
import {onMounted, onUpdated, reactive, ref} from "vue";
import {groupAllTreeListApi, groupDeleteApi} from "@/api/groupApi.ts";
import {
  type DropdownProps,
  type EnhancedTableInstanceFunctions,
  type EnhancedTableProps,
  MessagePlugin
} from "tdesign-vue-next";
import type {SysGroup, SysGroupTreeListRes} from "@/types/SysGroup.ts";
import GroupFormModal from "@/pages/system/group/components/GroupFormModal.vue";

const tableRef = ref<EnhancedTableInstanceFunctions>();
const formModalVisible = ref(false)
const oldData = ref<SysGroup | null>();

onMounted(() => {
  getGroupTreeList()
})

onUpdated(() => {
  tableRef.value!.expandAll()
})

//查询表单数据
const searchFormData = ref<{
  name?: string,
}>({})

//重置查询表单数据
function resetSearchFormData() {
  searchFormData.value = {}
}

//定义表格列
const columns: EnhancedTableProps<SysGroupTreeListRes>['columns'] = [
  {
    colKey: "name",
    title: "名称",
  },
  {
    colKey: "orderNo",
    title: "序号",
  },
  {
    colKey: "createTime",
    title: "创建时间",
  },
  {
    colKey: "updateTime",
    title: "更新时间",
  },
  {
    colKey: "link",
    title: "操作",
    align: "center",
    cell: (h, {row}) => {
      const moreOptions: DropdownProps['options'] = [
        {
          content: '添加下级',
          onClick: () => {
            oldData.value = {
              pid: row.id,
              orderNo: 0,
            }
            formModalVisible.value = true;
          }
        },
        {
          content: () => {
            return (
                <t-popconfirm content={"如存在下级，也将一并删除。确定删除吗？"} onConfirm={() => handleDelete(row.id!)}>
                  <div class="text-[var(--td-error-color)]">删除</div>
                </t-popconfirm>
            )
          }
        }
      ]
      return (
          <t-space>
            <t-link theme="primary" onClick={() => {
              oldData.value = {...row}
              formModalVisible.value = true
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
    }
  }
]
//定义表格数据
const data = ref<SysGroupTreeListRes[]>();

//获取表格数据
async function getGroupTreeList() {
  const res = await groupAllTreeListApi(searchFormData.value)
  data.value = res.payload;
}

//表配置
const treeConfig: EnhancedTableProps['tree'] = reactive({
  childrenKey: 'children',
  treeNodeColumnIndex: 0,
  indent: 40,
  expandTreeNodeOnClick: false,
  //Tdesgin 有BUG，这个配置不生效
  defaultExpandAll: true,
});

//处理新增点击
function handleAdd() {
  formModalVisible.value = true
  oldData.value = null
}

//处理删除
function handleDelete(id: string) {
  groupDeleteApi(id).then(() => {
    MessagePlugin.success('删除成功');
    getGroupTreeList()
  })
}
</script>