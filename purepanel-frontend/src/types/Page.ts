export interface PageReq {
    //页码
    current: number;
    //每页条数
    size: number;
}

export interface PageRes<T> {
    //页码
    current: number;
    //每页条数
    size: number;
    //数据
    records: T[];
    //总数据量
    total: number;
    //总页数
    pages: number;
}