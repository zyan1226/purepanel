export interface AiChatbotChatRecord {
    id?: string;
    conversationId?: string;
    type?: 'USER' | 'ASSISTANT'
    content: string;
    createTime?: string;
}