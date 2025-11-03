package cn.yzdoit.purepanel.service;

import cn.yzdoit.purepanel.pojo.entity.SysGroup;
import cn.yzdoit.purepanel.pojo.entity.SysUserGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户群组关联相关服务
 *
 * @author 闫政
 * @since 2025/7/17 14:46 星期四
 */
public interface SysUserGroupService extends IService<SysUserGroup> {

    /**
     * 根据用户ID查询群组列表(包含下级)
     *
     * @param userId 用户ID
     * @return 群组列表
     */
    List<SysGroup> listGroupAndChildrenByUserId(String userId);


    /**
     * 递归查询群组的所有子级群组
     *
     * @param groupId 父级群组ID
     * @return 所有子级群组列表
     */
    List<SysGroup> getChildrenRecursively(String groupId);
}
