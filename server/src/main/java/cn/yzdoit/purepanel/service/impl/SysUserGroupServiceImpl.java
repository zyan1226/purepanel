package cn.yzdoit.purepanel.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.yzdoit.purepanel.constant.CacheName;
import cn.yzdoit.purepanel.mapper.SysGroupMapper;
import cn.yzdoit.purepanel.mapper.SysUserGroupMapper;
import cn.yzdoit.purepanel.pojo.entity.SysGroup;
import cn.yzdoit.purepanel.pojo.entity.SysUserGroup;
import cn.yzdoit.purepanel.service.SysUserGroupService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 用户群组关联相关服务
 *
 * @author 闫政
 * @since 2025/7/17 14:46 星期四
 */
@Service
@RequiredArgsConstructor
public class SysUserGroupServiceImpl extends ServiceImpl<SysUserGroupMapper, SysUserGroup> implements SysUserGroupService {


    private final SysUserGroupMapper sysUserGroupMapper;
    private final SysGroupMapper sysGroupMapper;

    /**
     * 根据用户ID查询群组列表(包含下级)
     *
     * @param userId 用户ID
     * @return 群组列表
     */
    @Override
    @Cacheable(value = CacheName.LIST_GROUP_AND_CHILDREN_BY_USER_ID,key = "#userId")
    public List<SysGroup> listGroupAndChildrenByUserId(String userId) {
        List<SysUserGroup> sysUserGroupList = sysUserGroupMapper.selectList(Wrappers.<SysUserGroup>lambdaQuery()
                .eq(SysUserGroup::getUserId, userId));
        if (CollUtil.isEmpty(sysUserGroupList)) {
            return Collections.emptyList();
        }
        List<String> groupIdList = sysUserGroupList.stream().map(SysUserGroup::getGroupId).toList();
        List<SysGroup> groupList = sysGroupMapper.selectByIds(groupIdList);
        // 递归查询所有子级群组
        List<SysGroup> allGroupList = new ArrayList<>(groupList);
        for (SysGroup group : groupList) {
            List<SysGroup> childGroupList = getChildrenRecursively(group.getId());
            allGroupList.addAll(childGroupList);
        }
        return allGroupList;
    }

    /**
     * 递归查询群组的所有子级群组
     *
     * @param groupId 父级群组ID
     * @return 所有子级群组列表
     */
    @Override
    public List<SysGroup> getChildrenRecursively(String groupId) {
        List<SysGroup> result = new ArrayList<>();
        List<SysGroup> children = sysGroupMapper.selectList(Wrappers.<SysGroup>lambdaQuery()
                .eq(SysGroup::getPid, groupId));
        if (CollUtil.isNotEmpty(children)) {
            result.addAll(children);
            // 递归查询子级的子级
            for (SysGroup child : children) {
                result.addAll(getChildrenRecursively(child.getId()));
            }
        }
        return result;
    }

}
