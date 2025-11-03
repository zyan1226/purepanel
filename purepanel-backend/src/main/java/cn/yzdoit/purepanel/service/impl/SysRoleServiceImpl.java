package cn.yzdoit.purepanel.service.impl;

import cn.yzdoit.purepanel.mapper.SysRoleMapper;
import cn.yzdoit.purepanel.pojo.entity.SysRole;
import cn.yzdoit.purepanel.pojo.entity.SysRolePermission;
import cn.yzdoit.purepanel.pojo.req.RoleBindPermissionReq;
import cn.yzdoit.purepanel.service.SysRolePermissionService;
import cn.yzdoit.purepanel.service.SysRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色相关服务
 *
 * @author 闫政
 * @since 2025/7/17 11:23 星期四
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRolePermissionService sysRolePermissionService;

    /**
     * 为角色授权
     *
     * @param req 请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindPermission(RoleBindPermissionReq req) {
        //删除旧的
        sysRolePermissionService.remove(Wrappers.<SysRolePermission>lambdaQuery()
                .eq(SysRolePermission::getRoleId, req.getRoleId()));
        //添加新的
        List<SysRolePermission> rolePermissionList = new ArrayList<>();
        req.getPermissionIdSet().forEach(permissionId ->
                rolePermissionList.add(SysRolePermission.builder()
                        .roleId(req.getRoleId())
                        .permissionId(permissionId)
                        .build())
        );
        sysRolePermissionService.saveBatch(rolePermissionList);
    }

    /**
     * 获取角色拥有权限
     *
     * @param roleId 角色id
     * @return List
     */
    @Override
    public List<String> getHavePermission(String roleId) {
        return sysRolePermissionService.list(Wrappers.<SysRolePermission>lambdaQuery()
                        .select(SysRolePermission::getPermissionId)
                        .eq(SysRolePermission::getRoleId, roleId))
                .stream().map(SysRolePermission::getPermissionId).toList();
    }
}
