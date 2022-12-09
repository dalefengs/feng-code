package cn.lzscxb.business.service.impl;

import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
import cn.lzscxb.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengProblemQueueMapper;
import cn.lzscxb.domain.entity.FengProblemQueue;
import cn.lzscxb.business.service.IFengProblemQueueService;

/**
 * 任务管理Service业务层处理
 * 
 * @author Likfees
 * @date 2022-12-08
 */
@Service
public class FengProblemQueueServiceImpl implements IFengProblemQueueService 
{
    @Autowired
    private FengProblemQueueMapper fengProblemQueueMapper;

    /**
     * 查询任务管理
     * 
     * @param id 任务管理主键
     * @return 任务管理
     */
    @Override
    public FengProblemQueue selectFengProblemQueueById(Long id)
    {
        return fengProblemQueueMapper.selectFengProblemQueueById(id);
    }

    /**
     * 查询任务管理列表
     * 
     * @param fengProblemQueue 任务管理
     * @return 任务管理
     */
    @Override
    public List<FengProblemQueue> selectFengProblemQueueList(FengProblemQueue fengProblemQueue)
    {
        return fengProblemQueueMapper.selectFengProblemQueueList(fengProblemQueue);
    }

    /**
     * 新增任务管理
     * 
     * @param fengProblemQueue 任务管理
     * @return 结果
     */
    @Override
    public int insertFengProblemQueue(FengProblemQueue fengProblemQueue)
    {
        fengProblemQueue.setCreateTime(DateUtils.getNowDate());
        fengProblemQueue.setUserId(SecurityUtils.getUserId());
        return fengProblemQueueMapper.insertFengProblemQueue(fengProblemQueue);
    }

    /**
     * 修改任务管理
     * 
     * @param fengProblemQueue 任务管理
     * @return 结果
     */
    @Override
    public int updateFengProblemQueue(FengProblemQueue fengProblemQueue)
    {
        fengProblemQueue.setUpdateTime(DateUtils.getNowDate());
        return fengProblemQueueMapper.updateFengProblemQueue(fengProblemQueue);
    }

    /**
     * 批量删除任务管理
     * 
     * @param ids 需要删除的任务管理主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemQueueByIds(Long[] ids)
    {
        return fengProblemQueueMapper.deleteFengProblemQueueByIds(ids);
    }

    /**
     * 删除任务管理信息
     * 
     * @param id 任务管理主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemQueueById(Long id)
    {
        return fengProblemQueueMapper.deleteFengProblemQueueById(id);
    }
}
