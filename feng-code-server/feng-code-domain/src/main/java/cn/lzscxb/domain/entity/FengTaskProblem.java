package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学习任务与题目关联对象 feng_task_problem
 * 
 * @author Likfees
 * @date 2022-12-25
 */
public class FengTaskProblem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 学习任务id */
    private String taskId;

    /** 题目id */
    @Excel(name = "题目id")
    private String problemId;

    /** 排序 */
    @Excel(name = "排序")
    private Long soft;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskId(String taskId) 
    {
        this.taskId = taskId;
    }

    public String getTaskId() 
    {
        return taskId;
    }
    public void setProblemId(String problemId) 
    {
        this.problemId = problemId;
    }

    public String getProblemId() 
    {
        return problemId;
    }
    public void setSoft(Long soft) 
    {
        this.soft = soft;
    }

    public Long getSoft() 
    {
        return soft;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskId", getTaskId())
            .append("problemId", getProblemId())
            .append("soft", getSoft())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
