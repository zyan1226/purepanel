package cn.yzdoit.purepanel.pojo.bo;


import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * 双参数
 *
 * @author 闫政
 * @since 2025/6/23 19:11 周一
 */
@Data
@SuperBuilder
public class TwoParams<A, B> {

    private A a;
    private B b;
}
