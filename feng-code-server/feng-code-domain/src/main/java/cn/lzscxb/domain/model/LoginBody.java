package cn.lzscxb.domain.model;

import lombok.Data;

/**
 * 用户登录对象
 *
 * @author Likfees
 */
@Data
public class LoginBody {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 学院ID
     */
    private int collegeId;

    /**
     * 班级ID
     */
    private int classId;

    /**
     * 唯一标识
     */
    private String uuid;

}
