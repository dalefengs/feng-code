package cn.lzscxb.business.service.impl;

import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengProblemTagsMapper;
import cn.lzscxb.domain.entity.FengProblemTags;
import cn.lzscxb.business.service.IFengProblemTagsService;

/**
 * 题目标签Service业务层处理
 * 
 * @author Likfees
 * @date 2022-10-16
 */
@Service
public class FengProblemTagsServiceImpl implements IFengProblemTagsService 
{
    @Autowired
    private FengProblemTagsMapper fengProblemTagsMapper;

    /**
     * 查询题目标签
     * 
     * @param id 题目标签主键
     * @return 题目标签
     */
    @Override
    public FengProblemTags selectFengProblemTagsById(Long id)
    {
        return fengProblemTagsMapper.selectFengProblemTagsById(id);
    }

    /**
     * 查询题目标签列表
     * 
     * @param fengProblemTags 题目标签
     * @return 题目标签
     */
    @Override
    public List<FengProblemTags> selectFengProblemTagsList(FengProblemTags fengProblemTags)
    {
        return fengProblemTagsMapper.selectFengProblemTagsList(fengProblemTags);
    }

    /**
     * 新增题目标签
     * 
     * @param fengProblemTags 题目标签
     * @return 结果
     */
    @Override
    public int insertFengProblemTags(FengProblemTags fengProblemTags)
    {
        fengProblemTags.setCreateTime(DateUtils.getNowDate());
        return fengProblemTagsMapper.insertFengProblemTags(fengProblemTags);
    }

    /**
     * 修改题目标签
     * 
     * @param fengProblemTags 题目标签
     * @return 结果
     */
    @Override
    public int updateFengProblemTags(FengProblemTags fengProblemTags)
    {
        fengProblemTags.setUpdateTime(DateUtils.getNowDate());
        return fengProblemTagsMapper.updateFengProblemTags(fengProblemTags);
    }

    /**
     * 批量删除题目标签
     * 
     * @param ids 需要删除的题目标签主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemTagsByIds(Long[] ids)
    {
        return fengProblemTagsMapper.deleteFengProblemTagsByIds(ids);
    }

    /**
     * 删除题目标签信息
     * 
     * @param id 题目标签主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemTagsById(Long id)
    {
        return fengProblemTagsMapper.deleteFengProblemTagsById(id);
    }
}
