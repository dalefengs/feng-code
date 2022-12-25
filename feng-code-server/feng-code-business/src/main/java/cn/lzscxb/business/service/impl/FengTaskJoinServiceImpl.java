package cn.lzscxb.business.service.impl;

import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
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
public class FengTaskJoinServiceImpl implements IFengTaskJoinService 
{
    @Autowired
    private FengTaskJoinMapper fengTaskJoinMapper;

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
     * 新增学习任务学生参与
     * 
     * @param fengTaskJoin 学习任务学生参与
     * @return 结果
     */
    @Override
    public int insertFengTaskJoin(FengTaskJoin fengTaskJoin)
    {
        fengTaskJoin.setCreateTime(DateUtils.getNowDate());
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
