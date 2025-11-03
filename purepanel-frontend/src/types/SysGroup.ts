export interface SysGroupTreeListRes extends SysGroup {
    children: SysGroupTreeListRes[];
}

export interface SysGroup {
    id?: string;
    pid?: string;
    name?: string;
    orderNo?: number;
}