package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import cn.lzscxb.domain.model.ExecuteResult;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 任务管理对象 feng_problem_queue
 *
 * @author Likfees
 * @date 2022-12-08
 */
@Data
public class FengProblemQueue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 用户名 */
    @Excel(name = "用户名")
    private Long userId;

    /** 当前登录教师id */
    private Long currenTeacherId;

    /** 题目 */
    @Excel(name = "题目")
    private Long problemId;

    /** 学习任务 */
    @Excel(name = "学习任务")
    private Long taskId;

    /** 每日一题 */
    @Excel(name = "每日一题")
    private Long everydayId;

    /** 学生参与学习任务 */
    @Excel(name = "学生参与学习任务")
    private Long taskJoinId;

    /** 语言类型 */
    private int type;

    private Long socre;

    /** 语言类型 */
    @Excel(name = "语言类型")
    private String typeName;

    /** 代码 */
    private String code;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 是否自动批阅 */
    private Integer isAuto;

    /** 成功信息 */
    private String successMsg;

    /** 错误信息 */
    private String errorMsg;

    private ExecuteResult executeResult;

    private String title;

    private String nickname;

    private int retryCount;

    private String totalCount;

    private String successCount;

    private Integer ranking;

    private String avatar;
    private String taskTitle;
    private String className;
    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(String successCount) {
        this.successCount = successCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
