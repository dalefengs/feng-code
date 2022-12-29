package cn.lzscxb.domain.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 每日一题对象 feng_problem_everyday
 * 
 * @author Likfees
 * @date 2022-12-29
 */
@Data
public class FengProblemEveryday extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 题目id */
    @Excel(name = "题目id")
    private Long problemid;

    /** 题目标题 */
    @Excel(name = "题目标题")
    private String title;

    /** 当天时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "当天时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dateDay;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProblemid(Long problemid) 
    {
        this.problemid = problemid;
    }

    public Long getProblemid() 
    {
        return problemid;
    }
    public void setDateDay(Date dateDay) 
    {
        this.dateDay = dateDay;
    }

    public Date getDateDay() 
    {
        return dateDay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("problemid", getProblemid())
            .append("dateDay", getDateDay())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
