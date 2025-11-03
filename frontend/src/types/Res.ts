export default interface Res<T> {
    status: number;
    payload: T;
    msg: string;
    exceptionInfo: string;
    serverTime: Date;
    serverTimeZone: string;
}