//获取登录用户的权限
import type Res from "@/types/Res.ts";
import type {AiChatbotConversation} from "@/types/AiChatbotConversation.ts";
import request from "@/api/request.ts";
import type {AiChatbotChatRecord} from "@/types/AiChatbotChatRecord.ts";

//获取会话列表
export function listChatbotConversationApi(): Promise<Res<AiChatbotConversation[]>> {
    return request.get('/chatbot/listConversation')
}

//获取会话记录
export function listChatbotChatRecordApi(conversationId: string): Promise<Res<AiChatbotChatRecord[]>> {
    return request.get(`/chatbot/listChatRecord?conversationId=${conversationId}`)
}

export interface AIChatReq {
    question: string;
    conversationId: string;
    modelName?: string;
}

// 从 vite 环境变量中读取 baseURL
const baseURL = import.meta.env.VITE_API_BASE

export async function streamChatApi(req: AIChatReq, onMessage: (message: string) => void, onComplete?: () => void) {
    const loginInfoStr = localStorage.getItem("loginInfo");
    if (!loginInfoStr) return;
    const loginCode = JSON.parse(loginInfoStr).loginCode;

    const response = await fetch(`${baseURL}/chatbot/chat`, {
        method: "POST",
        headers: {
            "Login-Code": loginCode,
            "Content-Type": "application/json",
            "Accept": "text/event-stream" // 建议加上
        },
        body: JSON.stringify(req),
        // signal: controller.signal, // 如果需要取消，再打开
    });

    if (!response.body) return;

    const reader = response.body.getReader();
    const decoder = new TextDecoder();
    let buffer = "";               // 未完整的那一部分行
    let dataParts: string[] = [];  // 收集同一个 SSE 事件中的所有 data 内容行
    let lastEventId: string | null = null;
    let eventType = "message";

    const flushEvent = () => {
        if (dataParts.length === 0) return;
        // 按 SSE 规范，用换行合并多行 data 字段
        const data = dataParts.join("\n");
        dataParts = [];

        if (data === "[DONE]") {
            // 根据你的协议决定要不要做特殊处理，这里我们忽略
            return;
        }

        // 尝试解析 JSON（可选），否则原样传回字符串
        try {
            // 如果 onMessage 只接受字符串，这里把解析的对象再转成字符串或改用 onMessageObject
            // const obj = JSON.parse(data);
            // onMessage(obj);
            onMessage(data);
        } catch (e) {
            onMessage(data);
        }
    };

    const processBuffer = (isFinal = false) => {
        // 把 buffer 按行切分（支持 \n 和 \r\n）
        const lines = buffer.split(/\r?\n/);
        // 如果不是最后一块，最后一行可能是不完整的，保存在 buffer
        if (!isFinal) {
            buffer = lines.pop() || "";
        } else {
            buffer = "";
        }

        for (const rawLine of lines) {
            // 空行 -> 事件结束（flush）
            if (rawLine === "") {
                flushEvent();
                eventType = "message";
                continue;
            }

            // 注释行（以 : 开头）忽略
            if (rawLine.charAt(0) === ":") continue;

            // 按第一个冒号分割 field:value
            const idx = rawLine.indexOf(":");
            let field: string;
            let value = "";

            if (idx === -1) {
                field = rawLine;
            } else {
                field = rawLine.slice(0, idx);
                value = rawLine.slice(idx + 1);
                // 如果冒号后有一个空格，去掉它（SSE 规范）
                if (value.startsWith(" ")) value = value.slice(1);
            }

            switch (field) {
                case "data":
                    dataParts.push(value);
                    break;
                case "event":
                    eventType = value;
                    break;
                case "id":
                    lastEventId = value;
                    break;
                case "retry":
                    // 可选：根据需要更新重连时间
                    break;
                default:
                    // 忽略未知字段
                    break;
            }
        }
    };

    try {
        while (true) {
            const {value, done} = await reader.read();
            if (done) {
                // flush decoder 的缓冲区
                buffer += decoder.decode();
                // 处理最后残留并把最后事件 flush 出去
                processBuffer(true);
                break;
            }
            buffer += decoder.decode(value, {stream: true});
            processBuffer(false);
        }
    } finally {
        // 可选：关闭 reader
        try {
            onComplete?.()
            reader.releaseLock();
        } catch (_) {
        }
    }
}

