package cn.lzscxb.business.mapper;

import java.util.List;
import cn.lzscxb.domain.entity.FengTask;

/**
 * 学习任务Mapper接口
 * 
 * @author Likfees
 * @date 2022-12-24
 */
public interface FengTaskMapper 
{
    /**
     * 查询学习任务
     * 
     * @param id 学习任务主键
     * @return 学习任务
     */
    public FengTask selectFengTaskById(FengTask fengTask);

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
     * 删除学习任务
     * 
     * @param id 学习任务主键
     * @return 结果
     */
    public int deleteFengTaskById(Long id);

    /**
     * 批量删除学习任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengTaskByIds(Long[] ids);
}
