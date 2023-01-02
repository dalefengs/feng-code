package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学习任务对象 feng_task
 *
 * @author Likfees
 * @date 2022-12-24
 */
@Data
public class FengTask extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 创建任务的教师id
     */
    private Long teacherId;

    /**
     * 当前用户Id
     */
    private Long CurrentUserId;

    /**
     * 学习任务名称
     */
    @Excel(name = "学习任务名称")
    private String title;

    /**
     * 子标题
     */
    @Excel(name = "子标题")
    private String subTitle;

    /**
     * 任务图片
     */
    @Excel(name = "任务图片")
    private String imgUrl;

    @Excel(name = "任务简介说明")
    private String taskExplain;

    private String isJoin;

    private Long TaskJoinId;

    public Long getTaskJoinId() {
        return TaskJoinId;
    }

    public void setTaskJoinId(Long taskJoinId) {
        TaskJoinId = taskJoinId;
    }

    public String getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }
}
