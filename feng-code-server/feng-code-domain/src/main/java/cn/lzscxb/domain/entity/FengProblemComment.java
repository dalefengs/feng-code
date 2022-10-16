package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 题目评论对象 feng_problem_comment
 * 
 * @author Likfees
 * @date 2022-10-16
 */
public class FengProblemComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 上级评论id (用于多级评论) */
    @Excel(name = "上级评论id (用于多级评论)")
    private Long pid;

    /** 回复用户id */
    @Excel(name = "回复用户id")
    private Long replyUserId;

    /** 问题id */
    @Excel(name = "问题id")
    private Long problemId;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String content;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeCount;

    /** 踩数量 */
    @Excel(name = "踩数量")
    private Long trampleCount;

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
    public void setPid(Long pid) 
    {
        this.pid = pid;
    }

    public Long getPid() 
    {
        return pid;
    }
    public void setReplyUserId(Long replyUserId) 
    {
        this.replyUserId = replyUserId;
    }

    public Long getReplyUserId() 
    {
        return replyUserId;
    }
    public void setProblemId(Long problemId) 
    {
        this.problemId = problemId;
    }

    public Long getProblemId() 
    {
        return problemId;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setLikeCount(Long likeCount) 
    {
        this.likeCount = likeCount;
    }

    public Long getLikeCount() 
    {
        return likeCount;
    }
    public void setTrampleCount(Long trampleCount) 
    {
        this.trampleCount = trampleCount;
    }

    public Long getTrampleCount() 
    {
        return trampleCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("pid", getPid())
            .append("replyUserId", getReplyUserId())
            .append("problemId", getProblemId())
            .append("content", getContent())
            .append("likeCount", getLikeCount())
            .append("trampleCount", getTrampleCount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
