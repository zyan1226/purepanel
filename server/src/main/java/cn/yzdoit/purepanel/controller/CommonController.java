package cn.yzdoit.purepanel.controller;

import cn.yzdoit.purepanel.pojo.res.Res;
import cn.yzdoit.purepanel.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 不便分类的公用接口
 *
 * @author 闫政
 * @since 2025/7/9 08:07 星期三
 */
@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
@Tag(name = "不便分类的公用接口")
public class CommonController {

    private final CommonService commonService;


    @PostMapping("/uploadFile")
    @Operation(summary = "上传文件")
    public Res<String> uploadFile(@RequestParam("file") MultipartFile file
            , @RequestParam(required = false) String bizPath) {
        return Res.success(commonService.uploadFile(file, bizPath));
    }
}
