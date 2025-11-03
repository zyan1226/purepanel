package cn.yzdoit.purepanel.service;

import cn.yzdoit.purepanel.pojo.entity.SysRole;
import cn.yzdoit.purepanel.pojo.req.RoleBindPermissionReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色相关服务
 *
 * @author 闫政
 * @since 2025/7/17 11:23 星期四
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 为角色授权
     *
     * @param req 请求参数
     */
    void bindPermission(RoleBindPermissionReq req);

    /**
     * 获取角色拥有权限
     *
     * @param roleId 角色id
     * @return List
     */
    List<String> getHavePermission(String roleId);
}
