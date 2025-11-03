<template>
  <div class="ml-26 gap-6 flex flex-col h-screen justify-center w-[410px]">
    <div class="p-10 flex flex-col gap-6 rounded-xl backdrop-blur-3xl">
      <span class="text-4xl/14 font-bold">登录到<br/>PurePanel</span>
      <t-form :data="formData" :label-width="0" @submit="handleSubmit" :rules="formRules">
        <t-form-item name="account">
          <t-input size="large" v-model="formData.account" clearable placeholder="请输入账号">
            <template #prefix-icon>
              <user-icon/>
            </template>
          </t-input>
        </t-form-item>
        <t-form-item name="pwd">
          <t-input size="large" v-model="formData.pwd" type="password" clearable placeholder="请输入密码">
            <template #prefix-icon>
              <lock-on-icon/>
            </template>
          </t-input>
        </t-form-item>
        <t-form-item name="captcha">
          <div class="flex flex-row items-center w-full">
            <t-input class="!w-54" size="large" v-model="formData.captcha" clearable placeholder="请输入验证码">
              <template #prefix-icon>
                <VerifiedIcon/>
              </template>
            </t-input>
            <img @click="getCaptchaData" class="h-[var(--td-comp-size-l)] cursor-pointer rounded-md ml-auto"
                 :src="captchaRes.captchaBase64" alt=""/>
          </div>
        </t-form-item>
        <t-form-item>
          <t-button size="large" theme="primary" type="submit" block>登录</t-button>
        </t-form-item>
      </t-form>
    </div>
    <t-typography-text class="absolute bottom-10" theme="secondary">Copyright @ 2021-2025 PurePanel. All Rights
      Reserved
    </t-typography-text>
  </div>
</template>
<script setup lang="ts">
import {LockOnIcon, UserIcon, VerifiedIcon} from "tdesign-icons-vue-next";
import {type FormProps, MessagePlugin} from "tdesign-vue-next";
import {onMounted, reactive, ref} from "vue";
import {useRouter} from "vue-router";
import {type AccountLoginReq, getCaptchaApi, type GetCaptchaRes, loginApi} from '@/api/loginApi.ts'

const router = useRouter()

onMounted(() => {
  getCaptchaData()
})

//定义表单校验规则
const formRules: FormProps['rules'] = {
  account: [{
    required: true,
    message: '请输入账号'
  }],
  pwd: [
    {
      required: true,
      message: '请输入密码'
    }, {
      min: 6,
      message: '密码至少 6 位'
    }
  ],
  captcha: [
    {
      required: true,
      message: '请输入验证码'
    }, {
      len: 4,
      message: '请输入 4 位验证码'
    }
  ]
}

//获取验证码
const captchaRes = ref<GetCaptchaRes>({
  captchaKey: '',
  captchaBase64: ''
})

function getCaptchaData() {
  getCaptchaApi().then(res => {
    captchaRes.value = res.payload
  })
}

//定义表单数据
const formData: FormProps['data'] = reactive({
  account: 'admin',
  pwd: '123456',
  captcha: ''
});
//表单提交事件
const handleSubmit: FormProps['onSubmit'] = ({validateResult}) => {
  if (validateResult !== true) {
    return;
  }
  const req: AccountLoginReq = {
    account: formData!.account,
    pwd: formData!.pwd,
    captcha: formData!.captcha,
    captchaKey: captchaRes.value.captchaKey
  }
  loginApi(req).then(res => {
    MessagePlugin.success('登录成功')
    localStorage.setItem('loginInfo', JSON.stringify(res.payload))
    router.replace('/home')
  })
};
</script>