import type Res from "@/types/Res.ts";
import request from "@/api/request.ts";

//上传文件
export function uploadFileApi(file: File, bizPath?: string): Promise<Res<string>> {
    const formData = new FormData()
    formData.append('file', file)
    formData.append('bizPath', bizPath ?? '')
    return request.post('/common/uploadFile', formData)
}