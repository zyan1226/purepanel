package cn.yzdoit.purepanel.mapper;

import cn.yzdoit.purepanel.pojo.entity.SysUser;
import cn.yzdoit.purepanel.pojo.req.UserPageListReq;
import cn.yzdoit.purepanel.pojo.res.UserPageListRes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户信息表
 *
 * @author 闫政
 * @since 2025/6/23 06:42 星期一
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询用户信息
     *
     * @param page 分页对象
     * @param req  查询条件
     * @return 分页结果
     */
    IPage<UserPageListRes> pageList(IPage<UserPageListRes> page, @Param("req") UserPageListReq req);
}
