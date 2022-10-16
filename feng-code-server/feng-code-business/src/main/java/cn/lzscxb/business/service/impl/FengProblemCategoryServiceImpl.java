package cn.lzscxb.business.service.impl;

import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengProblemCategoryMapper;
import cn.lzscxb.domain.entity.FengProblemCategory;
import cn.lzscxb.business.service.IFengProblemCategoryService;

/**
 * 问题分类Service业务层处理
 * 
 * @author Likfees
 * @date 2022-10-16
 */
@Service
public class FengProblemCategoryServiceImpl implements IFengProblemCategoryService 
{
    @Autowired
    private FengProblemCategoryMapper fengProblemCategoryMapper;

    /**
     * 查询问题分类
     * 
     * @param id 问题分类主键
     * @return 问题分类
     */
    @Override
    public FengProblemCategory selectFengProblemCategoryById(Long id)
    {
        return fengProblemCategoryMapper.selectFengProblemCategoryById(id);
    }

    /**
     * 查询问题分类列表
     * 
     * @param fengProblemCategory 问题分类
     * @return 问题分类
     */
    @Override
    public List<FengProblemCategory> selectFengProblemCategoryList(FengProblemCategory fengProblemCategory)
    {
        return fengProblemCategoryMapper.selectFengProblemCategoryList(fengProblemCategory);
    }

    /**
     * 新增问题分类
     * 
     * @param fengProblemCategory 问题分类
     * @return 结果
     */
    @Override
    public int insertFengProblemCategory(FengProblemCategory fengProblemCategory)
    {
        fengProblemCategory.setCreateTime(DateUtils.getNowDate());
        return fengProblemCategoryMapper.insertFengProblemCategory(fengProblemCategory);
    }

    /**
     * 修改问题分类
     * 
     * @param fengProblemCategory 问题分类
     * @return 结果
     */
    @Override
    public int updateFengProblemCategory(FengProblemCategory fengProblemCategory)
    {
        fengProblemCategory.setUpdateTime(DateUtils.getNowDate());
        return fengProblemCategoryMapper.updateFengProblemCategory(fengProblemCategory);
    }

    /**
     * 批量删除问题分类
     * 
     * @param ids 需要删除的问题分类主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemCategoryByIds(Long[] ids)
    {
        return fengProblemCategoryMapper.deleteFengProblemCategoryByIds(ids);
    }

    /**
     * 删除问题分类信息
     * 
     * @param id 问题分类主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemCategoryById(Long id)
    {
        return fengProblemCategoryMapper.deleteFengProblemCategoryById(id);
    }
}
