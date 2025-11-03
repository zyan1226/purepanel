package cn.yzdoit.purepanel.mapper;


import cn.yzdoit.purepanel.pojo.bo.SysUserRoleBo;
import cn.yzdoit.purepanel.pojo.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关联表
 *
 * @author 闫政
 * @since 2025/6/23 23:04 周一
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户id查询用户角色
     *
     * @param userIdList 用户id
     * @return 用户角色列表
     */
    List<SysUserRoleBo> listByUserId(@Param("userIdList") List<String> userIdList);
}
