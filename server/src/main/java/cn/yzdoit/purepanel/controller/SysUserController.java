package cn.yzdoit.purepanel.controller;

import cn.hutool.core.util.StrUtil;
import cn.yzdoit.purepanel.constant.CacheName;
import cn.yzdoit.purepanel.exception.BusinessException;
import cn.yzdoit.purepanel.pojo.entity.SysUser;
import cn.yzdoit.purepanel.pojo.entity.SysUserGroup;
import cn.yzdoit.purepanel.pojo.entity.SysUserRole;
import cn.yzdoit.purepanel.pojo.properties.PurepanelProperties;
import cn.yzdoit.purepanel.pojo.req.ChangePwdReq;
import cn.yzdoit.purepanel.pojo.req.UserPageListReq;
import cn.yzdoit.purepanel.pojo.req.UserSaveReq;
import cn.yzdoit.purepanel.pojo.res.Res;
import cn.yzdoit.purepanel.pojo.res.UserPageListRes;
import cn.yzdoit.purepanel.service.SysUserGroupService;
import cn.yzdoit.purepanel.service.SysUserRoleService;
import cn.yzdoit.purepanel.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息相关接口
 *
 * @author 闫政
 * @since 2025/6/23 06:44 星期一
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "用户信息相关接口")
@RequestMapping("/sysUser")
public class SysUserController {

    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;
    private final SysUserGroupService sysUserGroupService;
    private final PurepanelProperties purepanelProperties;


    @PostMapping("/pageList")
    @Operation(summary = "分页查询用户信息")
    public Res<IPage<UserPageListRes>> pageList(@RequestBody @Validated UserPageListReq req) {
        return Res.success(sysUserService.pageList(req));
    }

    @PostMapping("/save")
    @Operation(summary = "保存用户信息")
    @CacheEvict(value = CacheName.LIST_GROUP_AND_CHILDREN_BY_USER_ID, key = "#req.id")
    public Res<?> save(@RequestBody UserSaveReq req) {
        sysUserService.save(req);
        return Res.success();
    }

    @PostMapping("/selfEdit")
    @Operation(summary = "登录用户自己编辑个人信息")
    public Res<?> selfEdit(@RequestAttribute String loginUserId, @RequestBody SysUser req) {
        req.setId(loginUserId);
        try {
            sysUserService.updateById(req);
        } catch (DuplicateKeyException e) {
            throw new BusinessException("账号 " + req.getAccount() + " 不可用，请换一个");
        }
        return Res.success();
    }

    @PostMapping("/changeSelfPwd")
    @Operation(summary = "登录用户自行修改密码")
    public Res<?> changeSelfPwd(@RequestAttribute String loginUserId, @RequestBody ChangePwdReq req) {
        req.setUserId(loginUserId);
        req.setIsSelfChange(true);
        sysUserService.changePwd(req);
        return Res.success();
    }

    @PostMapping("/changePwd")
    @Operation(summary = "修改指定用户密码")
    public Res<?> changePwd(@RequestBody ChangePwdReq req) {
        req.setIsSelfChange(false);
        sysUserService.changePwd(req);
        return Res.success();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除指定用户")
    @Transactional(rollbackFor = Exception.class)
    public Res<?> delete(@PathVariable String id) {
        if (StrUtil.equals(id, purepanelProperties.getRootUserId())) {
            return Res.fail("该用户为系统保留用户，不允许删除");
        }
        sysUserService.removeById(id);
        sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, id));
        sysUserGroupService.remove(Wrappers.<SysUserGroup>lambdaQuery()
                .eq(SysUserGroup::getUserId, id));
        return Res.success();
    }
}
