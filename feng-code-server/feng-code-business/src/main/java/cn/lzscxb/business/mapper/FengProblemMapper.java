package cn.lzscxb.business.mapper;

import java.util.List;
import cn.lzscxb.domain.entity.FengProblem;
import cn.lzscxb.domain.entity.FengProblemQueue;

/**
 * 题目管理Mapper接口
 *
 * @author Likfees
 * @date 2022-11-21
 */
public interface FengProblemMapper {
    /**
     * 查询题目管理
     *
     * @param id 题目管理主键
     * @return 题目管理
     */
    public FengProblem selectFengProblemById(Long id);

    /**
     * 查询题目管理列表
     *
     * @param fengProblem 题目管理
     * @return 题目管理集合
     */
    public List<FengProblem> selectFengProblemList(FengProblem fengProblem);

    /**
     * 新增题目管理
     *
     * @param fengProblem 题目管理
     * @return 结果
     */
    public int insertFengProblem(FengProblem fengProblem);

    /**
     * 修改题目管理
     *
     * @param fengProblem 题目管理
     * @return 结果
     */
    public int updateFengProblem(FengProblem fengProblem);

    /**
     * 删除题目管理
     *
     * @param id 题目管理主键
     * @return 结果
     */
    public int deleteFengProblemById(Long id);

    /**
     * 批量删除题目管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengProblemByIds(Long[] ids);

    List<FengProblem> selectFengProblemTaskList(FengProblem fengProblem);

    List<Long> selectFengProblemAllIds();

    FengProblem selectFengProblem(FengProblem fengProblem);
}
