package cn.yzdoit.purepanel.ai;

import cn.yzdoit.purepanel.service.SysPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * AI 工具定义
 *
 * @author 闫政
 * @since 2025/8/18 17:15 星期一
 */
@Component
@RequiredArgsConstructor
public class AITools {

    private final SysPermissionService sysPermissionService;

    @Tool(description = "查询系统菜单权限定义")
    private List<?> selectSystemMenuPermissionDefine() {
        return sysPermissionService.allTreeList(null, null, null);
    }

}
