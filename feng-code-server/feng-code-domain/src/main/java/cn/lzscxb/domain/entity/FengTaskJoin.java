package cn.lzscxb.domain.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学习任务学生参与对象 feng_task_join
 * 
 * @author Likfees
 * @date 2022-12-25
 */
public class FengTaskJoin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 班级id */
    @Excel(name = "班级id")
    private Long classId;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    /** 学习任务 */
    private Long taskId;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 正确题目数量 */
    @Excel(name = "正确题目数量")
    private Integer correctCount;

    /** 分数 */
    @Excel(name = "分数")
    private Integer score;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 答题时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "答题时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitTime;

    /** 批阅时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "批阅时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setClassId(Long classId) 
    {
        this.classId = classId;
    }

    public Long getClassId() 
    {
        return classId;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setCorrectCount(Integer correctCount) 
    {
        this.correctCount = correctCount;
    }

    public Integer getCorrectCount() 
    {
        return correctCount;
    }
    public void setScore(Integer score) 
    {
        this.score = score;
    }

    public Integer getScore() 
    {
        return score;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setSubmitTime(Date submitTime) 
    {
        this.submitTime = submitTime;
    }

    public Date getSubmitTime() 
    {
        return submitTime;
    }
    public void setCheckTime(Date checkTime) 
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() 
    {
        return checkTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("classId", getClassId())
            .append("userId", getUserId())
            .append("taskId", getTaskId())
            .append("status", getStatus())
            .append("correctCount", getCorrectCount())
            .append("score", getScore())
            .append("endTime", getEndTime())
            .append("submitTime", getSubmitTime())
            .append("checkTime", getCheckTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
