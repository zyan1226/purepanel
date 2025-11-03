package cn.yzdoit.purepanel.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.yzdoit.purepanel.mapper.SysPermissionMapper;
import cn.yzdoit.purepanel.mapper.SysRolePermissionMapper;
import cn.yzdoit.purepanel.mapper.SysUserRoleMapper;
import cn.yzdoit.purepanel.pojo.entity.SysPermission;
import cn.yzdoit.purepanel.pojo.entity.SysRolePermission;
import cn.yzdoit.purepanel.pojo.entity.SysUserRole;
import cn.yzdoit.purepanel.pojo.properties.PurepanelProperties;
import cn.yzdoit.purepanel.pojo.res.GetUserPermissionRes;
import cn.yzdoit.purepanel.pojo.res.SysPermissionTreeListRes;
import cn.yzdoit.purepanel.service.SysPermissionService;
import cn.yzdoit.purepanel.util.TreeListUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 权限定义相关服务
 *
 * @author 闫政
 * @since 2025/6/17 15:06 星期二
 */
@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;
    private final SysPermissionMapper sysPermissionMapper;
    private final PurepanelProperties purepanelProperties;

    /**
     * 获取树形列表
     *
     * @param title 标题
     * @param path  路径
     * @param type  类型
     * @return list
     */
    @Override
    public List<?> allTreeList(String title, String path, Integer type) {
        List<SysPermission> list = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                .like(StrUtil.isNotBlank(title), SysPermission::getTitle, title)
                .like(StrUtil.isNotBlank(path), SysPermission::getPath, path)
                .eq(type != null, SysPermission::getType, type)
                .orderByAsc(SysPermission::getOrderNo));
        //有查询条件，平铺返回
        if (StrUtil.isNotBlank(title) || StrUtil.isNotBlank(path) || type != null) {
            return list;
        }
        //没有查询条件，返回树形结构
        return TreeListUtil.toTree(list, SysPermission::getId, SysPermission::getPid
                , SysPermissionTreeListRes::setChildren, SysPermissionTreeListRes.class);
    }

    /**
     * 获取用户权限
     *
     * @param userId 用户id
     * @return GetUserPermissionRes
     */
    @Override
    public GetUserPermissionRes getUserPermission(String userId) {
        List<SysPermission> permissionList;
        if (StrUtil.equals(userId, purepanelProperties.getRootUserId())) {
            //根账号查询全部权限
            permissionList = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                    .eq(SysPermission::getIsHidden, false)
                    .orderByAsc(SysPermission::getOrderNo));
        } else {
            //获取用户角色
            List<String> roleIdList = sysUserRoleMapper.selectList(Wrappers.<SysUserRole>lambdaQuery()
                            .select(SysUserRole::getRoleId)
                            .eq(SysUserRole::getUserId, userId))
                    .stream().map(SysUserRole::getRoleId).toList();
            if (CollectionUtil.isEmpty(roleIdList)) {
                return GetUserPermissionRes.empty();
            }
            //获取角色权限
            List<String> permissionIdList = sysRolePermissionMapper.selectList(Wrappers.<SysRolePermission>lambdaQuery()
                            .select(SysRolePermission::getPermissionId)
                            .in(SysRolePermission::getRoleId, roleIdList))
                    .stream().map(SysRolePermission::getPermissionId).toList();
            if (CollectionUtil.isEmpty(permissionIdList)) {
                return GetUserPermissionRes.empty();
            }
            //获取权限信息
            permissionList = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                    .in(SysPermission::getId, permissionIdList)
                    .eq(SysPermission::getIsHidden, false)
                    .orderByAsc(SysPermission::getOrderNo));
        }
        if (CollectionUtil.isEmpty(permissionList)) {
            return GetUserPermissionRes.empty();
        }
        //处理总体权限树
        List<SysPermissionTreeListRes> permissionTree = TreeListUtil.toTree(permissionList, SysPermission::getId, SysPermission::getPid
                , SysPermissionTreeListRes::setChildren, SysPermissionTreeListRes.class);
        //处理菜单树信息
        List<SysPermission> menuList = permissionList.stream()
                .filter(p -> Arrays.asList(0, 2).contains(p.getType())).toList();
        List<SysPermissionTreeListRes> menuTree = TreeListUtil.toTree(menuList, SysPermission::getId, SysPermission::getPid
                , SysPermissionTreeListRes::setChildren, SysPermissionTreeListRes.class);
        //处理按钮信息
        List<SysPermission> pageElementsList = permissionList.stream()
                .filter(p -> Objects.equals(p.getType(), 1)).toList();
        //响应
        return GetUserPermissionRes.builder()
                .permissionTree(permissionTree)
                .menuTree(menuTree)
                .pageElementList(pageElementsList)
                .build();
    }

    /**
     * 保存菜单权限定义
     *
     * @param sysPermission 菜单权限信息
     */
    @Override
    public void addOrUpdate(SysPermission sysPermission) {
        setMenuLevel(sysPermission);
        String id = sysPermission.getId();
        if (StrUtil.isBlank(id)) {
            //新增
            sysPermissionMapper.insert(sysPermission);
        } else {
            sysPermissionMapper.updateById(sysPermission);
        }
    }

    /**
     * 递归删除
     *
     * @param idList 权限ID
     */
    @Override
    public void recurDelete(List<String> idList) {
        sysPermissionMapper.deleteByIds(idList);
        sysRolePermissionMapper.delete(Wrappers.<SysRolePermission>lambdaQuery()
                .in(SysRolePermission::getPermissionId, idList));
        //递归删除子节点
        List<SysPermission> subSysPermissionList = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                .in(SysPermission::getPid, idList));
        if (CollectionUtil.isNotEmpty(subSysPermissionList)) {
            this.recurDelete(subSysPermissionList.stream().map(SysPermission::getId).toList());
        }
    }

    /**
     * 计算并设置菜单层级
     *
     * @param sysPermission 菜单权限信息
     */
    private void setMenuLevel(SysPermission sysPermission) {
        if (StrUtil.isBlank(sysPermission.getPid())
                && sysPermission.getType() == 0) {
            sysPermission.setMenuLevel(2);
        } else if (StrUtil.isBlank(sysPermission.getPid())
                && sysPermission.getType() == 2) {
            sysPermission.setMenuLevel(1);
        } else {
            SysPermission parentSysPermission = sysPermissionMapper.selectById(sysPermission.getPid());
            sysPermission.setMenuLevel(parentSysPermission.getMenuLevel() + 1);
        }
    }
}
