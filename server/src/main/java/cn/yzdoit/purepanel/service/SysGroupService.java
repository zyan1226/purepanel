package cn.yzdoit.purepanel.service;

import cn.yzdoit.purepanel.pojo.entity.SysGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 群组信息相关服务
 *
 * @author 闫政
 * @since 2025/7/7 05:22 星期一
 */
public interface SysGroupService extends IService<SysGroup> {

    /**
     * 获取树形列表
     *
     * @param name 群组名称模糊查询
     * @return 树形列表
     */
    List<?> allTreeList(String name);

}
