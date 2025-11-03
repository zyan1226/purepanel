package cn.yzdoit.purepanel.pojo.res;

import cn.yzdoit.purepanel.pojo.entity.SysPermission;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 获取树形列表接口响应参数
 *
 * @author 闫政
 * @since 2025/6/17 17:18 星期二
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPermissionTreeListRes extends SysPermission implements Serializable {

    @Serial
    private static final long serialVersionUID = -699473124777223291L;

    /**
     * 子级
     */
    private List<SysPermissionTreeListRes> children;
}
