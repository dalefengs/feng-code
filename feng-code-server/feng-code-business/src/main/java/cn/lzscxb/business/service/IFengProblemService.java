package cn.lzscxb.business.service;

import java.util.List;
import cn.lzscxb.domain.entity.FengProblem;
import cn.lzscxb.domain.model.ProblemCreateBody;

/**
 * 题目管理Service接口
 * 
 * @author Likfees
 * @date 2022-11-21
 */
public interface IFengProblemService 
{
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
     * @param problemCreateBody 题目管理
     * @return 结果
     */
    public int insertFengProblem(ProblemCreateBody problemCreateBody);

    /**
     * 修改题目管理
     * 
     * @param problemCreateBody 题目管理
     * @return 结果
     */
    public int updateFengProblem(ProblemCreateBody problemCreateBody);

    /**
     * 批量删除题目管理
     * 
     * @param ids 需要删除的题目管理主键集合
     * @return 结果
     */
    public int deleteFengProblemByIds(Long[] ids);

    /**
     * 删除题目管理信息
     * 
     * @param id 题目管理主键
     * @return 结果
     */
    public int deleteFengProblemById(Long id);
}
