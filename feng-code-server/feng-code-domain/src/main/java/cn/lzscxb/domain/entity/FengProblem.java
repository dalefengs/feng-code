package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 题目管理对象 feng_problem
 * 
 * @author Likfees
 * @date 2022-11-21
 */
public class FengProblem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 题目标题 */
    @Excel(name = "题目标题")
    private String title;

    /** 作者 */
    private Long userId;

    /** 题目等级(0简单 1中等 2困难) */
    @Excel(name = "题目等级(0简单 1中等 2困难)")
    private Integer level;

    /** 点赞 */
    @Excel(name = "点赞")
    private Long likeCount;

    /** 题目说明 */
    private String description;

    /** 分类id */
    @Excel(name = "分类id")
    private Long categoryId;

    /** 标签id 一个问题属于一个标签 */
    @Excel(name = "标签id 一个问题属于一个标签")
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

    /** 0自动批阅(执行测试用例) 1教师批阅 */
    @Excel(name = "0自动批阅(执行测试用例) 1教师批阅")
    private Integer isAuto;

    /** 通过次数 */
    @Excel(name = "通过次数")
    private Long successCount;

    /** 支持的语言类型 */
    @Excel(name = "支持的语言类型")
    private String language;

    /** 方法名 */
    @Excel(name = "方法名")
    private String methodNames;

    /** 参数类型名称 */
    @Excel(name = "参数类型名称")
    private String paramTypes;

    /** 代码模版 */
    @Excel(name = "代码模版")
    private String codeTemplates;

    /** 测试用例 */
    @Excel(name = "测试用例")
    private String testCase;

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
    public void setIsAuto(Integer isAuto) 
    {
        this.isAuto = isAuto;
    }

    public Integer getIsAuto() 
    {
        return isAuto;
    }
    public void setSuccessCount(Long successCount) 
    {
        this.successCount = successCount;
    }

    public Long getSuccessCount() 
    {
        return successCount;
    }
    public void setLanguage(String language) 
    {
        this.language = language;
    }

    public String getLanguage() 
    {
        return language;
    }
    public void setMethodNames(String methodNames) 
    {
        this.methodNames = methodNames;
    }

    public String getMethodNames() 
    {
        return methodNames;
    }
    public void setParamTypes(String paramTypes) 
    {
        this.paramTypes = paramTypes;
    }

    public String getParamTypes() 
    {
        return paramTypes;
    }
    public void setCodeTemplates(String codeTemplates) 
    {
        this.codeTemplates = codeTemplates;
    }

    public String getCodeTemplates() 
    {
        return codeTemplates;
    }
    public void setTestCase(String testCase) 
    {
        this.testCase = testCase;
    }

    public String getTestCase() 
    {
        return testCase;
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
            .append("isAuto", getIsAuto())
            .append("successCount", getSuccessCount())
            .append("language", getLanguage())
            .append("methodNames", getMethodNames())
            .append("paramTypes", getParamTypes())
            .append("codeTemplates", getCodeTemplates())
            .append("testCase", getTestCase())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
