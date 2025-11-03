package cn.yzdoit.purepanel.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.yzdoit.purepanel.exception.BusinessException;
import cn.yzdoit.purepanel.pojo.properties.PurepanelProperties;
import cn.yzdoit.purepanel.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 不便分类的公用服务
 *
 * @author 闫政
 * @since 2025/7/9 08:14 星期三
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final PurepanelProperties purepanelProperties;

    /**
     * 上传文件
     *
     * @param file    文件
     * @param bizPath 业务路径
     * @return 文件路径（相对）
     */
    @Override
    public String uploadFile(MultipartFile file, String bizPath) {
        String fileName = IdUtil.getSnowflakeNextIdStr() + "." + FileUtil.getSuffix(file.getOriginalFilename());
        String dirPath, datePath;
        if (StrUtil.isNotBlank(bizPath)) {
            datePath = "";
            dirPath = purepanelProperties.getLocalObjectStoreBasePath() + "/" + bizPath;
        } else {
            datePath = DateUtil.format(new Date(), "/yyyy/MM/dd");
            dirPath = purepanelProperties.getLocalObjectStoreBasePath() + "upload" + datePath;
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            boolean flag = dir.mkdirs();
        }
        try {
            File localFile = new File(dirPath + "/" + fileName);
            boolean flag = localFile.createNewFile();
            file.transferTo(localFile);
            if (StrUtil.isBlank(bizPath)) {
                return "upload" + datePath + "/" + fileName;
            }
            return bizPath + "/" + fileName;
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new BusinessException("上传失败");
        }
    }
}
