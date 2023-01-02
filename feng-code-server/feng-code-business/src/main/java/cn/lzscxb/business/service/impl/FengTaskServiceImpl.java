package cn.lzscxb.business.service.impl;

import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
import cn.lzscxb.common.utils.SecurityUtils;
import cn.lzscxb.domain.entity.FengTaskProblem;
import cn.lzscxb.domain.enums.RoleKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengTaskMapper;
import cn.lzscxb.domain.entity.FengTask;
import cn.lzscxb.business.service.IFengTaskService;

/**
 * 学习任务Service业务层处理
 * 
 * @author Likfees
 * @date 2022-12-24
 */
@Service
public class FengTaskServiceImpl implements IFengTaskService 
{

    @Autowired
    private FengTaskMapper fengTaskMapper;

    /**
     * 查询学习任务
     * 
     * @param id 学习任务主键
     * @return 学习任务
     */
    @Override
    public FengTask selectFengTaskById(Long id)
    {
        FengTask task = new FengTask();
        task.setId(id);
        task.setCurrentUserId(SecurityUtils.getUserId());
        FengTask fengTask = fengTaskMapper.selectFengTaskById(task);
        return fengTask;
    }

    /**
     * 查询学习任务列表
     * 
     * @param fengTask 学习任务
     * @return 学习任务
     */
    @Override
    public List<FengTask> selectFengTaskList(FengTask fengTask)
    {
        if (SecurityUtils.isRoleDesignated(RoleKey.TEACHER)) {
            fengTask.setTeacherId(SecurityUtils.getUserId());
        }
        return fengTaskMapper.selectFengTaskList(fengTask);
    }

    /**
     * 新增学习任务
     * 
     * @param fengTask 学习任务
     * @return 结果
     */
    @Override
    public int insertFengTask(FengTask fengTask)
    {
        // 如果是教师则加id
        if (SecurityUtils.isRoleDesignated(RoleKey.TEACHER)) {
            fengTask.setTeacherId(SecurityUtils.getUserId());
        }
        fengTask.setCreateTime(DateUtils.getNowDate());
        return fengTaskMapper.insertFengTask(fengTask);
    }

    /**
     * 修改学习任务
     * 
     * @param fengTask 学习任务
     * @return 结果
     */
    @Override
    public int updateFengTask(FengTask fengTask)
    {
        fengTask.setUpdateTime(DateUtils.getNowDate());
        return fengTaskMapper.updateFengTask(fengTask);
    }

    /**
     * 批量删除学习任务
     * 
     * @param ids 需要删除的学习任务主键
     * @return 结果
     */
    @Override
    public int deleteFengTaskByIds(Long[] ids)
    {
        return fengTaskMapper.deleteFengTaskByIds(ids);
    }

    /**
     * 删除学习任务信息
     * 
     * @param id 学习任务主键
     * @return 结果
     */
    @Override
    public int deleteFengTaskById(Long id)
    {
        return fengTaskMapper.deleteFengTaskById(id);
    }

}
