export interface GetLoginUserPermissionRes {
    permissionTree: SysPermissionTreeListRes[];
    menuTree: SysPermissionTreeListRes[];
    pageElementList: SysPermission[];
}

export interface SysPermissionTreeListRes extends SysPermission {
    children: SysPermissionTreeListRes[];
}

export interface SysPermission {
    id?: string;
    pid?: string;
    type?: string;
    path?: string;
    title?: string;
    icon?: string;
    orderNo?: number;
    isHidden?: boolean;
    menuLevel?: number;
    authCode?: string;
    version?: number;
}