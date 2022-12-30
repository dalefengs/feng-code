package cn.lzscxb.business.mapper;

import java.util.HashSet;
import java.util.List;

import cn.lzscxb.domain.entity.FengProblem;
import cn.lzscxb.domain.entity.FengProblemQueue;

/**
 * 任务管理Mapper接口
 * 
 * @author Likfees
 * @date 2022-12-08
 */
public interface FengProblemQueueMapper 
{
    /**
     * 查询任务管理
     * 
     * @param id 任务管理主键
     * @return 任务管理
     */
    public FengProblemQueue selectFengProblemQueueById(Long id);

    /**
     * 查询任务管理列表
     * 
     * @param fengProblemQueue 任务管理
     * @return 任务管理集合
     */
    public List<FengProblemQueue> selectFengProblemQueueList(FengProblemQueue fengProblemQueue);

    /**
     * 新增任务管理
     * 
     * @param fengProblemQueue 任务管理
     * @return 结果
     */
    public long insertFengProblemQueue(FengProblemQueue fengProblemQueue);

    /**
     * 修改任务管理
     * 
     * @param fengProblemQueue 任务管理
     * @return 结果
     */
    public int updateFengProblemQueue(FengProblemQueue fengProblemQueue);

    /**
     * 删除任务管理
     * 
     * @param id 任务管理主键
     * @return 结果
     */
    public int deleteFengProblemQueueById(Long id);

    /**
     * 批量删除任务管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengProblemQueueByIds(Long[] ids);

    List<FengProblemQueue> selectSubmitList(FengProblemQueue fengProblemQueue);

    HashSet<Integer> selectProblemQuqueStatusList(FengProblemQueue problemQueue);

    List<FengProblemQueue> selectFengProblemRankList(FengProblemQueue fengProblemQueue);

}
