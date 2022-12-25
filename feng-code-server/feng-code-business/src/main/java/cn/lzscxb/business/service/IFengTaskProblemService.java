package cn.lzscxb.business.service;

import java.util.List;
import cn.lzscxb.domain.entity.FengTaskProblem;

/**
 * 学习任务与题目关联Service接口
 * 
 * @author Likfees
 * @date 2022-12-25
 */
public interface IFengTaskProblemService 
{
    /**
     * 查询学习任务与题目关联
     * 
     * @param id 学习任务与题目关联主键
     * @return 学习任务与题目关联
     */
    public FengTaskProblem selectFengTaskProblemById(Long id);

    /**
     * 查询学习任务与题目关联列表
     * 
     * @param fengTaskProblem 学习任务与题目关联
     * @return 学习任务与题目关联集合
     */
    public List<FengTaskProblem> selectFengTaskProblemList(FengTaskProblem fengTaskProblem);

    /**
     * 新增学习任务与题目关联
     * 
     * @param fengTaskProblem 学习任务与题目关联
     * @return 结果
     */
    public int insertFengTaskProblem(FengTaskProblem fengTaskProblem);

    /**
     * 修改学习任务与题目关联
     * 
     * @param fengTaskProblem 学习任务与题目关联
     * @return 结果
     */
    public int updateFengTaskProblem(FengTaskProblem fengTaskProblem);

    /**
     * 批量删除学习任务与题目关联
     * 
     * @param ids 需要删除的学习任务与题目关联主键集合
     * @return 结果
     */
    public int deleteFengTaskProblemByIds(Long[] ids);

    /**
     * 删除学习任务与题目关联信息
     * 
     * @param id 学习任务与题目关联主键
     * @return 结果
     */
    public int deleteFengTaskProblemById(Long id);
}
