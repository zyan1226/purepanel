<template>
  <page-box title="用户管理">
    <t-form @reset="resetSearchFormData" @submit="getUserPageList" layout="inline">
      <t-form-item label-width="0">
        <t-input clearable v-model="searchFormData.name" placeholder="按名称模糊查询"/>
      </t-form-item>
      <t-form-item label-width="0" name="path">
        <t-input clearable v-model="searchFormData.account" placeholder="按账号模糊查询"/>
      </t-form-item>
      <div class="flex flex-row gap-4">
        <t-button type="submit">
          查询
        </t-button>
        <t-button theme="default" type="reset">
          重置
        </t-button>
      </div>
      <div v-auth="'SYS_USER_ADD'" class="flex flex-row gap-4 ml-auto">
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
  <change-pwd-modal v-model:dialog-visible="childrenComponentVisible.changePwdModal"
                    :user-id="currentOperateUserInfo.id"/>
  <user-form-drawer v-model:drawer-visible="childrenComponentVisible.formDrawer"
                    :old-data="currentOperateUserInfo"
                    @submit-success="getUserPageList"/>
</template>
<script setup lang="tsx">
import PageBox from "@/components/PageBox.vue";
import {onMounted, ref} from "vue";
import {deleteUserApi, saveUserApi, userPageListApi} from "@/api/userApi.ts";
import type {UserPageListRes} from "@/types/SysUser.ts";
import {type DropdownProps, type EnhancedTableProps, MessagePlugin, type TableProps} from "tdesign-vue-next";
import ChangePwdModal from "@/components/ChangePwdModal.vue";
import UserFormDrawer from "@/pages/system/user/components/UserFormDrawer.vue";

//子组件显隐控制
const childrenComponentVisible = ref({
  changePwdModal: false,
  formDrawer: false,
});
//当前操作的用户信息
const currentOperateUserInfo = ref<UserPageListRes>({});
//表格数据定义
const data = ref<UserPageListRes[]>();
//查询表单数据
const searchFormData = ref<{
  name?: string,
  account?: string,
}>({})

onMounted(() => {
  getUserPageList()
})

//重置查询表单数据
function resetSearchFormData() {
  searchFormData.value = {}
  getUserPageList()
}

//处理新增按钮点击事件
function handleAdd() {
  childrenComponentVisible.value.formDrawer = true
  currentOperateUserInfo.value = {}
}

//定义表格列
const columns: EnhancedTableProps<UserPageListRes>['columns'] = [
  {
    colKey: "account",
    title: "账号",
  },
  {
    colKey: "name",
    title: "名称",
  },
  {
    colKey: "phoneNumber",
    title: "联系电话",
  },
  {
    colKey: "email",
    title: "邮箱",
  },
  {
    colKey: "groupNames",
    title: "群组",
  },
  {
    colKey: "roleNames",
    title: "角色",
  },
  {
    colKey: "status",
    title: "状态",
    cell: (h, {row}) => {
      if (row.status) {
        return <t-tag theme="success">正常</t-tag>
      } else {
        return <t-tag theme="danger">禁用</t-tag>
      }
    }
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
          content: '重置密码',
          onClick: () => {
            currentOperateUserInfo.value.id = row.id;
            childrenComponentVisible.value.changePwdModal = true;
          }
        },
        {
          content: () => {
            return (
                <t-popconfirm content={"确定更改状态吗？"}
                              onConfirm={() => changeUserStatus(row.status!, row.id!)}>
                  <div>{row.status ? '禁用' : '启用'}</div>
                </t-popconfirm>
            )
          }
        },
        {
          content: () => {
            return (
                <t-popconfirm content={"确定删除吗？"}
                              onConfirm={() => deleteUser(row.id!)}>
                  <div class="text-[var(--td-error-color)]">删除</div>
                </t-popconfirm>
            )
          }
        },
      ]
      return (<t-space>
        <t-link theme="primary" onClick={() => {
          childrenComponentVisible.value.formDrawer = true
          currentOperateUserInfo.value = row;
        }}>编辑
        </t-link>
        <t-dropdown hideAfterItemClick={false} options={moreOptions}>
          <t-link theme="primary">
            更多
            <t-icon name="chevron-down-s"/>
          </t-link>
        </t-dropdown>
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
  getUserPageList();
};

//获取表格数据
function getUserPageList() {
  userPageListApi({
    current: pagination.value!.current!,
    size: pagination.value!.pageSize!,
    name: searchFormData.value.name,
    account: searchFormData.value.account,
  }).then(res => {
    data.value = res.payload.records
    pagination.value!.total = res.payload.total
  })
}

//修改用户状态
function changeUserStatus(currentStatus: boolean, id: string) {
  saveUserApi({
    id: id,
    status: !currentStatus,
  }).then(() => {
    MessagePlugin.success('修改成功');
    getUserPageList()
  })
}

//删除用户
function deleteUser(id: string) {
  deleteUserApi(id).then(() => {
    MessagePlugin.success('删除成功');
    getUserPageList()
  })
}
</script>