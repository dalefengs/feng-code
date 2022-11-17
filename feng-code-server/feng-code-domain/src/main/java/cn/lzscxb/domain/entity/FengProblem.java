package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 题目管理对象 feng_problem
 * 
 * @author Likfees
 * @date 2022-11-17
 */
public class FengProblem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 题目标题 */
    @Excel(name = "题目标题")
    private String title;

    /** 作者 */
    @Excel(name = "作者")
    private Long userId;

    /** 难度等级 */
    @Excel(name = "难度等级")
    private Integer level;

    /** 点赞 */
    @Excel(name = "点赞")
    private Long likeCount;

    /** 题目说明 */
    @Excel(name = "题目说明")
    private String description;

    /** 分类id */
    @Excel(name = "分类id")
    private Long categoryId;

    /** 标签id */
    @Excel(name = "标签id")
    private Long tagId;

    /** 提示 */
    @Excel(name = "提示")
    private String hint;

    /** 提交次数 */
    @Excel(name = "提交次数")
    private Long submitCount;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 通过次数 */
    @Excel(name = "通过次数")
    private Long successCount;

    /** 测试用例文件地址 */
    private String testcastUrl;

    /** 方法名 */
    private String methodName;

    /** 参数名，多个参数使用;号分隔(类型:名称;类型:名称) 默认使用Java类型 */
    private String paramNames;

    /** 0自动批阅(执行测试用例) 1教师批阅 */
    private Integer isAuto;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setLevel(Integer level) 
    {
        this.level = level;
    }

    public Integer getLevel() 
    {
        return level;
    }
    public void setLikeCount(Long likeCount) 
    {
        this.likeCount = likeCount;
    }

    public Long getLikeCount() 
    {
        return likeCount;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setTagId(Long tagId) 
    {
        this.tagId = tagId;
    }

    public Long getTagId() 
    {
        return tagId;
    }
    public void setHint(String hint) 
    {
        this.hint = hint;
    }

    public String getHint() 
    {
        return hint;
    }
    public void setSubmitCount(Long submitCount) 
    {
        this.submitCount = submitCount;
    }

    public Long getSubmitCount() 
    {
        return submitCount;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setSuccessCount(Long successCount) 
    {
        this.successCount = successCount;
    }

    public Long getSuccessCount() 
    {
        return successCount;
    }
    public void setTestcastUrl(String testcastUrl) 
    {
        this.testcastUrl = testcastUrl;
    }

    public String getTestcastUrl() 
    {
        return testcastUrl;
    }
    public void setMethodName(String methodName) 
    {
        this.methodName = methodName;
    }

    public String getMethodName() 
    {
        return methodName;
    }
    public void setParamNames(String paramNames) 
    {
        this.paramNames = paramNames;
    }

    public String getParamNames() 
    {
        return paramNames;
    }
    public void setIsAuto(Integer isAuto) 
    {
        this.isAuto = isAuto;
    }

    public Integer getIsAuto() 
    {
        return isAuto;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("userId", getUserId())
            .append("level", getLevel())
            .append("likeCount", getLikeCount())
            .append("description", getDescription())
            .append("categoryId", getCategoryId())
            .append("tagId", getTagId())
            .append("hint", getHint())
            .append("submitCount", getSubmitCount())
            .append("sort", getSort())
            .append("successCount", getSuccessCount())
            .append("testcastUrl", getTestcastUrl())
            .append("methodName", getMethodName())
            .append("paramNames", getParamNames())
            .append("isAuto", getIsAuto())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
