package cn.yzdoit.purepanel.mapper;


import cn.yzdoit.purepanel.pojo.entity.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限关联表
 *
 * @author 闫政
 * @since 2025/6/23 23:08 周一
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {
}
