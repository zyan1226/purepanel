package cn.yzdoit.purepanel.service;

import cn.yzdoit.purepanel.pojo.entity.SysUser;
import cn.yzdoit.purepanel.pojo.req.ChangePwdReq;
import cn.yzdoit.purepanel.pojo.req.UserSaveReq;
import cn.yzdoit.purepanel.pojo.req.UserPageListReq;
import cn.yzdoit.purepanel.pojo.res.UserPageListRes;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户信息相关服务
 *
 * @author 闫政
 * @since 2025/6/23 06:43 星期一
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 修改密码
     *
     * @param req 修改密码请求
     */
    void changePwd(ChangePwdReq req);


    /**
     * 新增或修改用户信息
     *
     * @param req 用户信息
     */
    void save(UserSaveReq req);


    /**
     * 分页查询用户信息
     *
     * @param req 分页查询用户信息请求参数
     * @return 用户信息分页
     */
    IPage<UserPageListRes> pageList(UserPageListReq req);
}
