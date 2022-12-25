package cn.lzscxb.framework.web.service;

import cn.lzscxb.common.exception.user.CaptchaException;
import cn.lzscxb.common.exception.user.CaptchaExpireException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.lzscxb.domain.constant.CacheConstants;
import cn.lzscxb.domain.constant.Constants;
import cn.lzscxb.domain.constant.UserConstants;
import cn.lzscxb.domain.entity.FengUsers;
import cn.lzscxb.domain.model.RegisterBody;
import cn.lzscxb.common.core.redis.RedisCache;
import cn.lzscxb.common.utils.MessageUtils;
import cn.lzscxb.common.utils.SecurityUtils;
import cn.lzscxb.domain.utils.StringUtils;
import cn.lzscxb.framework.manager.AsyncManager;
import cn.lzscxb.framework.manager.factory.AsyncFactory;
import cn.lzscxb.system.service.ISysConfigService;
import cn.lzscxb.system.service.IFengUsersService;

/**
 * 注册校验方法
 *
 * @author Likfees
 */
@Component
public class SysRegisterService {
    @Autowired
    private IFengUsersService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody) {
        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();
        FengUsers fengUsers = new FengUsers();
        fengUsers.setUserName(username);
        fengUsers.setCollegeId((long) registerBody.getCollegeId());
        fengUsers.setClassId((long) registerBody.getClassId());
        fengUsers.setNickName(registerBody.getNickName());
        fengUsers.setStudentNumber(registerBody.getStudentNumber());
        fengUsers.setRoleIds(new Long[]{ 101L });

        // 验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username)) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(password)) {
            msg = "用户密码不能为空";
        } else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(fengUsers))) {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        } else if (registerBody.getCollegeId() <= 0) {
            msg = "请选择学院";
        } else if (registerBody.getClassId() <= 0) {
            msg = "请选择班级";
        }  else if (registerBody.getNickName().equals("")) {
            msg = "请输入昵称";
        }  else if (registerBody.getStudentNumber().equals("")) {
            msg = "请输入学号";
        } else {
            fengUsers.setPassword(SecurityUtils.encryptPassword(password));
            boolean regFlag = userService.registerUser(fengUsers);
            if (!regFlag) {
                msg = "注册失败,请联系系统管理人员";
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }
}
