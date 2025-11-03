package cn.yzdoit.purepanel.pojo.res;

import cn.yzdoit.purepanel.pojo.entity.SysGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * 群组信息树形列表接口响应参数
 *
 * @author 闫政
 * @since 2025/7/7 05:30 星期一
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysGroupTreeListRes extends SysGroup {

    @Serial
    private static final long serialVersionUID = 4102902914868377367L;

    /**
     * 子级
     */
    private List<SysGroupTreeListRes> children;
}
