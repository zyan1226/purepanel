import type Res from "@/types/Res.ts";
import type {SysGroup, SysGroupTreeListRes} from "@/types/SysGroup.ts";
import request from "@/api/request.ts";

//获取列表
export function groupAllTreeListApi(params?: any): Promise<Res<SysGroupTreeListRes[]>> {
    return request.get('/sysGroup/allTreeList', {params})
}

//保存
export function groupSaveApi(req: SysGroup): Promise<Res<any>> {
    return request.post('/sysGroup/save', req)
}
//删除
export function groupDeleteApi(id: string): Promise<Res<any>> {
    return request.delete(`/sysGroup/delete/${id}`)
}
