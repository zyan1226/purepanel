package cn.yzdoit.purepanel.controller;

import cn.yzdoit.purepanel.pojo.entity.SysPermission;
import cn.yzdoit.purepanel.pojo.res.GetUserPermissionRes;
import cn.yzdoit.purepanel.pojo.res.Res;
import cn.yzdoit.purepanel.service.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 菜单权限相关接口
 *
 * @author 闫政
 * @since 2025/6/17 15:07 星期二
 */
@Slf4j
@Tag(name = "菜单权限相关接口")
@RestController
@RequestMapping("/sysPermission")
@RequiredArgsConstructor
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    @GetMapping("/allTreeList")
    @Operation(summary = "获取树形列表")
    public Res<List<?>> allTreeList(@RequestParam(required = false) String title
            , @RequestParam(required = false) String path
            , @RequestParam(required = false) Integer type) {
        return Res.success(sysPermissionService.allTreeList(title, path, type));
    }

    @GetMapping("/getLoginUserPermission")
    @Operation(summary = "获取登录用户的权限")
    public Res<GetUserPermissionRes> getLoginUserPermission(@RequestAttribute String loginUserId) {
        return Res.success(sysPermissionService.getUserPermission(loginUserId));
    }

    @PostMapping("/addOrUpdate")
    @Operation(summary = "保存菜单权限定义")
    public Res<?> addOrUpdate(@RequestBody SysPermission sysPermission) {
        sysPermissionService.addOrUpdate(sysPermission);
        return Res.success();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除菜单权限定义")
    @Transactional(rollbackFor = Exception.class)
    public Res<?> delete(@PathVariable String id) {
        sysPermissionService.recurDelete(Collections.singletonList(id));
        return Res.success();
    }
}
