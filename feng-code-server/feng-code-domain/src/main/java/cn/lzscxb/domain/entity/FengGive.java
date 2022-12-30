package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 点赞对象 feng_give
 * 
 * @author Likfees
 * @date 2022-12-30
 */
public class FengGive extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 题目ID */
    @Excel(name = "题目ID")
    private Long problemId;

    /** 评论ID */
    @Excel(name = "评论ID")
    private Long commentId;

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
    public void setCommentId(Long commentId) 
    {
        this.commentId = commentId;
    }

    public Long getCommentId() 
    {
        return commentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("problemId", getProblemId())
            .append("commentId", getCommentId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
