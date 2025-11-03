package cn.yzdoit.purepanel.service;

import cn.yzdoit.purepanel.pojo.entity.SysPermission;
import cn.yzdoit.purepanel.pojo.res.GetUserPermissionRes;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 权限定义相关服务
 *
 * @author 闫政
 * @since 2025/6/17 15:06 星期二
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 获取树形列表
     *
     * @param title 标题
     * @param path  路径
     * @param type  类型
     * @return list
     */
    List<?> allTreeList(String title, String path, Integer type);

    /**
     * 获取用户权限
     *
     * @param userId 用户id
     * @return GetUserPermissionRes
     */
    GetUserPermissionRes getUserPermission(String userId);


    /**
     * 保存菜单权限定义
     *
     * @param sysPermission 菜单权限信息
     */
    void addOrUpdate(SysPermission sysPermission);

    /**
     * 递归删除
     *
     * @param idList 权限ID
     */
    void recurDelete(List<String> idList);
}
