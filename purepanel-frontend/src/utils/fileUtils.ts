const baseURL = import.meta.env.VITE_API_BASE

//根据文件相对路径获取文件网络地址
export function getFileNetworkPath(filePath: string) {
    if (filePath) {
        return `${baseURL}/files/${filePath}`;
    }
    return '';
}