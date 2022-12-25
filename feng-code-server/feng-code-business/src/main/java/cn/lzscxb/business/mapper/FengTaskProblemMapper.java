package cn.lzscxb.business.mapper;

import java.util.List;
import cn.lzscxb.domain.entity.FengTaskProblem;

/**
 * 学习任务与题目关联Mapper接口
 * 
 * @author Likfees
 * @date 2022-12-25
 */
public interface FengTaskProblemMapper 
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
     * 删除学习任务与题目关联
     * 
     * @param id 学习任务与题目关联主键
     * @return 结果
     */
    public int deleteFengTaskProblemById(Long id);

    /**
     * 批量删除学习任务与题目关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengTaskProblemByIds(Long[] ids);
}
