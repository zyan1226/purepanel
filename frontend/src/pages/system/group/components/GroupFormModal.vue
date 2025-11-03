<template>
  <t-form :rules="formRules" :data="formData" @submit="handleSubmit">
    <t-dialog destroy-on-close :visible="dialogVisible" :close-btn="true" :on-close="closeDialog">
      <template #header>填写群组信息</template>
      <template #body>
        <t-form-item label="名称" name="name">
          <t-input v-model="formData.name" placeholder="请输入名称"/>
        </t-form-item>
        <t-form-item label="父级" name="pid">
          <t-tree-select
              v-model="formData.pid"
              :data="allTreeList"
              clearable
              filterable
              placeholder="请选择父级"
              :tree-props="parentOptionsTree"
          />
        </t-form-item>
        <t-form-item label="排序" name="orderNo">
          <t-input-number v-model="formData.orderNo"/>
          <t-tooltip content="数字越小，排序越靠前">
            <t-icon class="ml-2" name="help-circle"/>
          </t-tooltip>
        </t-form-item>
      </template>
      <template #footer>
        <t-button theme="primary" type="submit">保存</t-button>
      </template>
    </t-dialog>
  </t-form>
</template>

<script setup lang="ts">
import {type DialogProps, type FormProps, MessagePlugin, type TreeSelectProps} from "tdesign-vue-next";
import type {SysGroup, SysGroupTreeListRes} from "@/types/SysGroup.ts";
import {reactive, watch} from "vue";
import {groupSaveApi} from "@/api/groupApi.ts";

//定义接收的参数
const props = defineProps<{
  dialogVisible: Boolean;
  oldData?: SysGroup | null;
  allTreeList: SysGroupTreeListRes[] | undefined;
}>()

watch(() => props.oldData, () => {
  if (props.oldData) {
    // 编辑模式
    formData = reactive({...props.oldData})
  } else {
    // 新增模式
    formData = reactive<SysGroup>({
      orderNo: 0,
    });
  }
})
//定义事件
const emit = defineEmits<{
  (e: 'update:dialogVisible', value: Boolean): void;
  (e: 'submit-success'): void;
}>()
//父级树选择 props
const parentOptionsTree: TreeSelectProps['treeProps'] = {
  keys: {
    label: 'name',
    value: 'id',
    children: 'children',
  },
};
//定义表单数据
let formData = reactive<SysGroup>({});
//定义表单校验规则
const formRules: FormProps['rules'] = {
  name: [
    {
      required: true,
      message: '请输入名称',
    },
  ],
}
//关闭弹窗
const closeDialog: DialogProps['onClose'] = () => {
  emit('update:dialogVisible', false)
};
//定义表单提交事件
const handleSubmit: FormProps['onSubmit'] = ({validateResult}) => {
  if (validateResult !== true) {
    return;
  }
  groupSaveApi(formData).then(() => {
    MessagePlugin.success('保存成功')
    emit('update:dialogVisible', false)
    emit('submit-success')
  })
};
</script>