package cn.lzscxb.business.domain;

import cn.lzscxb.common.annotation.Excel;
import cn.lzscxb.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 班级管理对象 feng_class
 * 
 * @author Likfees
 * @date 2022-10-14
 */
@Data
public class FengClass extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 专业名称 */
    @Excel(name = "班级名称")
    private String name;

    /** 学院id */
    private Long collegeId;

    /** 专业名称 */
    @Excel(name = "所属名称")
    private String collegeName;


    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCollegeId(Long collegeId) 
    {
        this.collegeId = collegeId;
    }

    public Long getCollegeId() 
    {
        return collegeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("collegeId", getCollegeId())
            .append("collegeName", getCollegeName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
