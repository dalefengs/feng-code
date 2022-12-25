package cn.lzscxb.domain.entity;

import cn.lzscxb.domain.annotation.Excel;
import cn.lzscxb.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学习任务与题目关联对象 feng_task_problem
 * 
 * @author Likfees
 * @date 2022-12-25
 */
@Data
public class FengTaskProblem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 学习任务id */
    private Long taskId;

    /** 题目id */
    @Excel(name = "题目id")
    private Long problemId;

    /** 题目ids */
    @Excel(name = "题目id")
    private Long[] problemIds;

    private String problemTitle;


    /** 排序 */
    @Excel(name = "排序")
    private Long soft;


}
