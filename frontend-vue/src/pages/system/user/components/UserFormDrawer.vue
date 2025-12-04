<template>
  <t-form :rules="formRules" :data="formData" @submit="handleSubmit">
    <t-drawer destroy-on-close :visible="drawerVisible" @close="closeDrawer"
              header="填写用户信息"
              size="medium"
              :close-btn="true">
      <t-form-item label="名称" name="name">
        <t-input v-model="formData.name" clearable placeholder="请输入名称"/>
      </t-form-item>
      <t-form-item label="账号" name="account">
        <t-input v-model="formData.account" clearable placeholder="请输入账号"/>
      </t-form-item>
      <t-form-item label="联系电话" name="phoneNumber">
        <t-input v-model="formData.phoneNumber" clearable placeholder="请输入联系电话"/>
      </t-form-item>
      <t-form-item label="邮箱" name="email">
        <t-input v-model="formData.email" clearable placeholder="请输入邮箱"/>
      </t-form-item>
      <t-form-item label="头像" name="email">
        <t-upload
            v-model="avatarFile"
            accept="image/*"
            theme="image"
            :request-method="uploadAvatar"
        />
      </t-form-item>
      <t-form-item label="群组" name="group">
        <t-tree-select v-model="formData.groupIdList"
                       multiple
                       clearable
                       :data="groupTree"
                       :keys="{value:'id',label:'name'}"
                       :tree-props="{checkStrictly:true}"
                       placeholder="请选择群组"/>
      </t-form-item>
      <t-form-item label="角色" name="role">
        <t-select v-model="formData.roleIdList" multiple clearable :options="roleOptions" placeholder="请选择角色"/>
      </t-form-item>
      <t-form-item v-if="!props.oldData.id" label="密码" name="pwd">
        <t-input v-model="formData.pwd" clearable placeholder="请输入密码" type="password"/>
      </t-form-item>
      <t-form-item v-if="!props.oldData.id" label="确认密码" name="confirmPwd">
        <t-input v-model="formData.confirmPwd" clearable placeholder="请确认密码" type="password"/>
      </t-form-item>
      <t-form-item v-if="!props.oldData.id" label="已启用" name="status">
        <t-switch v-model="formData.status"/>
      </t-form-item>
      <template #footer>
        <t-button type="submit">保存</t-button>
        <t-button theme="default" @click="closeDrawer">取消</t-button>
      </template>
    </t-drawer>
  </t-form>
</template>
<script setup lang="ts">
import type {UserPageListRes, UserSaveReq} from "@/types/SysUser.ts";
import {type FormProps, MessagePlugin, type SelectProps} from "tdesign-vue-next";
import {onMounted, reactive, ref, watch} from "vue";
import {uploadFileApi} from "@/api/commonApi.ts";
import type {UploadFile} from "tdesign-vue-next/es/upload/type";
import {rolePageListApi} from "@/api/roleApi.ts";
import {groupAllTreeListApi} from "@/api/groupApi.ts";
import type {SysGroupTreeListRes} from "@/types/SysGroup.ts";
import {saveUserApi} from "@/api/userApi.ts";
import {getFileNetworkPath} from "@/utils/fileUtils.ts";

onMounted(() => {
  //获取角色列表
  getRoleList()
  //获取群组树
  getGroupTree()
})

//定义接收的参数
const props = defineProps<{
  drawerVisible: Boolean;
  oldData: UserPageListRes;
}>()

//定义事件
const emit = defineEmits<{
  (e: 'update:drawerVisible', value: Boolean): void;
  (e: 'submit-success'): void;
}>()

//监听props变化,如果有值,则是编辑模式,否则是新增模式
watch(() => props.oldData, () => {
  if (props.oldData.id) {
    // 编辑模式
    formData = reactive({...props.oldData})
    //处理角色列表
    if (!formData.roleIdList) {
      formData.roleIdList = [];
    }
    //处理群组列表
    if (!formData.groupIdList) {
      formData.groupIdList = [];
    }
    //处理头像文件
    if (formData.avatar) {
      avatarFile.value = [
        {
          url: getFileNetworkPath(formData.avatar)
        },
      ]
    } else {
      avatarFile.value = []
    }
  } else {
    // 新增模式
    formData = reactive({
      status: true,
    });
    avatarFile.value = []
  }
})

//定义表单校验规则
const formRules: FormProps['rules'] = {
  name: [
    {
      required: true,
      message: '请输入名称',
    },
  ],
  account: [
    {
      required: true,
      message: '请输入账号',
    },
  ],
  pwd: [
    {
      required: !props.oldData.id,
      message: '请输入新密码',
    },
    {
      min: 6,
      message: '密码至少 6 位'
    }
  ],
  confirmPwd: [
    {
      required: !props.oldData.id,
      message: '请确认密码',
    },
    {
      validator: (value) => {
        return value === formData.pwd
      },
      message: '两次输入密码不一致',
    }
  ],
}
//定义表单数据
let formData = reactive<UserSaveReq>({});
//定义头像文件
const avatarFile = ref();
//定义角色选择框列表
const roleOptions = ref<SelectProps['options']>([]);
const groupTree = ref<SysGroupTreeListRes[]>([]);

//关闭抽屉
function closeDrawer() {
  emit('update:drawerVisible', false)
}

//获取角色列表
function getRoleList() {
  rolePageListApi({
    current: 1,
    size: Number.MAX_SAFE_INTEGER,
  }).then(res => {
    //处理角色选择框列表
    res.payload.records.forEach(item => {
      roleOptions.value!.push({
        label: item.name,
        value: item.id,
      })
    })
  })
}

//获取群组树
function getGroupTree() {
  groupAllTreeListApi().then(res => {
    groupTree.value = res.payload;
  })
}

//上传头像
async function uploadAvatar(file: UploadFile) {
  const res = await uploadFileApi(file.raw!,'avatar');
  formData.avatar = res.payload;
  return {
    status: 'success',
    response: {
      url: getFileNetworkPath(res.payload)
    }
  }
}

//定义表单提交事件
const handleSubmit: FormProps['onSubmit'] = ({validateResult}) => {
  if (validateResult !== true) {
    return;
  }
  //提交表单
  saveUserApi(formData).then(() => {
    MessagePlugin.success('保存成功');
    emit('update:drawerVisible', false)
    emit('submit-success')
  })
}
</script>