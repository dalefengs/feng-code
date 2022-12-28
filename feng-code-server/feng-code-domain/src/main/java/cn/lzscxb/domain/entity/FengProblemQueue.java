package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import cn.lzscxb.domain.model.ExecuteResult;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 任务管理对象 feng_problem_queue
 *
 * @author Likfees
 * @date 2022-12-08
 */
@Data
public class FengProblemQueue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 用户名 */
    @Excel(name = "用户名")
    private Long userId;

    /** 题目 */
    @Excel(name = "题目")
    private Long problemId;

    /** 学习任务 */
    @Excel(name = "学习任务")
    private Long taskId;

    /** 学生参与学习任务 */
    @Excel(name = "学生参与学习任务")
    private Long taskJoinId;

    /** 语言类型 */
    @Excel(name = "语言类型")
    private int type;

    /** 代码 */
    private String code;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 成功信息 */
    private String successMsg;

    /** 错误信息 */
    private String errorMsg;

    private ExecuteResult executeResult;

    private String title;

    private String nickname;

    private int retryCount;

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
