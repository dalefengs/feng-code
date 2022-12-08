package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 任务管理对象 feng_problem_queue
 * 
 * @author Likfees
 * @date 2022-12-08
 */
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
    private Long type;

    /** 代码 */
    private String code;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 成功信息 */
    private String successMsg;

    /** 错误信息 */
    private String errorMsg;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setProblemId(Long problemId) 
    {
        this.problemId = problemId;
    }

    public Long getProblemId() 
    {
        return problemId;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setTaskJoinId(Long taskJoinId) 
    {
        this.taskJoinId = taskJoinId;
    }

    public Long getTaskJoinId() 
    {
        return taskJoinId;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setSuccessMsg(String successMsg) 
    {
        this.successMsg = successMsg;
    }

    public String getSuccessMsg() 
    {
        return successMsg;
    }
    public void setErrorMsg(String errorMsg) 
    {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() 
    {
        return errorMsg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("problemId", getProblemId())
            .append("taskId", getTaskId())
            .append("taskJoinId", getTaskJoinId())
            .append("type", getType())
            .append("code", getCode())
            .append("status", getStatus())
            .append("successMsg", getSuccessMsg())
            .append("errorMsg", getErrorMsg())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
