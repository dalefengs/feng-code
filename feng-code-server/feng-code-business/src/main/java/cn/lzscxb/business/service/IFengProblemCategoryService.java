package cn.lzscxb.business.service;

import java.util.List;
import cn.lzscxb.domain.entity.FengProblemCategory;

/**
 * 问题分类Service接口
 * 
 * @author Likfees
 * @date 2022-10-16
 */
public interface IFengProblemCategoryService 
{
    /**
     * 查询问题分类
     * 
     * @param id 问题分类主键
     * @return 问题分类
     */
    public FengProblemCategory selectFengProblemCategoryById(Long id);

    /**
     * 查询问题分类列表
     * 
     * @param fengProblemCategory 问题分类
     * @return 问题分类集合
     */
    public List<FengProblemCategory> selectFengProblemCategoryList(FengProblemCategory fengProblemCategory);

    /**
     * 新增问题分类
     * 
     * @param fengProblemCategory 问题分类
     * @return 结果
     */
    public int insertFengProblemCategory(FengProblemCategory fengProblemCategory);

    /**
     * 修改问题分类
     * 
     * @param fengProblemCategory 问题分类
     * @return 结果
     */
    public int updateFengProblemCategory(FengProblemCategory fengProblemCategory);

    /**
     * 批量删除问题分类
     * 
     * @param ids 需要删除的问题分类主键集合
     * @return 结果
     */
    public int deleteFengProblemCategoryByIds(Long[] ids);

    /**
     * 删除问题分类信息
     * 
     * @param id 问题分类主键
     * @return 结果
     */
    public int deleteFengProblemCategoryById(Long id);
}
