package cn.lzscxb.business.service;

import java.util.List;
import cn.lzscxb.domain.entity.FengTaskJoin;

/**
 * 学习任务学生参与Service接口
 * 
 * @author Likfees
 * @date 2022-12-25
 */
public interface IFengTaskJoinService 
{
    /**
     * 查询学习任务学生参与
     * 
     * @param id 学习任务学生参与主键
     * @return 学习任务学生参与
     */
    public FengTaskJoin selectFengTaskJoinById(Long id);

    /**
     * 查询学习任务学生参与列表
     * 
     * @param fengTaskJoin 学习任务学生参与
     * @return 学习任务学生参与集合
     */
    public List<FengTaskJoin> selectFengTaskJoinList(FengTaskJoin fengTaskJoin);

    /**
     * 新增学习任务学生参与
     * 
     * @param fengTaskJoin 学习任务学生参与
     * @return 结果
     */
    public int insertFengTaskJoin(FengTaskJoin fengTaskJoin);

    /**
     * 修改学习任务学生参与
     * 
     * @param fengTaskJoin 学习任务学生参与
     * @return 结果
     */
    public int updateFengTaskJoin(FengTaskJoin fengTaskJoin);

    /**
     * 批量删除学习任务学生参与
     * 
     * @param ids 需要删除的学习任务学生参与主键集合
     * @return 结果
     */
    public int deleteFengTaskJoinByIds(Long[] ids);

    /**
     * 删除学习任务学生参与信息
     * 
     * @param id 学习任务学生参与主键
     * @return 结果
     */
    public int deleteFengTaskJoinById(Long id);
}
