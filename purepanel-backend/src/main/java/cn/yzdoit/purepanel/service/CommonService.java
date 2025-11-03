package cn.yzdoit.purepanel.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 不便分类的公用服务
 *
 * @author 闫政
 * @since 2025/7/9 08:13 星期三
 */
public interface CommonService {

    /**
     * 上传文件
     *
     * @param file    文件
     * @param bizPath 业务路径
     * @return 文件路径（相对）
     */
    String uploadFile(MultipartFile file, String bizPath);
}
