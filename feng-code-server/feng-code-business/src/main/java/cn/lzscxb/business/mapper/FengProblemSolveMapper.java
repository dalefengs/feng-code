package cn.lzscxb.business.mapper;

import java.util.List;
import cn.lzscxb.domain.entity.FengProblemSolve;

/**
 * 题解列表Mapper接口
 * 
 * @author Likfees
 * @date 2022-10-16
 */
public interface FengProblemSolveMapper 
{
    /**
     * 查询题解列表
     * 
     * @param id 题解列表主键
     * @return 题解列表
     */
    public FengProblemSolve selectFengProblemSolveById(Long id);

    /**
     * 查询题解列表列表
     * 
     * @param fengProblemSolve 题解列表
     * @return 题解列表集合
     */
    public List<FengProblemSolve> selectFengProblemSolveList(FengProblemSolve fengProblemSolve);

    /**
     * 新增题解列表
     * 
     * @param fengProblemSolve 题解列表
     * @return 结果
     */
    public int insertFengProblemSolve(FengProblemSolve fengProblemSolve);

    /**
     * 修改题解列表
     * 
     * @param fengProblemSolve 题解列表
     * @return 结果
     */
    public int updateFengProblemSolve(FengProblemSolve fengProblemSolve);

    /**
     * 删除题解列表
     * 
     * @param id 题解列表主键
     * @return 结果
     */
    public int deleteFengProblemSolveById(Long id);

    /**
     * 批量删除题解列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengProblemSolveByIds(Long[] ids);
}
