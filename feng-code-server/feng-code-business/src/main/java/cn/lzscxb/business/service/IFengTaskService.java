package cn.lzscxb.business.service;

import java.util.List;
import cn.lzscxb.domain.entity.FengTask;
import cn.lzscxb.domain.entity.FengTaskProblem;

/**
 * 学习任务Service接口
 * 
 * @author Likfees
 * @date 2022-12-24
 */
public interface IFengTaskService 
{
    /**
     * 查询学习任务
     * 
     * @param id 学习任务主键
     * @return 学习任务
     */
    public FengTask selectFengTaskById(Long id);

    /**
     * 查询学习任务列表
     * 
     * @param fengTask 学习任务
     * @return 学习任务集合
     */
    public List<FengTask> selectFengTaskList(FengTask fengTask);

    /**
     * 新增学习任务
     * 
     * @param fengTask 学习任务
     * @return 结果
     */
    public int insertFengTask(FengTask fengTask);

    /**
     * 修改学习任务
     * 
     * @param fengTask 学习任务
     * @return 结果
     */
    public int updateFengTask(FengTask fengTask);

    /**
     * 批量删除学习任务
     * 
     * @param ids 需要删除的学习任务主键集合
     * @return 结果
     */
    public int deleteFengTaskByIds(Long[] ids);

    /**
     * 删除学习任务信息
     * 
     * @param id 学习任务主键
     * @return 结果
     */
    public int deleteFengTaskById(Long id);

}
