<template>
  <page-box class="flex-row h-[96%] flex ">
    <div class="flex flex-col w-1/6 min-w-max p-4">
      <div class="flex items-center flex-row p-2 gap-1
          hover:bg-(--td-bg-color-container-hover) cursor-pointer rounded-lg"
           @click="handleNewConversation">
        <t-icon name="edit-1"/>
        <span>新聊天</span>
      </div>
      <p v-if="conversationList.length > 0" class="text-(--td-text-color-secondary) mt-8 ml-2">历史聊天</p>
      <div v-for="conversation in conversationList"
           :key="conversation.id"
           @mouseenter="hoverConversationId=conversation.id"
           @mouseleave="hoverConversationId = ''"
           @click="()=>handleConversationClick(conversation.id)"
           :class="['flex items-center flex-row p-2 hover:bg-(--td-bg-color-container-hover) cursor-pointer rounded-lg mt-2',
           currentConversationId === conversation.id ? 'bg-(--td-bg-color-container-hover)' : '']">
        <span class="w-48 text-ellipsis overflow-hidden text-nowrap">{{ conversation.title }}</span>
        <t-dropdown v-if="hoverConversationId === conversation.id || currentConversationId === conversation.id"
                    trigger="click"
                    :options="conversationOptions"
                    :hide-after-item-click="true"
                    class="ml-auto">
          <t-icon name="more"/>
        </t-dropdown>
      </div>
    </div>
    <chat
        ref="chatRef"
        class="px-60! pb-4! border-l border-l-(--td-border-level-1-color)"
        :reverse="false"
        :clear-history="false">
      <div v-if="chatList.length === 0" class="flex flex-col justify-center gap-14 h-full">
        <p class="w-full text-center text-3xl">😊 我们先从哪里开始呢？</p>
      </div>
      <template v-for="(item, index) in chatList" :key="index">
        <chat-item :class="['markdown-body',index===0?'mt-12':'']"
                   :content="item.content"
                   :role="item.role"
                   :avatar="item.avatar"
                   :text-loading="index===chatList.length-1?textLoading:false"
                   animation="moving"
                   :variant="item.role==='user'?'base':'text'"/>
      </template>
      <template #footer>
        <chat-sender :textarea-props="{placeholder: '询问任何问题'}"
                     @send="handleSend"
                     v-model:value="senderValue"
                     :loading="loading">
          <template #suffix="{ renderPresets }">
            <component :is="renderPresets([])"/>
          </template>
        </chat-sender>
      </template>
    </chat>
  </page-box>
</template>
<script setup lang="tsx">
import {nextTick, onMounted, ref} from 'vue';
import {listChatbotChatRecordApi, listChatbotConversationApi, streamChatApi} from "@/api/chatbotApi.ts";
import type {AiChatbotConversation} from "@/types/AiChatbotConversation.ts";
import {DeleteIcon, Edit2Icon, ShareIcon} from 'tdesign-icons-vue-next';
import {Chat, ChatItem, ChatSender} from '@tdesign-vue-next/chat'
import {Snowflake} from "@theinternetfolks/snowflake";
import type {DropdownOption} from "tdesign-vue-next";

//当前会话ID
const currentConversationId = ref<string>()
//鼠标悬停的会话ID
const hoverConversationId = ref<string>()

onMounted(() => {
  //获取会话列表
  listConversation()
  //获取聊天记录
  listChatRecord()
})

//历史聊天菜单项
const conversationOptions: Array<DropdownOption> = [
  {
    content: '重命名',
    value: 1,
    prefixIcon: () => <Edit2Icon/>,
  },
  {
    content: '分享',
    value: 1,
    prefixIcon: () => <ShareIcon/>,
  },
  {
    content: '删除',
    value: 1,
    prefixIcon: () => <DeleteIcon/>,
    theme: 'error',
  },
]

//消息对话列表
const chatList = ref<{
  avatar?: string;
  name?: string;
  datetime?: string;
  content: string;
  role: 'user' | 'assistant' | 'model-change',
}[]>([]);

//会话列表
const conversationList = ref<AiChatbotConversation[]>([])

//获取会话列表
function listConversation() {
  listChatbotConversationApi().then(res => {
    conversationList.value = res.payload
  })
}

//获取聊天记录
function listChatRecord() {
  if (!currentConversationId.value) {
    return
  }
  listChatbotChatRecordApi(currentConversationId.value).then(res => {
    chatList.value = res.payload.map(item => {
      return {
        name: item.type === 'USER' ? '自己' : 'AI 助理',
        content: item.content,
        role: item.type === 'USER' ? 'user' : 'assistant',
        avatar: item.type === 'USER' ? '' : 'https://tdesign.gtimg.com/site/chat-avatar.png',
      }
    })
    scrollToBottom()
  })
}

function handleNewConversation() {
  chatList.value = []
  currentConversationId.value = ''
}

function handleConversationClick(conversationId?: string) {
  currentConversationId.value = conversationId
  listChatRecord()
}

const textLoading = ref(false)
const loading = ref(false)
const senderValue = ref('')
const chatRef = ref();

// 滚动到底部
function scrollToBottom() {
  nextTick(() => {
    chatRef.value?.scrollToBottom?.({
      behavior: 'auto',
    });
  })
}

function handleSend(value: string) {
  textLoading.value = true
  loading.value = true
  senderValue.value = ''
  chatList.value.push({
    name: '自己',
    content: value,
    role: 'user',
  })
  scrollToBottom()
  chatList.value.push({
    name: 'AI 助理',
    content: '',
    role: 'assistant',
    avatar: 'https://tdesign.gtimg.com/site/chat-avatar.png',
  })
  let conversationId
  if (currentConversationId.value) {
    conversationId = currentConversationId.value
  } else {
    conversationId = Snowflake.generate()
    currentConversationId.value = conversationId;
  }
  let firstFlag = true;
  streamChatApi({
    question: value,
    conversationId: conversationId,
  }, (message) => {
    if (firstFlag) {
      textLoading.value = false
    }
    // 修复：添加类型检查以避免 "Object is possibly 'undefined'" 错误
    const lastIndex = chatList.value.length - 1;
    if (chatList.value[lastIndex]) {
      chatList.value[lastIndex].content += message;
    }
    scrollToBottom()
    firstFlag = false;
  }, () => {
    listConversation()
    loading.value = false
  })
}
</script>

<style lang="css">
/* 恢复markdown-body的样式 */
.markdown-body ul,
.markdown-body ol,
.markdown-body li,
.markdown-body h1,
.markdown-body h2,
.markdown-body h3,
.markdown-body h4,
.markdown-body h5 {
  all: revert !important;
}

.markdown-body hr {
  color: var(--td-gray-color-4);
  margin-top: 10px;
  margin-bottom: 10px;
}

.markdown-body code {
  font-size: var(--td-font-size-body-medium);
}

.markdown-body p {
  line-height: 30px;
}

.markdown-body table {
  border-collapse: separate;
  border-spacing: 0;
  border: 1px solid var(--td-gray-color-4);
  width: 100%;
  margin: 1em 0;
  border-radius: var(--td-radius-default);
  overflow: hidden;
}

.markdown-body th,
.markdown-body td {
  border: 1px solid var(--td-gray-color-4);
  padding: 0.5em 1em; /* 内边距 */
  text-align: left;
}

.markdown-body th {
  background-color: var(--td-bg-color-secondarycontainer-hover); /* 表头背景 */
}
</style>