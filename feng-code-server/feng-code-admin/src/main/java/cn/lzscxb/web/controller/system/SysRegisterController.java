package cn.lzscxb.web.controller.system;

import cn.lzscxb.common.core.domain.model.RegisterBody;
import cn.lzscxb.common.utils.StringUtils;
import cn.lzscxb.framework.web.service.SysRegisterService;
import cn.lzscxb.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cn.lzscxb.common.core.controller.BaseController;
import cn.lzscxb.common.core.domain.AjaxResult;

/**
 * 注册验证
 * 
 * @author Likfees
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
