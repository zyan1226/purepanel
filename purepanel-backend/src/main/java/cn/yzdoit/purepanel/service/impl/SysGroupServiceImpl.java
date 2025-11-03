package cn.yzdoit.purepanel.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yzdoit.purepanel.mapper.SysGroupMapper;
import cn.yzdoit.purepanel.pojo.entity.SysGroup;
import cn.yzdoit.purepanel.pojo.res.SysGroupTreeListRes;
import cn.yzdoit.purepanel.service.SysGroupService;
import cn.yzdoit.purepanel.util.TreeListUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 群组信息相关服务
 *
 * @author 闫政
 * @since 2025/7/7 05:23 星期一
 */
@Service
@RequiredArgsConstructor
public class SysGroupServiceImpl extends ServiceImpl<SysGroupMapper, SysGroup> implements SysGroupService {

    private final SysGroupMapper sysGroupMapper;

    /**
     * 获取树形列表
     *
     * @param name 群组名称模糊查询
     * @return 树形列表
     */
    @Override
    public List<?> allTreeList(String name) {
        List<SysGroup> list = sysGroupMapper.selectList(Wrappers.<SysGroup>lambdaQuery()
                .like(StrUtil.isNotBlank(name), SysGroup::getName, name)
                .orderByAsc(SysGroup::getOrderNo));
        //有查询条件，平铺返回
        if (StrUtil.isNotBlank(name)) {
            return list;
        }
        //没有查询条件，返回树形结构
        return TreeListUtil.toTree(list, SysGroup::getId, SysGroup::getPid
                , SysGroupTreeListRes::setChildren, SysGroupTreeListRes.class);
    }
}
