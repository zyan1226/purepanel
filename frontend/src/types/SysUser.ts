export interface SysUser {
    id?: string;
    name?: string;
    account?: string;
    pwd?: string;
    salt?: string;
    avatar?: string;
    status?: boolean;
    email?: string;
    phoneNumber?: string;
}

export interface UserPageListRes extends SysUser {
    groupIdList?: string[];
    roleIdList?: string[];
    groupNames?: string;
    roleNames?: string;
}

export interface UserSaveReq extends SysUser {
    groupIdList?: string[];
    roleIdList?: string[];
    confirmPwd?: string;
}