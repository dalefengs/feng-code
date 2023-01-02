package cn.lzscxb.domain.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学习任务学生参与对象 feng_task_join
 * 
 * @author Likfees
 * @date 2022-12-25
 */
@Data
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
    private Long userId;

    /** 学习任务 */
    private Long taskId;

    private FengTask fengTaskInfo;

    /** 学习任务 */
    private List<Long> classIds;

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

    private String nickName;
    private String className;
    private Long teacherId;



    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
