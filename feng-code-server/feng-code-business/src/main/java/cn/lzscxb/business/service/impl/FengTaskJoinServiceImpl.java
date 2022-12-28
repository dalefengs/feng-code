package cn.lzscxb.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.lzscxb.business.mapper.FengClassMapper;
import cn.lzscxb.business.mapper.FengTaskMapper;
import cn.lzscxb.common.utils.DateUtils;
import cn.lzscxb.common.utils.SecurityUtils;
import cn.lzscxb.domain.entity.FengClass;
import cn.lzscxb.domain.entity.FengTask;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengTaskJoinMapper;
import cn.lzscxb.domain.entity.FengTaskJoin;
import cn.lzscxb.business.service.IFengTaskJoinService;

/**
 * 学习任务学生参与Service业务层处理
 * 
 * @author Likfees
 * @date 2022-12-25
 */
@Service
@Slf4j
public class FengTaskJoinServiceImpl implements IFengTaskJoinService
{


    @Autowired
    private FengClassMapper fengClassMapper;

    @Autowired
    private FengTaskJoinMapper fengTaskJoinMapper;

    @Autowired
    private FengTaskMapper fengTaskMapper;

    /**
     * 查询学习任务学生参与
     * 
     * @param id 学习任务学生参与主键
     * @return 学习任务学生参与
     */
    @Override
    public FengTaskJoin selectFengTaskJoinById(Long id)
    {
        return fengTaskJoinMapper.selectFengTaskJoinById(id);
    }

    /**
     * 查询学习任务学生参与列表
     * 
     * @param fengTaskJoin 学习任务学生参与
     * @return 学习任务学生参与
     */
    @Override
    public List<FengTaskJoin> selectFengTaskJoinList(FengTaskJoin fengTaskJoin)
    {
        return fengTaskJoinMapper.selectFengTaskJoinList(fengTaskJoin);
    }

    /**
     * 查询学习任务学生参与列表
     *
     * @param fengTaskJoin 学习任务学生参与
     * @return 学习任务学生参与
     */
    @Override
    public List<FengTaskJoin> selectFengTaskJoinMyTaskList(FengTaskJoin fengTaskJoin)
    {
        fengTaskJoin.setUserId(SecurityUtils.getUserId());
        List<FengTaskJoin> fengTaskJoins = fengTaskJoinMapper.selectFengTaskJoinMyTaskList(fengTaskJoin);
        for (FengTaskJoin taskJoin : fengTaskJoins) {
            FengTask fengTask = new FengTask();
            fengTask.setId(taskJoin.getTaskId());
            taskJoin.setFengTaskInfo(fengTaskMapper.selectFengTaskById(fengTask));
        }
        return fengTaskJoins;
    }


    @Override
    public int insertFengTaskJoinByClass(FengTaskJoin fengTaskJoin) {
        // 查询班级下所有学生
        for (Long classId : fengTaskJoin.getClassIds()) {
            List<Long> userIds = fengClassMapper.selectUserIdByClassId(classId);
            log.info("classId:{}, 当前班级学生为：{} 个",fengTaskJoin.getClassId(), userIds.size());
            if (userIds.size() > 0) {
                List<FengTaskJoin> list = new ArrayList<>();
                for (Long userId : userIds) {
                    FengTaskJoin join = new FengTaskJoin();
                    join.setClassId(classId);
                    join.setUserId(userId);
                    join.setTaskId(fengTaskJoin.getTaskId());
                    join.setCreateTime(DateUtils.getNowDate());
                    join.setEndTime(fengTaskJoin.getEndTime());
                    log.info("------------------{}", join);
                    list.add(join);
                }
                int i = fengTaskJoinMapper.batchInsertTaskJoin(list);
            }
        }
        return 1;
    }

    /**
     * 新增学习任务学生参与
     * 
     * @param fengTaskJoin 学习任务学生参与
     * @return 结果
     */
    @Override
    public int insertFengTaskJoin(FengTaskJoin fengTaskJoin)
    {
        fengTaskJoin.setCreateTime(DateUtils.getNowDate());
        fengTaskJoin.setUserId(SecurityUtils.getUserId());
        return fengTaskJoinMapper.insertFengTaskJoin(fengTaskJoin);
    }

    /**
     * 修改学习任务学生参与
     * 
     * @param fengTaskJoin 学习任务学生参与
     * @return 结果
     */
    @Override
    public int updateFengTaskJoin(FengTaskJoin fengTaskJoin)
    {
        fengTaskJoin.setUpdateTime(DateUtils.getNowDate());
        return fengTaskJoinMapper.updateFengTaskJoin(fengTaskJoin);
    }

    /**
     * 批量删除学习任务学生参与
     * 
     * @param ids 需要删除的学习任务学生参与主键
     * @return 结果
     */
    @Override
    public int deleteFengTaskJoinByIds(Long[] ids)
    {
        return fengTaskJoinMapper.deleteFengTaskJoinByIds(ids);
    }

    /**
     * 删除学习任务学生参与信息
     * 
     * @param id 学习任务学生参与主键
     * @return 结果
     */
    @Override
    public int deleteFengTaskJoinById(Long id)
    {
        return fengTaskJoinMapper.deleteFengTaskJoinById(id);
    }

}
