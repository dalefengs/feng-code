package cn.lzscxb.common.utils;

import cn.lzscxb.domain.entity.FengUsers;
import cn.lzscxb.domain.entity.SysRole;
import cn.lzscxb.domain.enums.RoleKey;
import cn.lzscxb.domain.model.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import cn.lzscxb.domain.constant.HttpStatus;
import cn.lzscxb.common.exception.ServiceException;

/**
 * 安全服务工具类
 * 
 * @author Likfees
 */
public class SecurityUtils
{
    /**
     * 用户ID
     **/
    public static Long getUserId()
    {
        try
        {
            return getLoginUser().getUserId();
        }
        catch (Exception e)
        {
            throw new ServiceException("获取用户ID异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户账户
     **/
    public static String getUsername()
    {
        try
        {
            return getLoginUser().getUsername();
        }
        catch (Exception e)
        {
            throw new ServiceException("获取用户账户异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取到当前登陆用户表详情信息
     * @return
     */
    public static FengUsers getLoginUserInfo() {
        return getLoginUser().getUser();
    }

    /**
     * 获取当前登录用户
     **/
    public static LoginUser getLoginUser()
    {
        try
        {
            return (LoginUser) getAuthentication().getPrincipal();
        }
        catch (Exception e)
        {
            throw new ServiceException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    /**
     * 判断当前角色组中是否包含某个角色
     * @param RoleKey
     * @return
     */
    public static boolean isRoleDesignated(RoleKey RoleKey){
        boolean flag = false;
        FengUsers user = getLoginUserInfo();
        for (SysRole role : user.getRoles()) {
            if (role.getRoleKey().equals(RoleKey.getLowerCaseName())){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 传递用户信息判断当前角色组中是否包含某个角色
     * @param user
     * @param RoleKey
     * @return
     */
    public static boolean isRoleDesignated(FengUsers user, RoleKey RoleKey){
        boolean flag = false;
        for (SysRole role : user.getRoles()) {
            if (role.getRoleKey().equals(RoleKey.getLowerCaseName())){
                flag = true;
                break;
            }
        }
        return flag;
    }


}
