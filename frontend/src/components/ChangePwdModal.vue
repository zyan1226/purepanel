<template>
  <t-form :rules="formRules" :data="formData" @submit="handleSubmit">
    <t-dialog destroy-on-close :visible="dialogVisible" :close-btn="true" :on-close="closeDialog">
      <template #header>
        修改密码
      </template>
      <template #body>
        <div class="h-44">
          <t-form-item v-if="needOldPwd" label="旧密码" name="oldPwd">
            <t-input v-model="formData.oldPwd" type="password" placeholder="请输入旧密码"/>
          </t-form-item>
          <t-form-item label="新密码" name="newPwd">
            <t-input v-model="formData.newPwd" type="password" placeholder="请输入新密码"/>
          </t-form-item>
          <t-form-item label="确认新密码" name="confirmPwd">
            <t-input v-model="formData.confirmPwd" type="password" placeholder="请确认新密码"/>
          </t-form-item>
        </div>
      </template>
      <template #footer>
        <t-button type="submit">修改</t-button>
      </template>
    </t-dialog>
  </t-form>
</template>
<script setup lang="ts">
import {type DialogProps, type FormProps, MessagePlugin} from "tdesign-vue-next";
import {reactive} from "vue";
import {changePwdApi, changeSelfPwdApi} from "@/api/userApi.ts";

//定义接收的参数
const props = defineProps<{
  dialogVisible: Boolean;
  needOldPwd?: Boolean;
  userId?: string;
}>()
//定义事件
const emit = defineEmits<{
  (e: 'update:dialogVisible', value: Boolean): void;
}>()
//定义表单校验规则
const formRules: FormProps['rules'] = {
  oldPwd: [
    {
      required: !!props.needOldPwd,
      message: '请输入原密码',
    },
    {
      min: 6,
      message: '密码至少 6 位'
    }
  ],
  newPwd: [
    {
      required: true,
      message: '请输入新密码',
    },
    {
      min: 6,
      message: '密码至少 6 位'
    }
  ],
  confirmPwd: [
    {
      required: true,
      message: '请确认新密码',
    },
    {
      validator: (value) => {
        return value === formData.newPwd
      },
      message: '两次输入密码不一致',
    }
  ],
}
//定义表单数据
let formData = reactive<{
  oldPwd?: string;
  newPwd?: string;
  confirmPwd?: string;
}>({});
//关闭弹窗
const closeDialog: DialogProps['onClose'] = () => {
  formData = reactive({})
  emit('update:dialogVisible', false)
};
//提交表单
const handleSubmit: FormProps['onSubmit'] = ({validateResult}) => {
  if (validateResult !== true) {
    return;
  }
  if (props.needOldPwd) {
    changeSelfPwdApi({
      oldPwd: formData.oldPwd,
      newPwd: formData.newPwd!
    }).then(() => {
      MessagePlugin.success('修改成功')
      emit('update:dialogVisible', false)
    })
  } else {
    changePwdApi({
      oldPwd: formData.oldPwd,
      newPwd: formData.newPwd!,
      userId: props.userId
    }).then(() => {
      MessagePlugin.success('修改成功')
      emit('update:dialogVisible', false)
    })
  }
  formData = reactive({})
}
</script>