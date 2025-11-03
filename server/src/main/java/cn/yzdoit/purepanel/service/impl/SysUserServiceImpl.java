package cn.yzdoit.purepanel.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.yzdoit.purepanel.exception.BusinessException;
import cn.yzdoit.purepanel.mapper.SysUserGroupMapper;
import cn.yzdoit.purepanel.mapper.SysUserMapper;
import cn.yzdoit.purepanel.mapper.SysUserRoleMapper;
import cn.yzdoit.purepanel.pojo.bo.SysUserGroupBo;
import cn.yzdoit.purepanel.pojo.bo.SysUserRoleBo;
import cn.yzdoit.purepanel.pojo.bo.TwoParams;
import cn.yzdoit.purepanel.pojo.entity.SysUser;
import cn.yzdoit.purepanel.pojo.entity.SysUserGroup;
import cn.yzdoit.purepanel.pojo.entity.SysUserRole;
import cn.yzdoit.purepanel.pojo.req.ChangePwdReq;
import cn.yzdoit.purepanel.pojo.req.UserPageListReq;
import cn.yzdoit.purepanel.pojo.req.UserSaveReq;
import cn.yzdoit.purepanel.pojo.res.UserPageListRes;
import cn.yzdoit.purepanel.service.SysUserGroupService;
import cn.yzdoit.purepanel.service.SysUserRoleService;
import cn.yzdoit.purepanel.service.SysUserService;
import cn.yzdoit.purepanel.util.CheckUtil;
import cn.yzdoit.purepanel.util.PwdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户信息相关服务
 *
 * @author 闫政
 * @since 2025/6/23 06:43 星期一
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysUserGroupMapper sysUserGroupMapper;
    private final SysUserGroupService sysUserGroupService;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysUserRoleService sysUserRoleService;

    /**
     * 修改密码
     *
     * @param req 修改密码请求
     */
    @Override
    public void changePwd(ChangePwdReq req) {
        SysUser sysUser = sysUserMapper.selectById(req.getUserId());
        CheckUtil.notNull(sysUser, "用户不存在");
        if (req.getIsSelfChange()) {
            //检查旧密码
            CheckUtil.check(PwdUtil.verify(req.getOldPwd(), sysUser.getSalt(), sysUser.getPwd()), "旧密码错误");
        }
        //设置新密码
        TwoParams<String, String> pwdParams = PwdUtil.encode(req.getNewPwd());
        sysUserMapper.updateById(SysUser.builder()
                .id(sysUser.getId())
                .pwd(pwdParams.getA())
                .salt(pwdParams.getB())
                .build());
    }

    /**
     * 新增或修改用户信息
     *
     * @param req 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserSaveReq req) {
        try {
            if (StrUtil.isBlank(req.getId())) {
                //新增
                TwoParams<String, String> pwdParams = PwdUtil.encode(req.getPwd());
                req.setPwd(pwdParams.getA());
                req.setSalt(pwdParams.getB());
                sysUserMapper.insert(req);
            } else {
                //修改
                sysUserMapper.updateById(req);
            }
        } catch (DuplicateKeyException e) {
            throw new BusinessException("账号 " + req.getAccount() + " 不可用，请换一个");
        }
        //处理群组关联
        sysUserGroupMapper.delete(Wrappers.<SysUserGroup>lambdaQuery()
                .eq(SysUserGroup::getUserId, req.getId()));
        if (CollUtil.isNotEmpty(req.getGroupIdList())) {
            List<SysUserGroup> sysUserGroupList = req.getGroupIdList().stream().<SysUserGroup>map(groupId ->
                    SysUserGroup.builder()
                            .userId(req.getId())
                            .groupId(groupId)
                            .build()
            ).toList();
            sysUserGroupService.saveBatch(sysUserGroupList);
        }
        //处理角色关联
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, req.getId()));
        if (CollUtil.isNotEmpty(req.getRoleIdList())) {
            List<SysUserRole> sysUserRoleList = req.getRoleIdList().stream().<SysUserRole>map(roleId ->
                    SysUserRole.builder()
                            .userId(req.getId())
                            .roleId(roleId)
                            .build()
            ).toList();
            sysUserRoleService.saveBatch(sysUserRoleList);
        }
    }

    /**
     * 分页查询用户信息
     *
     * @param req 分页查询用户信息请求参数
     * @return 用户信息分页
     */
    @Override
    public IPage<UserPageListRes> pageList(UserPageListReq req) {
        IPage<UserPageListRes> page = new Page<>(req.getCurrent(), req.getSize());
        page = sysUserMapper.pageList(page, req);
        List<UserPageListRes> records = page.getRecords();
        if (CollUtil.isEmpty(records)) {
            return page;
        }
        List<String> userIdList = records.stream().map(UserPageListRes::getId).toList();
        //查询相关部门
        List<SysUserGroupBo> sysUserGroupBoList = sysUserGroupMapper.listByUserId(userIdList);
        Map<String, List<SysUserGroupBo>> sysUserGroupBoListByUser = new HashMap<>();
        if (CollUtil.isNotEmpty(sysUserGroupBoList)) {
            sysUserGroupBoListByUser = sysUserGroupBoList
                    .stream().collect(Collectors.groupingBy(SysUserGroupBo::getUserId));
        }
        //查询相关角色
        List<SysUserRoleBo> sysUserRoleBoList = sysUserRoleMapper.listByUserId(userIdList);
        Map<String, List<SysUserRoleBo>> sysUserRoleBoListByUser = new HashMap<>();
        if (CollUtil.isNotEmpty(sysUserGroupBoList)) {
            sysUserRoleBoListByUser = sysUserRoleBoList
                    .stream().collect(Collectors.groupingBy(SysUserRoleBo::getUserId));
        }
        for (UserPageListRes record : records) {
            List<SysUserGroupBo> thisUserGroupList = sysUserGroupBoListByUser.get(record.getId());
            if (null != thisUserGroupList) {
                record.setGroupIdList(thisUserGroupList.stream().map(SysUserGroupBo::getGroupId).toList());
                record.setGroupNames(String.join(",", thisUserGroupList.stream().map(SysUserGroupBo::getName).toList()));
            }
            List<SysUserRoleBo> thisUserRoleList = sysUserRoleBoListByUser.get(record.getId());
            if (null != thisUserRoleList) {
                record.setRoleIdList(thisUserRoleList.stream().map(SysUserRoleBo::getRoleId).toList());
                record.setRoleNames(String.join(",", thisUserRoleList.stream().map(SysUserRoleBo::getName).toList()));
            }
        }
        return page;
    }
}
