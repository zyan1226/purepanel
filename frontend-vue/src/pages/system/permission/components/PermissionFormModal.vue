<template>
  <t-form :rules="formRules" :data="formData" @submit="handleSubmit">
    <t-dialog destroy-on-close :visible="dialogVisible" :close-btn="true" :on-close="closeDialog">
      <template #header>填写菜单权限信息</template>
      <template #body>
        <t-form-item label="标题" name="title">
          <t-input v-model="formData.title" placeholder="请输入标题"/>
        </t-form-item>
        <t-form-item label="类型" name="type">
          <t-radio-group variant="primary-filled" v-model="formData.type" @change="handleTypeChange">
            <t-radio-button value="0">菜单</t-radio-button>
            <t-radio-button value="1">页面元素</t-radio-button>
            <t-radio-button value="2">菜单组</t-radio-button>
          </t-radio-group>
        </t-form-item>
        <t-form-item v-if="formData.type!=='2'" label="父级" name="pid">
          <t-tree-select
              v-model="formData.pid"
              :data="allTreeList"
              clearable
              filterable
              placeholder="请选择父级"
              :tree-props="parentOptionsTree"
          />
        </t-form-item>
        <t-form-item v-if="formData.type==='1'" label="授权编码" name="authCode">
          <t-input v-model="formData.authCode" placeholder="请输入授权编码"/>
          <t-tooltip content="开发人员用于控制页面元素的显隐">
            <t-icon class="ml-2" name="help-circle"/>
          </t-tooltip>
        </t-form-item>
        <t-form-item v-if="formData.type==='0'" label="访问路径" name="path">
          <t-input v-model="formData.path" placeholder="请输入访问路径"/>
        </t-form-item>
        <t-form-item v-if="formData.type==='0'" label="图标" name="icon">
          <t-select
              v-model="formData.icon"
              placeholder="请选择"
              :popup-props="{ overlayInnerStyle: { width: '360px' } }">
            <t-option v-for="item in iconOptions" :key="item.stem" :value="item.stem" class="!inline-block !text-2xl">
              <div>
                <t-icon :name="item.stem"/>
              </div>
            </t-option>
            <template #valueDisplay>
              <t-icon :name="formData.icon" class="mr-2"/>
              {{ formData.icon }}
            </template>
          </t-select>
        </t-form-item>
        <t-form-item label="排序" name="orderNo">
          <t-input-number v-model="formData.orderNo"/>
          <t-tooltip content="数字越小，排序越靠前">
            <t-icon class="ml-2" name="help-circle"/>
          </t-tooltip>
        </t-form-item>
        <t-form-item label="隐藏" name="isHidden">
          <t-switch v-model="formData.isHidden"/>
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
import {reactive, ref, watch} from "vue";
import {addOrUpdateApi} from "@/api/permissionApi.ts";
import type {SysPermission, SysPermissionTreeListRes} from "@/types/SysPermission.ts";
import {manifest} from 'tdesign-icons-vue-next';

// 获取全部图标的列表
const iconOptions = ref(manifest);

const parentOptionsTree: TreeSelectProps['treeProps'] = {
  keys: {
    label: 'title',
    value: 'id',
    children: 'children',
  },
};

//定义接收的参数
const props = defineProps<{
  allTreeList: SysPermissionTreeListRes[] | undefined;
  dialogVisible: Boolean;
  oldData?: SysPermission | null;
}>()

watch(() => props.oldData, () => {
  if (props.oldData?.id) {
    // 编辑模式
    formData = reactive({...props.oldData})
  } else {
    // 新增模式
    formData = reactive<SysPermission>({
      pid: props.oldData?.pid,
      type: '0',
      orderNo: 0,
      isHidden: false,
    });
  }
})

//定义事件
const emit = defineEmits<{
  (e: 'update:dialogVisible', value: Boolean): void;
  (e: 'submit-success'): void;
}>()

//关闭弹窗
const closeDialog: DialogProps['onClose'] = () => {
  emit('update:dialogVisible', false)
};
//定义表单校验规则
const formRules: FormProps['rules'] = {
  title: [
    {
      required: true,
      message: '请输入标题',
    },
  ],
}

//定义表单数据
let formData = reactive<SysPermission>({});
//定义表单提交事件
const handleSubmit: FormProps['onSubmit'] = ({validateResult}) => {
  if (validateResult !== true) {
    return;
  }
  const data: SysPermission = {
    type: formData.type,
    path: formData.path,
    title: formData.title,
    orderNo: formData.orderNo,
    isHidden: formData.isHidden,
    id: formData.id,
    pid: formData.pid,
    version: formData.version,
    icon: formData.icon,
    authCode: formData.authCode,
  }
  addOrUpdateApi(data).then(() => {
    MessagePlugin.success('保存成功')
    emit('update:dialogVisible', false)
    emit('submit-success')
  })
};

function handleTypeChange(value: string) {
  if (value === '2') {
    formData.pid = undefined
    formData.path = undefined
    formData.icon = undefined
  }
}
</script>