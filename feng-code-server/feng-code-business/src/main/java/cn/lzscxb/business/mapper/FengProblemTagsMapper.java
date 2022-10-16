package cn.lzscxb.business.mapper;

import java.util.List;
import cn.lzscxb.domain.entity.FengProblemTags;

/**
 * 题目标签Mapper接口
 * 
 * @author Likfees
 * @date 2022-10-16
 */
public interface FengProblemTagsMapper 
{
    /**
     * 查询题目标签
     * 
     * @param id 题目标签主键
     * @return 题目标签
     */
    public FengProblemTags selectFengProblemTagsById(Long id);

    /**
     * 查询题目标签列表
     * 
     * @param fengProblemTags 题目标签
     * @return 题目标签集合
     */
    public List<FengProblemTags> selectFengProblemTagsList(FengProblemTags fengProblemTags);

    /**
     * 新增题目标签
     * 
     * @param fengProblemTags 题目标签
     * @return 结果
     */
    public int insertFengProblemTags(FengProblemTags fengProblemTags);

    /**
     * 修改题目标签
     * 
     * @param fengProblemTags 题目标签
     * @return 结果
     */
    public int updateFengProblemTags(FengProblemTags fengProblemTags);

    /**
     * 删除题目标签
     * 
     * @param id 题目标签主键
     * @return 结果
     */
    public int deleteFengProblemTagsById(Long id);

    /**
     * 批量删除题目标签
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengProblemTagsByIds(Long[] ids);
}
