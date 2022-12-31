package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 题目评论对象 feng_problem_comment
 *
 * @author Likfees
 * @date 2022-10-16
 */
@Data
public class FengProblemComment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private Long id;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private Long userId;

    /**
     * 上级评论id (用于多级评论)
     */
    @Excel(name = "上级评论id (用于多级评论)")
    private Long pid;

    /**
     * 回复用户id
     */
    @Excel(name = "回复用户id")
    private Long replyUserId;

    /**
     * 问题id
     */
    @Excel(name = "问题id")
    private Long problemId;

    /**
     * 评论内容
     */
    @Excel(name = "评论内容")
    private String content;

    private List<FengProblemComment> children;


    private String nickName;
    private String problemTitle;
    private Integer giveCount;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGiveCount() {
        return giveCount;
    }

    public void setGiveCount(Integer giveCount) {
        this.giveCount = giveCount;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
