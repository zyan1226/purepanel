import {
    type GetLoginUserPermissionRes,
    type SysPermission,
    type SysPermissionTreeListRes
} from "@/types/SysPermission.ts";
import request from "@/api/request.ts";
import type Res from "@/types/Res.ts";

//新增或修改
export function addOrUpdateApi(req: SysPermission): Promise<Res<any>> {
    return request.post('/sysPermission/addOrUpdate', req)
}

//获取列表
export function allTreeListApi(params?: any): Promise<Res<SysPermissionTreeListRes[]>> {
    return request.get('/sysPermission/allTreeList', {params})
}
//获取登录用户的权限
export function getLoginUserPermissionApi(): Promise<Res<GetLoginUserPermissionRes>> {
    return request.get('/sysPermission/getLoginUserPermission')
}
//删除
export function deleteApi(id: string): Promise<Res<any>> {
    return request.delete(`/sysPermission/delete/${id}`)
}