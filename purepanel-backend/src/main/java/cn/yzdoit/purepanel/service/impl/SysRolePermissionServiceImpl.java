package cn.yzdoit.purepanel.service.impl;


import cn.yzdoit.purepanel.mapper.SysRolePermissionMapper;
import cn.yzdoit.purepanel.pojo.entity.SysRolePermission;
import cn.yzdoit.purepanel.service.SysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色权限关联相关服务
 *
 * @author 闫政
 * @since 2025/7/24 23:23 周四
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission>
        implements SysRolePermissionService {
}
