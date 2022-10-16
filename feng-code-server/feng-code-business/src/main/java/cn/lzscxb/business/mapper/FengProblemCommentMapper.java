package cn.lzscxb.business.mapper;

import java.util.List;
import cn.lzscxb.domain.entity.FengProblemComment;

/**
 * 题目评论Mapper接口
 * 
 * @author Likfees
 * @date 2022-10-16
 */
public interface FengProblemCommentMapper 
{
    /**
     * 查询题目评论
     * 
     * @param id 题目评论主键
     * @return 题目评论
     */
    public FengProblemComment selectFengProblemCommentById(Long id);

    /**
     * 查询题目评论列表
     * 
     * @param fengProblemComment 题目评论
     * @return 题目评论集合
     */
    public List<FengProblemComment> selectFengProblemCommentList(FengProblemComment fengProblemComment);

    /**
     * 新增题目评论
     * 
     * @param fengProblemComment 题目评论
     * @return 结果
     */
    public int insertFengProblemComment(FengProblemComment fengProblemComment);

    /**
     * 修改题目评论
     * 
     * @param fengProblemComment 题目评论
     * @return 结果
     */
    public int updateFengProblemComment(FengProblemComment fengProblemComment);

    /**
     * 删除题目评论
     * 
     * @param id 题目评论主键
     * @return 结果
     */
    public int deleteFengProblemCommentById(Long id);

    /**
     * 批量删除题目评论
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFengProblemCommentByIds(Long[] ids);
}
