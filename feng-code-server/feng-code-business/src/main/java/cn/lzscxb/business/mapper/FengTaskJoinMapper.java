package cn.lzscxb.business.mapper;

import java.util.List;
import cn.lzscxb.domain.entity.FengTaskJoin;

/**
 * 学习任务学生参与Mapper接口
 * 
 * @author Likfees
 * @date 2022-12-25
 */
public interface FengTaskJoinMapper 
{
    /**
     * 查询学习任务学生参与
     * 
     * @param id 学习任务学生参与主键
     * @return 学习任务学生参与
     */
    public FengTaskJoin selectFengTaskJoinById(Long id);

    FengTaskJoin selectFengTaskJoinByTaskId(FengTaskJoin fengTaskJoin);


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
     * 删除学习任务学生参与
     * 
     * @param id 学习任务学生参与主键
     * @return 结果
     */
    public int deleteFengTaskJoinById(Long id);

    /**
     * 批量删除学习任务学生参与
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengTaskJoinByIds(Long[] ids);


    public int batchInsertTaskJoin(List<FengTaskJoin> list);

    List<FengTaskJoin> selectFengTaskJoinMyTaskList(FengTaskJoin fengTaskJoin);

    List<FengTaskJoin> selectOverdueList();
}
