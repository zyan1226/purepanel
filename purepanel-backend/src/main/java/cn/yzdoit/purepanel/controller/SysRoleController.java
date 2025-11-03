package cn.yzdoit.purepanel.controller;

import cn.hutool.core.util.StrUtil;
import cn.yzdoit.purepanel.pojo.entity.SysRole;
import cn.yzdoit.purepanel.pojo.entity.SysRolePermission;
import cn.yzdoit.purepanel.pojo.entity.SysUserRole;
import cn.yzdoit.purepanel.pojo.req.RoleBindPermissionReq;
import cn.yzdoit.purepanel.pojo.req.RolePageListReq;
import cn.yzdoit.purepanel.pojo.res.Res;
import cn.yzdoit.purepanel.service.SysRolePermissionService;
import cn.yzdoit.purepanel.service.SysRoleService;
import cn.yzdoit.purepanel.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色信息相关接口
 *
 * @author 闫政
 * @since 2025/7/8 07:03 星期二
 */
@RestController
@RequestMapping("/sysRole")
@Tag(name = "角色信息相关接口")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;
    private final SysRolePermissionService sysRolePermissionService;


    @PostMapping("/pageList")
    @Operation(summary = "分页查询角色信息")
    public Res<IPage<SysRole>> pageList(@RequestBody RolePageListReq req) {
        IPage<SysRole> page = sysRoleService.page(new Page<>(req.getCurrent(), req.getSize()), Wrappers.<SysRole>lambdaQuery()
                .like(StrUtil.isNotBlank(req.getName()), SysRole::getName, req.getName())
                .orderByDesc(SysRole::getCreateTime));
        return Res.success(page);
    }

    @PostMapping("/save")
    @Operation(summary = "保存角色信息")
    public Res<?> save(@RequestBody SysRole req) {
        sysRoleService.saveOrUpdate(req);
        return Res.success();
    }

    @PostMapping("/bindPermission")
    @Operation(summary = "为角色授权")
    public Res<?> bindPermission(@RequestBody RoleBindPermissionReq req) {
        sysRoleService.bindPermission(req);
        return Res.success();
    }

    @GetMapping("/getHavePermission")
    @Operation(summary = "获取角色拥有权限")
    public Res<List<String>> getHavePermission(@RequestParam String roleId) {
        return Res.success(sysRoleService.getHavePermission(roleId));
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除指定角色")
    @Transactional(rollbackFor = Exception.class)
    public Res<?> delete(@PathVariable String id) {
        sysRoleService.removeById(id);
        sysRolePermissionService.remove(Wrappers.<SysRolePermission>lambdaQuery()
                .eq(SysRolePermission::getRoleId, id));
        sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRoleId, id));
        return Res.success();
    }
}
