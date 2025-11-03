package cn.yzdoit.purepanel.mapper;

import cn.yzdoit.purepanel.pojo.bo.SysUserGroupBo;
import cn.yzdoit.purepanel.pojo.entity.SysUserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户群组关联表
 *
 * @author 闫政
 * @since 2025/7/8 05:25 星期二
 */
@Mapper
public interface SysUserGroupMapper extends BaseMapper<SysUserGroup> {

    /**
     * 根据用户id查询用户群组
     *
     * @param userIdList 用户id
     * @return 用户群组列表
     */
    List<SysUserGroupBo> listByUserId(@Param("userIdList") List<String> userIdList);
}
