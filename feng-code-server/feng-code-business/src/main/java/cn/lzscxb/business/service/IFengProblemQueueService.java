package cn.lzscxb.business.service;

import java.util.List;

import cn.lzscxb.domain.entity.FengProblemQueue;
import cn.lzscxb.domain.model.ExecuteResult;

/**
 * 任务管理Service接口
 *
 * @author Likfees
 * @date 2022-12-08
 */
public interface IFengProblemQueueService {

    /**
     * 控制任务执行
     *
     * @param
     * @return
     */
     FengProblemQueue excuteQuque(long id);

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
    public int insertFengProblemQueue(FengProblemQueue fengProblemQueue);

    /**
     * 修改任务管理
     *
     * @param fengProblemQueue 任务管理
     * @return 结果
     */
    public int updateFengProblemQueue(FengProblemQueue fengProblemQueue);

    /**
     * 批量删除任务管理
     *
     * @param ids 需要删除的任务管理主键集合
     * @return 结果
     */
    public int deleteFengProblemQueueByIds(Long[] ids);

    /**
     * 删除任务管理信息
     *
     * @param id 任务管理主键
     * @return 结果
     */
    public int deleteFengProblemQueueById(Long id);

    List<FengProblemQueue> submitList(FengProblemQueue fengProblemQueue);
}
