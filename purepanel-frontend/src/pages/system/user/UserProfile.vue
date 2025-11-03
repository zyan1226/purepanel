<template>
  <page-box>
    <t-form class="w-1/2 self-center"
            @submit="handleSubmit"
            :rules="formRules"
            :data="formData">
      <t-form-item label="头像" name="email">
        <div class="flex flex-row gap-4">
          <t-avatar v-if="avatarUrl" size="120px" shape="round"
                    :image="avatarUrl"/>
          <t-avatar v-else size="120px" shape="round">
            {{ nameAvatar }}
          </t-avatar>
          <div class="flex flex-col gap-4 justify-center">
            <span>
              上传新头像
              <br/>
              <t-typography-text theme="secondary" class="!text-sm">
                理想的图片比例为 1:1，允许的最大文件大小为 2 MB。
              </t-typography-text>
            </span>
            <t-upload
                :files="[]"
                accept="image/*"
                :request-method="uploadAvatar"
            />
          </div>
        </div>
      </t-form-item>
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
      <t-form-item>
        <t-button type="submit">保存</t-button>
        <t-button class="!ml-4" theme="default" @click="router.back()">取消</t-button>
      </t-form-item>
    </t-form>
  </page-box>
</template>
<script setup lang="ts">
import PageBox from "@/components/PageBox.vue";
import {onMounted, reactive, ref} from "vue";
import type {SysUser} from "@/types/SysUser.ts";
import {useRouter} from "vue-router";
import type {AccountLoginRes} from "@/api/loginApi.ts";
import type {UploadFile} from "tdesign-vue-next/es/upload/type";
import {uploadFileApi} from "@/api/commonApi.ts";
import {getFileNetworkPath} from "@/utils/fileUtils.ts";
import {type FormProps, MessagePlugin} from "tdesign-vue-next";
import {selfEditApi} from "@/api/userApi.ts";

const router = useRouter()

let formData = reactive<SysUser>({});
//用户头像网络地址
const avatarUrl = ref<string>()
const nameAvatar = ref<string>()
let loginUserInfo = {} as AccountLoginRes;
onMounted(() => {
  //获取登录信息
  const localStorageLoginInfo = localStorage.getItem('loginInfo');
  if (localStorageLoginInfo) {
    loginUserInfo = JSON.parse(localStorageLoginInfo) as AccountLoginRes
    //获取用户名首字母
    nameAvatar.value = loginUserInfo.sysUser.name!.substring(0, 1)
    //头像
    if (loginUserInfo.sysUser.avatar) {
      avatarUrl.value = getFileNetworkPath(loginUserInfo.sysUser.avatar)
    }
    formData.id = loginUserInfo.sysUser.id
    formData.name = loginUserInfo.sysUser.name
    formData.account = loginUserInfo.sysUser.account
    formData.email = loginUserInfo.sysUser.email
    formData.phoneNumber = loginUserInfo.sysUser.phoneNumber
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
}

//上传头像
async function uploadAvatar(file: UploadFile) {
  const res = await uploadFileApi(file.raw!, 'avatar')
  formData.avatar = res.payload
  const fileNetworkPath = getFileNetworkPath(res.payload);
  avatarUrl.value = fileNetworkPath
  return {
    status: 'success',
    response: {
      url: fileNetworkPath
    }
  }
}

//定义表单提交事件
const handleSubmit: FormProps['onSubmit'] = ({validateResult}) => {
  if (validateResult !== true) {
    return;
  }
  //提交表单
  selfEditApi(formData).then(() => {
    MessagePlugin.success('保存成功');
    //更新localStorage
    loginUserInfo.sysUser.avatar = formData.avatar
    loginUserInfo.sysUser.account = formData.account
    loginUserInfo.sysUser.name = formData.name
    loginUserInfo.sysUser.email = formData.email
    loginUserInfo.sysUser.phoneNumber = formData.phoneNumber
    localStorage.setItem('loginInfo', JSON.stringify(loginUserInfo))
    //获取用户名首字母
    nameAvatar.value = loginUserInfo.sysUser.name!.substring(0, 1)
  })
}
</script>